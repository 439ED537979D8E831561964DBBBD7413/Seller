package com.winsant.seller.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
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
import com.winsant.seller.adapter.CategoryListAdapter;
import com.winsant.seller.kprogresshud.KProgressHUD;
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

public class CategoryFragment extends BaseFragment {

    private View rootView;
    private ImageView imgError;
    private NestedScrollView ns_view;
    private RecyclerView ApproveCategoriesList, RequestCategoriesList, FutureCategoriesList;

    private CardView cv_approve, cv_request, cv_future;

    private ArrayList<CategoryModel> ApproveCategoriesArrayList, RequestCategoriesArrayList, FutureCategoriesArrayList;
    private KProgressHUD progressHUD;
    private AlertDialog alertdialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        setUI();
        return rootView;
    }

    private void setUI() {

        ns_view = (NestedScrollView) rootView.findViewById(R.id.ns_view);

        cv_approve = (CardView) rootView.findViewById(R.id.cv_approve);
        cv_request = (CardView) rootView.findViewById(R.id.cv_request);
        cv_future = (CardView) rootView.findViewById(R.id.cv_future);

        ApproveCategoriesList = (RecyclerView) rootView.findViewById(R.id.ApproveCategoriesList);
        RequestCategoriesList = (RecyclerView) rootView.findViewById(R.id.RequestCategoriesList);
        FutureCategoriesList = (RecyclerView) rootView.findViewById(R.id.FutureCategoriesList);

        ApproveCategoriesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        RequestCategoriesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        FutureCategoriesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        imgError = (ImageView) rootView.findViewById(R.id.imgError);
        getData();
    }

    private void getData() {

        if (CommonDataUtility.checkConnection(activity)) {

            imgError.setVisibility(View.GONE);
            getCategoryData();

        } else {

            ns_view.setVisibility(View.GONE);
            imgError.setVisibility(View.VISIBLE);
            Glide.with(activity).load(R.drawable.no_wifi).into(imgError);
        }
    }

    private void getCategoryData() {

        ApproveCategoriesArrayList = new ArrayList<>();
        RequestCategoriesArrayList = new ArrayList<>();
        FutureCategoriesArrayList = new ArrayList<>();

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            System.out.println(StaticDataUtility.APP_TAG + " getCategoryData param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " getCategoryData param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.CATEGORY_APPROVE, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " getCategoryData response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                if (jsonObject.has("res_cat")) {
                                    JSONArray res_cat = jsonObject.optJSONArray("res_cat");

                                    for (int i = 0; i < res_cat.length(); i++) {

                                        JSONObject res_cat_object = res_cat.optJSONObject(i);

                                        if (res_cat_object.getString("request_status").equals("1")) {
                                            ApproveCategoriesArrayList.add(new CategoryModel(res_cat_object.optString("category_id"), res_cat_object.optString("category_name"),
                                                    res_cat_object.getString("request_status")));
                                        } else {
                                            RequestCategoriesArrayList.add(new CategoryModel(res_cat_object.optString("category_id"), res_cat_object.optString("category_name"),
                                                    res_cat_object.getString("request_status")));
                                        }
                                    }
                                }

                                if (jsonObject.has("get_cat")) {
                                    JSONArray get_cat = jsonObject.optJSONArray("get_cat");

                                    for (int i = 0; i < get_cat.length(); i++) {

                                        JSONObject get_cat_object = get_cat.optJSONObject(i);
                                        FutureCategoriesArrayList.add(new CategoryModel(get_cat_object.optString("category_id"), get_cat_object.optString("category_name"),
                                                "3"));
                                    }
                                }

                                setData();

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
        ns_view.setVisibility(View.GONE);
        imgError.setVisibility(View.VISIBLE);
        Glide.with(activity).load(R.drawable.no_data).into(imgError);
    }

    private void setData() {

        if (ApproveCategoriesArrayList.size() > 0) {

            cv_approve.setVisibility(View.VISIBLE);
            ApproveCategoriesList.setAdapter(new CategoryListAdapter(activity, ApproveCategoriesArrayList,
                    new CategoryListAdapter.onClickListener() {
                        @Override
                        public void onClick(int position, String category_id) {
                            category_request(category_id);
                        }

                        @Override
                        public void onStatusClick(String category_id) {

                        }
                    }));
        }

        if (RequestCategoriesArrayList.size() > 0) {

            cv_request.setVisibility(View.VISIBLE);
            RequestCategoriesList.setAdapter(new CategoryListAdapter(activity, RequestCategoriesArrayList,
                    new CategoryListAdapter.onClickListener() {
                        @Override
                        public void onClick(int position, String category_id) {
                            category_request(category_id);
                        }

                        @Override
                        public void onStatusClick(String category_id) {
                            category_status(category_id);
                        }
                    }));
        }

        if (FutureCategoriesArrayList.size() > 0) {

            cv_future.setVisibility(View.VISIBLE);
            FutureCategoriesList.setAdapter(new CategoryListAdapter(activity, FutureCategoriesArrayList,
                    new CategoryListAdapter.onClickListener() {
                        @Override
                        public void onClick(int position, String category_id) {
                            category_request(category_id);
                        }

                        @Override
                        public void onStatusClick(String category_id) {

                        }
                    }));
        }
    }

    private void category_request(String category_id) {

        ApproveCategoriesArrayList = new ArrayList<>();
        RequestCategoriesArrayList = new ArrayList<>();
        FutureCategoriesArrayList = new ArrayList<>();

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            obj.put("category_id", category_id);
            System.out.println(StaticDataUtility.APP_TAG + " category_request param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " category_request param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.CATEGORY_REQUEST, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " category_request response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                progressHUD.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                                getData();

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

    private void category_status(String category_id) {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            obj.put("category_id", category_id);
            System.out.println(StaticDataUtility.APP_TAG + " category_status param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " category_status param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.CATEGORY_STATUS, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " category_status response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                progressHUD.dismiss();

                                JSONObject req_info = jsonObject.optJSONObject("req_info");
                                ShowRequestStatus(req_info);

                            } else {

                                progressHUD.dismiss();
                                Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
                                noDataError();
                            }

                        } catch (Exception e) {
                            progressHUD.dismiss();
                            Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
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

    public void ShowRequestStatus(JSONObject req_info) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_category_status, null);

        TableRow tblRequestTime, tblAdminTime, tblComments;
        TextView categoryTitle, RequestId, RequestStatus, RequestTime, AdminTime, Comments;

        ImageView imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        tblRequestTime = (TableRow) view.findViewById(R.id.tblRequestTime);
        tblAdminTime = (TableRow) view.findViewById(R.id.tblAdminTime);
        tblComments = (TableRow) view.findViewById(R.id.tblComments);

        categoryTitle = (TextView) view.findViewById(R.id.categoryTitle);
        RequestId = (TextView) view.findViewById(R.id.RequestId);
        RequestStatus = (TextView) view.findViewById(R.id.RequestStatus);
        RequestTime = (TextView) view.findViewById(R.id.RequestTime);
        AdminTime = (TextView) view.findViewById(R.id.AdminTime);
        Comments = (TextView) view.findViewById(R.id.Comments);

        categoryTitle.setText(req_info.optString("category_name"));
        RequestId.setText(req_info.optString("request_id"));

        if (req_info.optString("request_status").equals("0"))
            RequestStatus.setText(getString(R.string.pending));
        else if (req_info.optString("request_status").equals("2"))
            RequestStatus.setText(getString(R.string.rejected));

        if (req_info.optString("comment").equals("") || req_info.optString("comment").equals("null")) {
            tblComments.setVisibility(View.GONE);
        } else {
            Comments.setText(req_info.optString("comment"));
        }

        if (req_info.optString("request_date_time").equals("") || req_info.optString("request_date_time").equals("null")) {
            tblRequestTime.setVisibility(View.GONE);
        } else {
            RequestTime.setText(req_info.optString("request_date_time"));
        }

        if (req_info.optString("admin_date_time").equals("") || req_info.optString("admin_date_time").equals("null")) {
            tblAdminTime.setVisibility(View.GONE);
        } else {
            AdminTime.setText(req_info.optString("admin_date_time"));
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertdialog = alertDialogBuilder.create();
        alertdialog.setView(view);
        alertdialog.setCancelable(false);
        alertdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertdialog.show();
    }
}
