package com.team766.beartracks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team766.beartracks.Login.Welcome_Screen;
import com.team766.beartracks.Roster.Member;
import com.team766.beartracks.Settings.SettingsActivity;
import com.team766.beartracks.Calendar.Calendar_Fragment;
import com.team766.beartracks.UI.Groups_Fragment;
import com.team766.beartracks.UI.Home_Fragment;
import com.team766.beartracks.Roster.Roster_List;
import com.team766.beartracks.Role.Role_List;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager fragmentManager;
    private NavigationView nvDrawer;
    private View headerView;
    private Toolbar toolbar;
    private String userEmail, emailChecker;
    private TextView profileName;
    static final String STATE_SELECTED_POSITION = "orientation";
    private int mCurrentSelectedPosition = 0;
    private SharedPreferences.Editor editor;
    private Firebase peopleRef;
    private Member User;
    private CircleImageView profPic;
    public static HashMap<String, Member> teamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        peopleRef = new Firebase("https://beartracks.firebaseio.com/people/");

        if (savedInstanceState != null) {
            mCurrentSelectedPosition =
                    savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        SharedPreferences settings = getSharedPreferences(Welcome_Screen.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        userEmail = settings.getString("userEmail", "");
        String personName = settings.getString("userName", "");
        String picURL = settings.getString("picURL", "");

        setupToolbar();
        teamMembers = new HashMap<>();
        storeRoster(); //saves a copy of the roster so I can reference it easily anywhere in the app
        setupNavDrawer();
        profileName = (TextView) headerView.findViewById(R.id.userEmailDisplay);
        profPic = (CircleImageView) headerView.findViewById(R.id.circleView);

        if(personName.equals("")){
            setProfileName();
        }
        else if(picURL.equals("")){
            editor.putString("picURL", "https://pbs.twimg.com/profile_images/1307271994/image.jpg");
            editor.apply();
        }
        else{
            profileName.setText(personName);
            Picasso.with(getApplicationContext()).load(picURL).fit().centerCrop().into(profPic);
        }

        fragmentManager = getSupportFragmentManager();
        //setup default fragment
        Home_Fragment home_fragment = new Home_Fragment();
        swapFragment(home_fragment);

    }

    private void makeToast(String name){
        Toast.makeText(this, name , Toast.LENGTH_SHORT).show();
    }

    private void setupNavDrawer(){
        if(toolbar != null){
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            headerView = nvDrawer.inflateHeaderView(R.layout.header);
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
                    User = child.getValue(Member.class);
                    emailChecker = User.getEmail();
                    if (emailChecker.equals(userEmail)) {
                        profileName.setText(User.getName());
                        Picasso.with(getApplicationContext()).load(User.getPhoto()).fit().centerCrop().into(profPic);
                        editor.putString("userName", User.getName());
                        editor.putString("picURL", User.getPhoto());
                        editor.commit();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                makeToast("You got yourself an error, try restarting the app");
            }
        });
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    private void swapFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.content_holder, fragment).commit();
    }

    public void selectDrawerItem(MenuItem item){

        switch (item.getItemId()){
            case R.id.home_frag:
                swapFragment(new Home_Fragment());
                mCurrentSelectedPosition = 0;
                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.cal_frag:
                swapFragment(new Calendar_Fragment());
                mCurrentSelectedPosition = 1;
                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.people_frag:
                swapFragment(new Roster_List());
                mCurrentSelectedPosition = 3;
                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.role_frag:
                swapFragment(new Role_List());
                mCurrentSelectedPosition = 4;
                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                mCurrentSelectedPosition = 5;
                break;
            case R.id.logOut:
                confirmLogOut();
                break;
            default:
                swapFragment(new Home_Fragment());
                mCurrentSelectedPosition = 0;
                mDrawerLayout.closeDrawers();
        }
    }

    private void storeRoster(){
        Firebase peopleRef = new Firebase("https://beartracks.firebaseio.com/people");
        peopleRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Member teamMember = dataSnapshot.getValue(Member.class);
                teamMembers.put(dataSnapshot.getKey(), teamMember);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public static Member getMemberByID(String id){
        return teamMembers.get(id);
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

    private void confirmLogOut(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to log out?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Welcome_Screen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                editor.putBoolean("hasLoggedIn", false).commit();
                startActivity(intent);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
