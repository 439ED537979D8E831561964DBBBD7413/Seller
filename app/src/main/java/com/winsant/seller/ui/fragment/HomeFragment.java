package com.winsant.seller.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.utils.CommonDataUtility;

/**
 * Created by Developer on 5/11/2017.
 */

public class HomeFragment extends BaseFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        activity.setSupportActionBar(toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setTypeface(CommonDataUtility.setTitleTypeFace(activity));
        toolbar_title.setText(getString(R.string.app_name));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.activity_action_notification)).setIcon(R.drawable.ico_notification_svg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
