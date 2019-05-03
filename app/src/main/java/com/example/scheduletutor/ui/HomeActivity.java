package com.example.scheduletutor.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scheduletutor.MainActivity;
import com.example.scheduletutor.R;
import com.example.scheduletutor.di.component.DaggerHomeComponent;
import com.example.scheduletutor.di.component.HomeComponent;
import com.example.scheduletutor.di.modul.HomeModule;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.ui.fragment.HistoryFragment;
import com.example.scheduletutor.ui.fragment.ListFragment;

import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    UserLocalStore userLocalStore;

    User currUser;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        checkUserLoggedIn();
        setSupportActionBar(toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_ALL_APPS));
//                Snackbar.make(view, "Sedang Dalam Perbaikan!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initiateFirstLoad();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkUserLoggedIn() {
        HomeComponent homeComponent = DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build();
        homeComponent.Inject(this);
        Log.d("Test",userLocalStore.getUserLoggedIn()+"");
        if(!userLocalStore.getUserLoggedIn()){
            Intent i = new Intent(HomeActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(i);
        }
        currUser = userLocalStore.getLoggedInUser();
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imageView = headerLayout.findViewById(R.id.ivUserPhoto);
        TextView tvName = headerLayout.findViewById(R.id.tvUserName);
        TextView tvEmail = headerLayout.findViewById(R.id.tvUserEmail);
        Glide.with(HomeActivity.this).load("http:\\/\\/myavantgardeina.com\\/tutorApps\\/profile1.jpg").into(imageView);
        tvName.setText(currUser.getUserName());
        tvEmail.setText(currUser.getUserEmail());

    }

    @SuppressLint("RestrictedApi")
    private void initiateFirstLoad() {
        if(currUser.getUserRoleID().equals("1") ) { //1 student 2 tutor
            navigationView.inflateMenu(R.menu.activity_student_home_drawer);
            fab.setVisibility(View.INVISIBLE);
        }else navigationView.inflateMenu(R.menu.activity_tutor_home_drawer);

        //set first
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container,new ListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = ( DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.tutor_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_list_kelas) {
            fragment= new ListFragment();
            Toast.makeText(this, "daftar kelas", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_list_kelas_saya) {
            fragment= new ListFragment();
            Toast.makeText(this, "kelas saya", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_exit) {
            userLocalStore.clearUserData();
            Intent i =new Intent(HomeActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
        } else if (id == R.id.nav_history) {
            fragment= new HistoryFragment();
            Toast.makeText(this, "history", Toast.LENGTH_SHORT).show();
        }
        if(fragment!=null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
