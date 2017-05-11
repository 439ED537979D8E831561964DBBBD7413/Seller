package com.winsant.seller.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.ui.MyApplication;
import com.winsant.seller.utils.CommonDataUtility;

/**
 * Created by Developer on 5/11/2017.
 */

public class HomeFragment extends BaseFragment {

    private View rootView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bindView();

        return rootView;
    }

    private void bindView() {
        TextView txtCompanyName = (TextView) rootView.findViewById(R.id.txtCompanyName);
        txtCompanyName.setTypeface(CommonDataUtility.setHelveticaNeueHvTypeFace(activity));
        txtCompanyName.setText(MyApplication.getInstance().getPreferenceUtility().getCompanyName());
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
