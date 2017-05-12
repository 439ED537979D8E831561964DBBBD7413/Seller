package com.winsant.seller.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.winsant.seller.R;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    private Activity activity;
    private TextView mToolbar_title;

    private TextView txtStoreName;
    private TextView txtStoreDesc;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtMobileNumber;
    private TextView txtEmailAddress;
    private KProgressHUD progressHUD;
    private ImageView imgError;
    private NestedScrollView ns_account;
    private TextView txtPinCode;
    private TextView txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);

        activity = UserProfileActivity.this;

        setupToolbar();
        setupUI();
    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            mToolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        }
        mToolbar_title.setTypeface(CommonDataUtility.setHelveticaNeueHvTypeFace(activity));

        mToolbar_title.setText(R.string.seller_profile_setting);

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

    private void setupUI() {

        imgError = (ImageView) findViewById(R.id.imgError);
        ns_account = (NestedScrollView) findViewById(R.id.ns_account);

        TextView txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        txtStoreName = (TextView) findViewById(R.id.txtStoreName);
        TextView txtStore = (TextView) findViewById(R.id.txtStore);
        txtStoreDesc = (TextView) findViewById(R.id.txtStoreDesc);

        TextView firstName = (TextView) findViewById(R.id.FirstName);
        txtFirstName = (TextView) findViewById(R.id.txtFirstName);
        TextView lastName = (TextView) findViewById(R.id.LastName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        TextView mobile = (TextView) findViewById(R.id.Mobile);
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        TextView email = (TextView) findViewById(R.id.Email);
        txtEmailAddress = (TextView) findViewById(R.id.txtEmailAddress);
        TextView pincode = (TextView) findViewById(R.id.PinCode);
        txtPinCode = (TextView) findViewById(R.id.txtPinCode);
        TextView address = (TextView) findViewById(R.id.Address);
        txtAddress = (TextView) findViewById(R.id.txtAddress);

        txtDisplay.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtStoreName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        txtStore.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtStoreDesc.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

        firstName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtFirstName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        lastName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtLastName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        mobile.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtMobileNumber.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        email.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtEmailAddress.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        pincode.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtPinCode.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        address.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtAddress.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

        if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {

            txtFirstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtLastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtEmailAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtPinCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            firstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            lastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            pincode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            txtDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtStoreName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtStore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtStoreDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (activity.getResources().getBoolean(R.bool.isTablet)) {

            txtFirstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtLastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtEmailAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtPinCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            firstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            lastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            pincode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            txtDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtStoreName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtStore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtStoreDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        } else {

            txtFirstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtLastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtEmailAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtPinCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            firstName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            lastName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            pincode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            txtDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtStoreName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtStore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtStoreDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }

        if (CommonDataUtility.checkConnection(activity)) {
            imgError.setVisibility(View.GONE);
            ns_account.setVisibility(View.VISIBLE);
            getUserProfileDetails();
        } else {
            imgError.setVisibility(View.VISIBLE);
            ns_account.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.ico_wifi_off_svg).into(imgError);
        }
    }

    private void getUserProfileDetails() {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            System.out.println(StaticDataUtility.APP_TAG + " getUserProfileDetails param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " getUserProfileDetails param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.PERSONAL_DETAIL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " getUserProfileDetails response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                JSONObject personal = jsonObject.optJSONObject("personal");

                                txtFirstName.setText(personal.optString("first_name"));
                                txtLastName.setText(personal.optString("last_name"));
                                txtMobileNumber.setText(personal.optString("mobile"));
                                txtEmailAddress.setText(personal.optString("email"));
                                txtPinCode.setText(personal.optString("zipcode"));

                                JSONObject seller_store = jsonObject.optJSONObject("seller_store");

                                txtStoreName.setText(seller_store.optString("store_name"));
                                txtStoreDesc.setText(seller_store.optString("business_detail"));
                                txtAddress.setText(seller_store.optString("add1") + " " + seller_store.optString("add2") + " " + seller_store.optString("add3"));

                                progressHUD.dismiss();

                            } else {

                                progressHUD.dismiss();
                                Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } catch (Exception e) {
                            System.out.println(StaticDataUtility.APP_TAG + " error --> " + e.toString());
                            progressHUD.dismiss();
                            noDataError();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                imgError.setVisibility(View.VISIBLE);

                System.out.println(StaticDataUtility.APP_TAG + " error --> " + error.getMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Glide.with(activity).load(R.drawable.ico_wifi_off_svg).into(imgError);
                } else {
                    noDataError();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        Volley.newRequestQueue(activity).add(jsonObjReq);
    }

    private void noDataError() {
        ns_account.setVisibility(View.GONE);
        imgError.setVisibility(View.VISIBLE);
        Glide.with(activity).load(R.drawable.no_data).into(imgError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
