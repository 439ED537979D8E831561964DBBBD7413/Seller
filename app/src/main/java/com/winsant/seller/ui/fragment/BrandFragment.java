package com.winsant.seller.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.winsant.seller.R;
import com.winsant.seller.ui.BrandRequestActivity;
import com.winsant.seller.ui.BrandStatusActivity;
import com.winsant.seller.utils.CommonDataUtility;

/**
 * Created by Developer on 5/11/2017.
 */

public class BrandFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_brand, container, false);
        setUI();
        return rootView;
    }

    private void setUI() {

        Button btnRequest = (Button) rootView.findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(this);

        Button btnStatus = (Button) rootView.findViewById(R.id.btnStatus);
        btnStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRequest:

                if (CommonDataUtility.checkConnection(activity)) {
                    startActivity(new Intent(activity, BrandRequestActivity.class));
                } else {
                    Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnStatus:

                if (CommonDataUtility.checkConnection(activity)) {
                    startActivity(new Intent(activity, BrandStatusActivity.class));
                } else {
                    Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
