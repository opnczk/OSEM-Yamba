package com.sar2016.olivier.alexandra.yamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import winterwell.jtwitter.Twitter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateStatusFragment.OnFragmentInteractionListener, TimelineFragment.OnFragmentInteractionListener{

    static Context context;
    private NavigationView navigationView;
    private View navHeader;
    private Toolbar toolbar;


    public static final String TAG_TIMELINE = "timeline";
    public static final String TAG_UPDATE = "update";
    public static String CURRENT_TAG = TAG_TIMELINE;

    private boolean shouldLoadHomeOnBackPress = true;
    private Handler mHandler;
    private SharedPreferences preferences;
    private Twitter api;

    private TweetDB tweetDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mHandler = new Handler();
        
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(savedInstanceState == null){
            loadTimelineFragment();
        }

        this.tweetDataBase = new TweetDB(this);

        startService(new Intent(getBaseContext(), GetNewStatusesService.class));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            this.CURRENT_TAG = this.TAG_TIMELINE;
        } else if (id == R.id.nav_gallery) {
            this.CURRENT_TAG = this.TAG_UPDATE;
        }

        this.loadTimelineFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadTimelineFragment(){

        Fragment frag = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        if(frag != null){
            /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.show(frag);
            fragmentTransaction.commit();*/
            return;
        }

        Runnable mPendingRunnable = new Runnable(){

            @Override
            public void run() {
                Fragment fragment = getTimelineFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        if(mPendingRunnable != null){
            mHandler.post(mPendingRunnable);
        }
    }

    private Fragment getTimelineFragment(){
            if(CURRENT_TAG == TAG_UPDATE) {
                UpdateStatusFragment updateStatusFragment = new UpdateStatusFragment();
                return updateStatusFragment;
            }else{
                TimelineFragment timelineFragment = new TimelineFragment();
                return timelineFragment;
            }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    private Twitter getTwitterObject() {
        String username = this.getPreferences().getString(getResources().getString(R.string.key_username), "DEFAULT");
        String password = this.getPreferences().getString(getResources().getString(R.string.key_password), "DEFAULT");

        String api = this.getPreferences().getString(getResources().getString(R.string.key_api_url), "DEFAULT");
        Twitter twitter = new Twitter(username, password);
        twitter.setAPIRootUrl(api);
        return twitter;
    }

    public Twitter getApi() {
        return this.api;
    }

    @Override
    protected void onStart() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // API
        api = this.getTwitterObject();

        // Listener on preferences changings
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                api = null;
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);

        this.updateTimeline();
        super.onStart();
    }

    public void updateTimeline(){
        try {
            FragmentManager fm = getSupportFragmentManager();
            TimelineFragment currentFragment = (TimelineFragment) fm.findFragmentByTag(MainActivity.TAG_TIMELINE);
            if(currentFragment != null && currentFragment.isVisible()) {
                currentFragment.setTimeline(this.getTimelineList());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Tweet> getTimelineList(){
        this.tweetDataBase.open();
        List<Tweet> tweets = this.tweetDataBase.getAll();
        this.tweetDataBase.close();

        return tweets;
    }

    public void pushToDB(List<Twitter.Status> statuses) {
        this.tweetDataBase.open();
        this.tweetDataBase.deleteAll();

        Log.d("PUSHING TO DB",""+statuses.size());
        for(int i = 0 ; i < statuses.size(); i++){
            this.tweetDataBase.insertTweet(new Tweet(statuses.get(i)));
        }
        this.tweetDataBase.close();
    }

    public void pushToDb(Tweet tweet){
        this.tweetDataBase.open();

        this.tweetDataBase.insertTweet(tweet);

        this.tweetDataBase.close();
    }
}
