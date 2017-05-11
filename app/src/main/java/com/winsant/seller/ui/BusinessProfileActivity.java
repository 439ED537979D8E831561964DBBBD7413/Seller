package com.winsant.seller.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.utils.CommonDataUtility;

public class BusinessProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
//    private LinearLayout ll_account, ll_business;
    private TextView mToolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_business);

        activity = BusinessProfileActivity.this;

        setupToolbar();
    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            mToolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        }
        mToolbar_title.setTypeface(CommonDataUtility.setHelveticaNeueHvTypeFace(activity));

        mToolbar_title.setText(R.string.business_profile_setting);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ico_arrow_back_svg);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

//    private void setupUI() {
//
//        TextView txtAccount = (TextView) findViewById(R.id.txtAccount);
//        TextView txtAccountHint = (TextView) findViewById(R.id.txtAccountHint);
//        TextView txtBusiness = (TextView) findViewById(R.id.txtBusiness);
//        TextView txtBusinessHint = (TextView) findViewById(R.id.txtBusinessHint);
//        TextView txtProfileTitle = (TextView) findViewById(R.id.txtProfileTitle);
//
//        txtProfileTitle.setTypeface(CommonDataUtility.setHelveticaNeueHvTypeFace(activity));
//        txtAccount.setTypeface(CommonDataUtility.setHelveticaLightTypeFace(activity));
//        txtAccountHint.setTypeface(CommonDataUtility.setHelveticaLightTypeFace(activity));
//        txtBusiness.setTypeface(CommonDataUtility.setHelveticaLightTypeFace(activity));
//        txtBusinessHint.setTypeface(CommonDataUtility.setHelveticaLightTypeFace(activity));
//
//        if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {
//
//            txtProfileTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
//            txtAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            txtAccountHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            txtBusiness.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            txtBusinessHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//
//        } else if (activity.getResources().getBoolean(R.bool.isTablet)) {
//
//            txtProfileTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//            txtAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            txtAccountHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//            txtBusiness.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            txtBusinessHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//
//        } else {
//
//            txtProfileTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//            txtAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            txtAccountHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//            txtBusiness.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            txtBusinessHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        }
//
//        ll_account = (LinearLayout) findViewById(R.id.ll_account);
//        ll_account.setOnClickListener(this);
//        ll_business = (LinearLayout) findViewById(R.id.ll_business);
//        ll_business.setOnClickListener(this);
//
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_account:

                startActivity(new Intent(activity, BusinessProfileActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.ll_business:

                startActivity(new Intent(activity, BusinessProfileActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
