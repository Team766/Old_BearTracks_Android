package com.team766.beartracks;

import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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
    private Toolbar toolbar;
    private String userEmail, emailChecker;
    private TextView profileName;
    static final String STATE_SELECTED_POSITION = "orientation";
    private int mCurrentSelectedPosition = 0;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Firebase peopleRef;
    private Person User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleRef = new Firebase("https://beartracks.firebaseio.com/people/");

        if (savedInstanceState != null) {
            mCurrentSelectedPosition =
                    savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        settings = getSharedPreferences(welcome_screen.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        userEmail = settings.getString("userEmail", "");
        String personName = settings.getString("userName", "");

        setupToolbar();
        setupNavDrawer();
        profileName = (TextView) findViewById(R.id.userEmailDisplay);

        if(personName.equals("")){
            setProfileName();
        }else{
            profileName.setText(personName);
        }

        fragmentManager = getSupportFragmentManager();
        //setup default fragment
        Home_Fragment home_fragment = new Home_Fragment();
        fragmentManager.beginTransaction().replace(R.id.content_holder, home_fragment).commit();

    }

    private void makeToast(String name){
        Toast.makeText(this, name , Toast.LENGTH_SHORT).show();
    }

    private void setupNavDrawer(){
        if(toolbar != null){
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            // Find and setup drawerview
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            setupDrawerContent(nvDrawer);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.string.drawer_open, R.string.drawer_close);

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
    }

    private void setProfileName(){
        peopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User = child.getValue(Person.class);
                    emailChecker = User.getEmail();
                    if (emailChecker.equals(userEmail)) {
                        profileName.setText(User.getName());
                        editor.putString("userName", User.getName());
                        editor.commit();
                        break;
                    }
                    makeToast("Setting Up Profile Name");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                makeToast("You got yourself an error, try restarting the app");
            }
        });
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
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
                mCurrentSelectedPosition = 0;
                break;
            case R.id.cal_frag:
                fragmentClass = Calendar_Fragment.class;
                mCurrentSelectedPosition = 1;
                break;
            case R.id.groups_frag:
                fragmentClass = Groups_Fragment.class;
                mCurrentSelectedPosition = 2;
                break;
            case R.id.people_frag:
                fragmentClass = People_Fragment.class;
                mCurrentSelectedPosition = 3;
                break;
            case R.id.project_frag:
                fragmentClass = Project_Fragment.class;
                mCurrentSelectedPosition = 4;
                break;
            //happy now Brett?
            default:
                fragmentClass = Home_Fragment.class;
                mCurrentSelectedPosition = 0;
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

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = nvDrawer.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
        selectDrawerItem(menu.getItem(mCurrentSelectedPosition).setChecked(true));
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
