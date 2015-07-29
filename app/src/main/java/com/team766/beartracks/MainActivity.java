package com.team766.beartracks;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Home_Fragment home_fragment;
    private Calendar_Fragment calendar_fragment;
    private Groups_Fragment groups_fragment;
    private People_Fragment people_fragment;
    private Project_Fragment project_fragment;
    private String[] activityNames;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityNames = getResources().getStringArray(R.array.activities);

        //This next chunk of code is used to implement the nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, activityNames));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //End nav drawer implementation

        home_fragment = new Home_Fragment();
        calendar_fragment = new Calendar_Fragment();
        groups_fragment = new Groups_Fragment();
        people_fragment = new People_Fragment();
        project_fragment = new Project_Fragment();

        fragmentManager = getSupportFragmentManager();
        swapFragment(home_fragment);
    }

    //Registers User clicks to set position
    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
            setPosition(position);
        }
    }

    //Decides which fragment to switch to
    public void setPosition(int position){
        switch (position){
            case 0:
                swapFragment(home_fragment);
                break;
            case 1:
                swapFragment(calendar_fragment);
                break;
            case 2:
                swapFragment(groups_fragment);
                break;
            case 3:
                swapFragment(people_fragment);
                break;
            case 4:
                swapFragment(project_fragment);
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    //Changes the current fragment to the user selected fragment
    private void swapFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content_holder, fragment).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
