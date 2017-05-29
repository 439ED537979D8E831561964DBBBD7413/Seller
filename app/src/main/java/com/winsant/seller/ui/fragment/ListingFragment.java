package com.winsant.seller.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.ui.AddNewProductActivity;

/**
 * Created by Developer on 5/11/2017.
 */

public class ListingFragment extends BaseFragment {

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        InitUI();
        return rootView;
    }

    private void InitUI() {
        TextView addNewProduct = (TextView) rootView.findViewById(R.id.addNewProduct);
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, AddNewProductActivity.class));
            }
        });
    }
}
