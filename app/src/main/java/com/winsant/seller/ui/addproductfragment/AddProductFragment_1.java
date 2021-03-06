package com.winsant.seller.ui.addproductfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.winsant.seller.adapter.BrandCategorySingleListAdapter;
import com.winsant.seller.adapter.BrandListAdapter;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.model.BrandModel;
import com.winsant.seller.model.CategoryModel;
import com.winsant.seller.ui.MyApplication;
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

public class AddProductFragment_1 extends AddBaseFragment implements View.OnClickListener {

    private ImageView imgError;
    private LinearLayout ll_one;
    private TextView txtBrandName, txtCategoryName;
    private ArrayList<BrandModel> BrandArrayList;
    private ArrayList<CategoryModel> CategoryArrayList;
    private KProgressHUD progressHUD;
    private AlertDialog alertdialog;
    private String BrandId = "", CategoryId = "";
    private RelativeLayout rl_add_product;

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_product_fragment_1, container, false);
        InitUI();
        return rootView;
    }

    private void InitUI() {

        activity.mToolbar_title.setText(getString(R.string.category));

        rl_add_product = (RelativeLayout) rootView.findViewById(R.id.rl_add_product);

        txtBrandName = (TextView) rootView.findViewById(R.id.txtBrandName);
        txtCategoryName = (TextView) rootView.findViewById(R.id.txtCategoryName);
        txtBrandName.setOnClickListener(this);
        txtCategoryName.setOnClickListener(this);
        rootView.findViewById(R.id.btnNext).setOnClickListener(this);

        ll_one = (LinearLayout) rootView.findViewById(R.id.ll_one);
        imgError = (ImageView) rootView.findViewById(R.id.imgError);
        getData();
    }

    private void getData() {

        if (CommonDataUtility.checkConnection(activity)) {

            imgError.setVisibility(View.GONE);
            getCategoryBrandData();

        } else {

            ll_one.setVisibility(View.GONE);
            imgError.setVisibility(View.VISIBLE);
            Glide.with(activity).load(R.drawable.no_wifi).into(imgError);
        }
    }

    private void getCategoryBrandData() {

        BrandArrayList = new ArrayList<>();
        CategoryArrayList = new ArrayList<>();

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.ADD_P_CATEGORY_GET, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                if (jsonObject.has("brand_res")) {
                                    JSONArray res_cat = jsonObject.optJSONArray("brand_res");

                                    for (int i = 0; i < res_cat.length(); i++) {

                                        JSONObject res_cat_object = res_cat.optJSONObject(i);
                                        BrandArrayList.add(new BrandModel(res_cat_object.optString("brand_id"), res_cat_object.optString("brand_name")));
                                    }
                                }

                                if (jsonObject.has("cat_res")) {
                                    JSONArray cat_name = jsonObject.optJSONArray("cat_res");

                                    for (int i = 0; i < cat_name.length(); i++) {

                                        JSONObject get_cat_object = cat_name.optJSONObject(i);
                                        CategoryArrayList.add(new CategoryModel(get_cat_object.optString("category_ids"), get_cat_object.optString("category"), "0"));
                                    }
                                }

                                progressHUD.dismiss();

                            } else {

                                progressHUD.dismiss();
                                CommonDataUtility.showSnackBar(rl_add_product, "Something problem, Try again!!");
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
        ll_one.setVisibility(View.GONE);
        imgError.setVisibility(View.VISIBLE);
        Glide.with(activity).load(R.drawable.no_data).into(imgError);
    }

    private void CheckCategoryBrand() {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            obj.put("category_ids", CategoryId);
            obj.put("brand_id", BrandId);

            System.out.println(StaticDataUtility.APP_TAG + " CheckCategoryBrand param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " CheckCategoryBrand param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.ADD_P_CATEGORY_POST, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " CheckCategoryBrand response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {
                                progressHUD.dismiss();

                                Fragment fragment = new AddProductFragment_2();

                                Bundle bundle = new Bundle();
                                bundle.putString("category_name", txtCategoryName.getText().toString());
                                bundle.putString("brand_name", txtBrandName.getText().toString());
                                fragment.setArguments(bundle);

                                MyApplication.getInstance().getPreferenceUtility().setString("product_id", jsonObject.optString("add_p_id"));

                                activity.getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, fragment).addToBackStack(null).commit();

                            } else {
                                progressHUD.dismiss();
                                CommonDataUtility.showSnackBar(rl_add_product, message);
                            }

                        } catch (Exception e) {
                            System.out.println(StaticDataUtility.APP_TAG + " error --> " + e.toString());
                            progressHUD.dismiss();
                            CommonDataUtility.showSnackBar(rl_add_product, "Something problem, Try again!!");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                System.out.println(StaticDataUtility.APP_TAG + " error --> " + error.getMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    CommonDataUtility.showSnackBar(rl_add_product, getString(R.string.no_internet));
                } else {
                    CommonDataUtility.showSnackBar(rl_add_product, "Server error!!Try again.");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtBrandName:
                if (BrandArrayList.size() > 0)
                    ShowBrandListDialog();
                break;
            case R.id.txtCategoryName:
                if (CategoryArrayList.size() > 0)
                    ShowCategoryListDialog();
                break;
            case R.id.btnNext:

                if (CommonDataUtility.checkConnection(activity)) {

                    if (CategoryId.equals("")) {
                        CommonDataUtility.showSnackBar(rl_add_product, "Please select category name");
                    } else if (BrandId.equals("")) {
                        CommonDataUtility.showSnackBar(rl_add_product, "Please select brand name");
                    } else {
                        CheckCategoryBrand();
                    }
                } else {
                    CommonDataUtility.showSnackBar(rl_add_product, getString(R.string.no_internet));
                }

                break;
        }
    }

    public void ShowBrandListDialog() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_list, null);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtTitle.setText("Select Brand");

        ImageView imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        view.findViewById(R.id.tblBrand).setVisibility(View.GONE);

        view.findViewById(R.id.btnOk).setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new BrandListAdapter(activity, BrandArrayList,
                new BrandListAdapter.onClickListener() {
                    @Override
                    public void onClick(String brand_id, String brand_name) {
                        BrandId = brand_id;
                        txtBrandName.setText(brand_name);
                        alertdialog.dismiss();
                    }
                }));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertdialog = alertDialogBuilder.create();
        alertdialog.setView(view);
        alertdialog.setCancelable(false);
        alertdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertdialog.show();
    }

    public void ShowCategoryListDialog() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_list, null);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtTitle.setText("Select Category");

        ImageView imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnOk.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new BrandCategorySingleListAdapter(activity, CategoryArrayList,
                new BrandCategorySingleListAdapter.onClickListener() {
                    @Override
                    public void onClick(int position, String category_id, String category_name) {
                        CategoryId = category_id;
                        txtCategoryName.setText(category_name);
                        alertdialog.dismiss();
                    }
                }));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertdialog = alertDialogBuilder.create();
        alertdialog.setView(view);
        alertdialog.setCancelable(false);
        alertdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertdialog.show();
    }
}