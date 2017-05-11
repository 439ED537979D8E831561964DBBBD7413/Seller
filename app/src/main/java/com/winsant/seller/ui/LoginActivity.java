package com.winsant.seller.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.winsant.seller.R;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private EditText edtUserId, edtPassword;
    private String strUserId, strPassword;
    private Button btnLogin;
    private LinearLayout ll_login;
    private KProgressHUD progressHUD;
    private TextView mToolbar_title;
    private ProgressDialog pDialog;

    private boolean mIntentInProgress;

    private boolean isReadPhone = false, isReadAccount = false;
    private int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            mToolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        }
        mToolbar_title.setTypeface(CommonDataUtility.setTitleTypeFace(activity));
        mToolbar_title.setText(getString(R.string.title_activity_login));

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

        ll_login = (LinearLayout) findViewById(R.id.ll_login);

        edtUserId = (EditText) findViewById(R.id.edtUserId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        edtUserId.setTypeface(CommonDataUtility.setTypeFace1(activity));
        edtPassword.setTypeface(CommonDataUtility.setTypeFace1(activity));

        TextView txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        txtForgotPass.setTypeface(CommonDataUtility.setTypeFace1(activity));

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTypeface(CommonDataUtility.setTypeFace1(activity));

        if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {

            edtUserId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            edtPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtForgotPass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (activity.getResources().getBoolean(R.bool.isTablet)) {

            edtUserId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            edtPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            txtForgotPass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        } else {

            edtUserId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            edtPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            txtForgotPass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }

        txtForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private boolean checkPermission() {

        int permissionReadPhoneState = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        int accountPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        } else {
            isReadPhone = true;
        }
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        } else {
            isReadAccount = true;
        }
        if (!listPermissionsNeeded.isEmpty()) {
            Toast.makeText(activity, "please grant all this permission to work all functionality properly", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_PERMISSION);
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            // START_INCLUDE(permission_result)

            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);

            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);

            // Check for READ_PHONE_STATE
            if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                isReadPhone = true;

            } else {
                isReadPhone = false;
                CommonDataUtility.showSnackBar(ll_login, "Phone read permission was NOT granted.");
            }

            // Check for GET_ACCOUNTS
            if (perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                isReadAccount = true;

            } else {
                isReadAccount = false;
                CommonDataUtility.showSnackBar(ll_login, "Google Account read permission was NOT granted.");
            }

            if (!isReadPhone && !isReadAccount) {
                checkPermission();
            }

            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtForgotPass:

                startActivity(new Intent(activity, ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.btnLogin:

                strUserId = edtUserId.getText().toString();
                strPassword = edtPassword.getText().toString();

                if (CommonDataUtility.checkConnection(activity)) {

                    String message = LoginValidation();

                    if (message.equals("true")) {
                        Login();
                    } else {
                        CommonDataUtility.showSnackBar(ll_login, message);
                    }

                } else {
                    CommonDataUtility.showSnackBar(ll_login, getString(R.string.no_internet));
                }

                break;
        }
    }

    private String LoginValidation() {

        if (strUserId.equals(""))
            return "Please enter valid Email/Mobile Number";
        else if (strUserId.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(strUserId).matches())
            return "Please enter valid Email";
        else if (!strUserId.contains("@") && !Patterns.PHONE.matcher(strUserId).matches())
            return "Please enter valid Mobile number";
        else if (strPassword.equals(""))
            return "Please enter password";
        else
            return "true";
    }

    private void Login() {

        JSONObject obj = new JSONObject();
        try {

            obj.put("username", strUserId);
            obj.put("password", strPassword);
            System.out.println(StaticDataUtility.APP_TAG + " Login param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " Login param error --> " + e.toString());
        }

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.LOGIN, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " Login response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                JSONObject data = jsonObject.optJSONObject("data");

                                MyApplication.getInstance().getPreferenceUtility().setLogin(true);
                                MyApplication.getInstance().getPreferenceUtility().setUserId(data.optString("user_id"));
                                MyApplication.getInstance().getPreferenceUtility().setEmail(data.optString("email"));
                                MyApplication.getInstance().getPreferenceUtility().setFirstName(data.optString("first_name"));
                                MyApplication.getInstance().getPreferenceUtility().setLastName(data.optString("last_name"));
                                MyApplication.getInstance().getPreferenceUtility().setMobileNumber(data.optString("mobile_number"));
                                MyApplication.getInstance().getPreferenceUtility().setString("mobile_verify", data.optString("is_otp_verified"));
                                MyApplication.getInstance().getPreferenceUtility().setString("is_password_set", data.optString("is_password_set"));

                                progressHUD.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                progressHUD.dismiss();
                                CommonDataUtility.showSnackBar(ll_login, message);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressHUD.dismiss();
                            CommonDataUtility.showSnackBar(ll_login, "Something problem while login,Try again later!!!");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                CommonDataUtility.showSnackBar(ll_login, "Something problem while login,Try again later!!!");
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

    private void SignUp() {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("username", strUserId);
            obj.put("password", strPassword);
            System.out.println(StaticDataUtility.APP_TAG + " SignUp param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " SignUp param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.REGISTER, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " SignUp response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                JSONObject data = jsonObject.optJSONObject("data");

                                MyApplication.getInstance().getPreferenceUtility().setLogin(true);
                                MyApplication.getInstance().getPreferenceUtility().setUserId(data.optString("user_id"));
                                MyApplication.getInstance().getPreferenceUtility().setEmail(data.optString("email"));
                                MyApplication.getInstance().getPreferenceUtility().setFirstName(data.optString("first_name"));
                                MyApplication.getInstance().getPreferenceUtility().setLastName(data.optString("last_name"));
                                MyApplication.getInstance().getPreferenceUtility().setMobileNumber(data.optString("mobile_number"));
                                MyApplication.getInstance().getPreferenceUtility().setString("mobile_verify", data.optString("is_otp_verified"));
                                MyApplication.getInstance().getPreferenceUtility().setString("is_password_set", data.optString("is_password_set"));
                                // MyApplication.getInstance().getPreferenceUtility().setInt("total_cart", data.optString("total_cart"));

                                progressHUD.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                progressHUD.dismiss();
                                CommonDataUtility.showSnackBar(ll_login, message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressHUD.dismiss();
                            CommonDataUtility.showSnackBar(ll_login, "Something problem while Sign Up,Try again later!!!");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                CommonDataUtility.showSnackBar(ll_login, "Something problem while Sign Up,Try again later!!!");
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
}
