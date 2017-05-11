package com.winsant.seller.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.winsant.seller.R;
import com.winsant.seller.ui.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();
        initNavigationDrawer();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ico_menu_svg);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupActionBarDrawerToogle();
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
    }

    private void setupActionBarDrawerToogle() {

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                //Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                //Snackbar.make(drawerView, R.string.drawer_open, Snackbar.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {

        //setting up selected item listener
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
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
}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.activity_action_cart)).setIcon(R.drawable.ico_menu_cart).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        return true;
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case 1:
//
//                if (MyApplication.getInstance().getPreferenceUtility().getLogin()) {
//                    startActivity(new Intent(HomeActivity.this, CartActivity.class));
//                } else {
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                }
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
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
