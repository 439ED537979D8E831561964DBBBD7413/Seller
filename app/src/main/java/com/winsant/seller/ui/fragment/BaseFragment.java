package com.winsant.seller.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.winsant.seller.ui.HomeActivity;


/**
 * Created by Developer on 2/15/2017.
 */

public class BaseFragment extends Fragment {

    public HomeActivity activity;
    private MenuItem cart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
    }
}
