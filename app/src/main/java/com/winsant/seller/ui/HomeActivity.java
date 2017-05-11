package com.winsant.seller.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.winsant.seller.R;
import com.winsant.seller.ui.fragment.HomeFragment;
import com.winsant.seller.ui.fragment.ListingFragment;
import com.winsant.seller.ui.fragment.OrderFragment;
import com.winsant.seller.ui.fragment.PaymentFragment;
import com.winsant.seller.ui.fragment.ReturnFragment;
import com.winsant.seller.ui.fragment.SettingFragment;
import com.winsant.seller.ui.fragment.SupportFragment;
import com.winsant.seller.utils.CommonDataUtility;

public class HomeActivity extends AppCompatActivity {

    private Activity activity;
    private boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity = HomeActivity.this;

        setupToolbar();
        initNavigationDrawer();
        setUpNavigationView();
        pushFragment(new HomeFragment());
    }

    private void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setTypeface(CommonDataUtility.setTitleTypeFace(activity));
        toolbar_title.setText(getString(R.string.nav_home));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ico_menu_svg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    private void loadNavHeader() {
//        // name, website
//        txtName.setText("Ravi Tamada");
//        txtWebsite.setText("www.androidhive.info");
//
//        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);
//
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
//    }

    private void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.mNavigationView);

//        navHeader = mNavigationView.getHeaderView(0);

    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        setToolbarTitle(getString(R.string.nav_home));
                        pushFragment(new HomeFragment());
                        break;
                    case R.id.nav_order:
                        setToolbarTitle(getString(R.string.nav_order));
                        pushFragment(new OrderFragment());
                        break;
                    case R.id.nav_listing:
                        setToolbarTitle(getString(R.string.nav_listing));
                        pushFragment(new ListingFragment());
                        break;
                    case R.id.nav_payment:
                        setToolbarTitle(getString(R.string.nav_payment));
                        pushFragment(new PaymentFragment());
                        break;
                    case R.id.nav_return:
                        setToolbarTitle(getString(R.string.nav_return));
                        pushFragment(new ReturnFragment());
                        break;
                    case R.id.nav_support:
                        setToolbarTitle(getString(R.string.nav_support));
                        pushFragment(new SupportFragment());
                        return true;
                    case R.id.nav_setting:
                        setToolbarTitle(getString(R.string.nav_setting));
                        pushFragment(new SettingFragment());
                        return true;
                    case R.id.nav_logout:

                        CommonDataUtility.clearData();

                        Intent intent = new Intent(activity, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        return true;
                    default:
                        return true;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                closeNavDrawer();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setToolbarTitle(String title) {
        toolbar_title.setText(title);
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {

        if (isNavDrawerOpen())
            closeNavDrawer();

        System.gc();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount() - 1; ++i) {
                getSupportFragmentManager().popBackStack();
            }

            pushFragment(new HomeFragment());

        } else {
            backPress();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void backPress() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /**
     * Method to push any fragment_home into given id.
     *
     * @param fragment An instance of Fragment to show into the given id.
     */

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.add(R.id.container, fragment).addToBackStack(null);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.activity_action_notification)).setIcon(R.drawable.ico_notification_svg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

//    private void selectFragment(MenuItem item) {
//        Fragment frag = null;
//        // init corresponding fragment_home
//        switch (item.getItemId()) {
//            case R.id.action_home:
//                frag = MenuFragment.newInstance(getString(R.string.text_home));
//                break;
//            case R.id.action_offer:
//                frag = MenuFragment.newInstance(getString(R.string.text_offer));
//                break;
//            case R.id.action_concept:
//                frag = MenuFragment.newInstance(getString(R.string.text_concept));
//                break;
//            case R.id.action_wishlist:
//                frag = MenuFragment.newInstance(getString(R.string.text_wishlist));
//                break;
//            case R.id.action_profile:
//                frag = MenuFragment.newInstance(getString(R.string.text_profile));
//                break;
//        }
//
//        // update selected item
//        mSelectedItem = item.getItemId();
//
//        item.setChecked(true);
//
//        // uncheck the other items.
//        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
//            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
//            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
//        }
//
//        toolbar_title.setText(item.getTitle());
//
//        if (frag != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.container, frag, frag.getTag());
//            ft.commit();
//        }
