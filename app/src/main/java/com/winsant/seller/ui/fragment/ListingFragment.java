package com.winsant.seller.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winsant.seller.R;

/**
 * Created by Developer on 5/11/2017.
 */

public class ListingFragment extends BaseFragment {

    private View rootView;
    private TextView addNewProduct;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        InitUI();
        return rootView;
    }

    private void InitUI() {
        addNewProduct = (TextView) rootView.findViewById(R.id.addNewProduct);
    }
}
