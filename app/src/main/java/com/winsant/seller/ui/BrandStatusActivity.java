package com.winsant.seller.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.winsant.seller.adapter.BrandStatusListAdapter;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.model.BrandStatusModel;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Developer on 5/11/2017.
 */

public class BrandStatusActivity extends AppCompatActivity {

    private Activity activity;
    private ImageView imgError;
    private RecyclerView brandStatusList;

    private ArrayList<BrandStatusModel> BrandStatusModelList;
    private KProgressHUD progressHUD;

    private TextView mToolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_status);

        activity = BrandStatusActivity.this;
        setUI();
    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            mToolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        }

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

        mToolbar_title.setTypeface(CommonDataUtility.setHelveticaNeueHvTypeFace(activity));
        mToolbar_title.setText(getString(R.string.title_activity_brand_status));

        imgError = (ImageView) findViewById(R.id.imgError);

        brandStatusList = (RecyclerView) findViewById(R.id.brandStatusList);
        brandStatusList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        getData();
    }

    private void getData() {

        if (CommonDataUtility.checkConnection(activity)) {

            imgError.setVisibility(View.GONE);
            getBrandStatusData();

        } else {

            imgError.setVisibility(View.VISIBLE);
            brandStatusList.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.no_wifi).into(imgError);
        }
    }

    private void getBrandStatusData() {

        BrandStatusModelList = new ArrayList<>();

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            System.out.println(StaticDataUtility.APP_TAG + " getBrandStatusData param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " getBrandStatusData param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.BRAND_APPROVAL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " getBrandStatusData response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                if (jsonObject.has("req_info")) {
                                    JSONArray req_info = jsonObject.optJSONArray("req_info");

                                    for (int i = 0; i < req_info.length(); i++) {

                                        JSONObject req_info_object = req_info.optJSONObject(i);

                                        BrandStatusModelList.add(new BrandStatusModel(req_info_object.optString("brand_request_id"), req_info_object.optString("brand_name")
                                                , req_info_object.optString("category_name"), req_info_object.optString("status"), req_info_object.optString("request_datetime")
                                                , req_info_object.optString("admin_datetime"), req_info_object.optString("comment")));
                                    }
                                }

                                brandStatusList.setAdapter(new BrandStatusListAdapter(activity, BrandStatusModelList));
                                progressHUD.dismiss();

                            } else {

                                progressHUD.dismiss();
                                Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
                                noDataError();
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
        brandStatusList.setVisibility(View.GONE);
        imgError.setVisibility(View.VISIBLE);
        Glide.with(activity).load(R.drawable.no_data).into(imgError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
