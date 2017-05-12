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

public class BusinessProfileActivity extends AppCompatActivity {

    private Activity activity;
    private TextView mToolbar_title;

    private TextView txtBusinessName;
    private TextView txtBusinessType;
    private TextView txtPanCard;
    private TextView txtTinNumber;
    private TextView txtMobileNumber;
    private TextView txtTanNumber;
    private KProgressHUD progressHUD;
    private TextView txtHolderName;
    private TextView txtAccountNumber;
    private TextView txtBankName;
    private TextView txtBranchName;
    private TextView txtIFSCCode;
    private TextView txtAddressProof;
    private TextView txtCancelCheque;
    private ImageView imgError;

    private NestedScrollView ns_business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_business);

        activity = BusinessProfileActivity.this;

        setupToolbar();
        setupUI();
    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            mToolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        }
        mToolbar_title.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

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

    private void setupUI() {

        imgError = (ImageView) findViewById(R.id.imgError);
        ns_business = (NestedScrollView) findViewById(R.id.ns_business);

        TextView BusinessName = (TextView) findViewById(R.id.BusinessName);
        txtBusinessName = (TextView) findViewById(R.id.txtBusinessName);
        TextView BusinessType = (TextView) findViewById(R.id.BusinessType);
        txtBusinessType = (TextView) findViewById(R.id.txtBusinessType);
        TextView PanCard = (TextView) findViewById(R.id.PanCard);
        txtPanCard = (TextView) findViewById(R.id.txtPanCard);
        TextView TinNumber = (TextView) findViewById(R.id.TinNumber);
        txtTinNumber = (TextView) findViewById(R.id.txtTinNumber);
        TextView mobile = (TextView) findViewById(R.id.Mobile);
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        TextView TanNumber = (TextView) findViewById(R.id.TanNumber);
        txtTanNumber = (TextView) findViewById(R.id.txtTanNumber);

        TextView HolderName = (TextView) findViewById(R.id.HolderName);
        txtHolderName = (TextView) findViewById(R.id.txtHolderName);
        TextView AccountNumber = (TextView) findViewById(R.id.AccountNumber);
        txtAccountNumber = (TextView) findViewById(R.id.txtAccountNumber);
        TextView BankName = (TextView) findViewById(R.id.BankName);
        txtBankName = (TextView) findViewById(R.id.txtBankName);
        TextView BranchName = (TextView) findViewById(R.id.BranchName);
        txtBranchName = (TextView) findViewById(R.id.txtBranchName);
        TextView IFSCCode = (TextView) findViewById(R.id.IFSCCode);
        txtIFSCCode = (TextView) findViewById(R.id.txtIFSCCode);
        TextView AddressProof = (TextView) findViewById(R.id.AddressProof);
        txtAddressProof = (TextView) findViewById(R.id.txtAddressProof);
        TextView CancelCheque = (TextView) findViewById(R.id.CancelCheque);
        txtCancelCheque = (TextView) findViewById(R.id.txtCancelCheque);

        BusinessName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtBusinessName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        BusinessType.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtBusinessType.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        PanCard.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtPanCard.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        TinNumber.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtTinNumber.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        mobile.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtMobileNumber.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        TanNumber.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtTanNumber.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

        HolderName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtHolderName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        AccountNumber.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtAccountNumber.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        BankName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtBankName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        BranchName.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtBranchName.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        IFSCCode.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtIFSCCode.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        AddressProof.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtAddressProof.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));
        CancelCheque.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
        txtCancelCheque.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

        if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {

            HolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtHolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            AccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtAccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            BankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtBankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            BranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtBranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            IFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtIFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            AddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtAddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            CancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtCancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            txtPanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtTinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtTanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            PanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            TinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            TanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            BusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtBusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            BusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtBusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (activity.getResources().getBoolean(R.bool.isTablet)) {

            HolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtHolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            AccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtAccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            BankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtBankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            BranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtBranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            IFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtIFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            AddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtAddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            CancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtCancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            txtPanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtTinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtTanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            PanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            TinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            TanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            BusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtBusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            BusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtBusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        } else {

            HolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtHolderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            AccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtAccountNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            BankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtBankName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            BranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtBranchName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            IFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtIFSCCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            AddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtAddressProof.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            CancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtCancelCheque.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            txtPanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtTinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtTanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            PanCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            TinNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            mobile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            TanNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            BusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtBusinessName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            BusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            txtBusinessType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }

        if (CommonDataUtility.checkConnection(activity)) {
            imgError.setVisibility(View.GONE);
            ns_business.setVisibility(View.VISIBLE);
            getBusinessDetails();
        } else {
            imgError.setVisibility(View.VISIBLE);
            ns_business.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.ico_wifi_off_svg).into(imgError);
        }
    }

    private void getBusinessDetails() {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            System.out.println(StaticDataUtility.APP_TAG + " getBusinessDetails param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " getBusinessDetails param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.ACCOUNT_DETAIL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " getBusinessDetails response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                JSONObject seller_bank = jsonObject.optJSONObject("seller_bank");

                                txtHolderName.setText(seller_bank.optString("acc_holder"));
                                txtBankName.setText(seller_bank.optString("bank_name"));
                                txtBranchName.setText(seller_bank.optString("branch"));
                                txtAccountNumber.setText(seller_bank.optString("acc_no"));
                                txtIFSCCode.setText(seller_bank.optString("ifsc_code"));
                                txtAddressProof.setText(seller_bank.optString("address_proof_image").equals("") ? "Not available" : "Verified");
                                txtCancelCheque.setText(seller_bank.optString("cancel_cheque").equals("") ? "Not available" : " Verified");

                                JSONObject seller_business = jsonObject.optJSONObject("seller_business");

                                txtBusinessName.setText(seller_business.optString("business_name"));
                                txtBusinessType.setText(seller_business.optString("business_type"));
                                txtMobileNumber.setText(seller_business.optString("phone_number"));
                                txtPanCard.setText(seller_business.optString("pan").equals("") ? seller_business.optString("business_pan")
                                        : seller_business.optString("pan"));
                                txtTinNumber.setText(seller_business.optString("vat_tin").equals("") ? seller_business.optString("not_tin_reson")
                                        : seller_business.optString("vat_tin"));
                                txtTanNumber.setText(seller_business.optString("tan").equals("") ? seller_business.optString("not_tan")
                                        : seller_business.optString("tan"));

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
        ns_business.setVisibility(View.GONE);
        imgError.setVisibility(View.VISIBLE);
        Glide.with(activity).load(R.drawable.no_data).into(imgError);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
