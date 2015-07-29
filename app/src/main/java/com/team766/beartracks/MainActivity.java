package com.team766.beartracks;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.team766.beartracks.UI.Calendar_Fragment;
import com.team766.beartracks.UI.Groups_Fragment;
import com.team766.beartracks.UI.Home_Fragment;
import com.team766.beartracks.UI.People_Fragment;
import com.team766.beartracks.UI.Project_Fragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager fragmentManager;
    private NavigationView nvDrawer;
    private Home_Fragment home_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        fragmentManager = getSupportFragmentManager();

        home_fragment = new Home_Fragment();
        fragmentManager.beginTransaction().replace(R.id.content_holder, home_fragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navView){
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem item){
        Fragment fragment = null;

        Class fragmentClass;
        switch (item.getItemId()){
            case R.id.home_frag:
                fragmentClass = Home_Fragment.class;
                break;
            case R.id.cal_frag:
                fragmentClass = Calendar_Fragment.class;
                break;
            case R.id.groups_frag:
                fragmentClass = Groups_Fragment.class;
                break;
            case R.id.people_frag:
                fragmentClass = People_Fragment.class;
                break;
            case R.id.project_frag:
                fragmentClass = Project_Fragment.class;
                break;
            //happy now Brett?
            default:
                fragmentClass = Home_Fragment.class;
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.content_holder, fragment).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Override so we can close drawer on back button press
    @Override
    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(Gravity.START | Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

}
