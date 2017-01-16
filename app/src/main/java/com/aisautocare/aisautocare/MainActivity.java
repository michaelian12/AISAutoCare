package com.aisautocare.aisautocare;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aisautocare.aisautocare.activity.GarageActivity;
import com.aisautocare.aisautocare.activity.ProfileActivity;
import com.aisautocare.aisautocare.adapter.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // remove title
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Set Navigation Drawer */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navItemSelect);

        /* Set View Pager */
        viewPager = (ViewPager) findViewById(R.id.category_viewpager);
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        tabLayout = (TabLayout) findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {

        Intent intent;

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setCheckable(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (menuItem.getItemId()){
                case R.id.nav_profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_order:
                    Toast.makeText(getApplicationContext(), "Pesanan", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(MainActivity.this, OrderActivity.class);
//                    startActivity(intent);
                    return true;
                case R.id.nav_garage:
                    intent = new Intent(MainActivity.this, GarageActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return true;
            }
        }
    };
}
