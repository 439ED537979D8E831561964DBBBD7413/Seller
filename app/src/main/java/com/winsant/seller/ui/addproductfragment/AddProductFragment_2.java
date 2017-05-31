package com.winsant.seller.ui.addproductfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winsant.seller.R;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.utils.CommonDataUtility;

/**
 * Created by Developer on 5/11/2017.
 */

public class AddProductFragment_2 extends AddBaseFragment implements View.OnClickListener {

    private ImageView imgError;
    private LinearLayout ll_one;
    private TextView txtBrandName, txtCategoryName;
    private KProgressHUD progressHUD;
    private String BrandName = "", CategoryName = "";
    private RadioButton rbYes, rbNo;
    private RelativeLayout rl_add_product;

    private EditText edtSellerSKU, edtProductName, edtEAN, edtUPC, edtMPN, edtDescription;
    private String strSellerSKU = "", strProductName = "", strEAN = "", strUPC = "", strMPN = "", strDescription = "";

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_product_fragment_2, container, false);
        InitUI();
        return rootView;
    }

    private void InitUI() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            BrandName = bundle.getString("category_name");
            CategoryName = bundle.getString("brand_name");
        }

        activity.mToolbar_title.setText(R.string.mandatory_points);

        rl_add_product = (RelativeLayout) rootView.findViewById(R.id.rl_add_product);

        rbYes = (RadioButton) rootView.findViewById(R.id.rbYes);
        rbNo = (RadioButton) rootView.findViewById(R.id.rbNo);

        txtBrandName = (TextView) rootView.findViewById(R.id.txtBrandName);
        txtBrandName.setText(BrandName);

        txtCategoryName = (TextView) rootView.findViewById(R.id.txtCategoryName);
        txtCategoryName.setText(CategoryName);

        edtSellerSKU = (EditText) rootView.findViewById(R.id.edtSellerSKU);
        edtProductName = (EditText) rootView.findViewById(R.id.edtProductName);
        edtEAN = (EditText) rootView.findViewById(R.id.edtEAN);
        edtUPC = (EditText) rootView.findViewById(R.id.edtUPC);
        edtMPN = (EditText) rootView.findViewById(R.id.edtMPN);
        edtDescription = (EditText) rootView.findViewById(R.id.edtDescription);

        rootView.findViewById(R.id.btnNext).setOnClickListener(this);
        rootView.findViewById(R.id.btnPrevious).setOnClickListener(this);

        ll_one = (LinearLayout) rootView.findViewById(R.id.ll_one);
        imgError = (ImageView) rootView.findViewById(R.id.imgError);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnNext:

                if (CommonDataUtility.checkConnection(activity)) {

                    strSellerSKU = edtSellerSKU.getText().toString().trim();
                    strProductName = edtProductName.getText().toString().trim();
                    strDescription = edtDescription.getText().toString().trim();
                    strEAN = edtEAN.getText().toString().trim();
                    strUPC = edtUPC.getText().toString().trim();
                    strMPN = edtMPN.getText().toString().trim();

                    String message = checkValidation();

                    if (message.equals("true")) {

                    } else {
                        CommonDataUtility.showSnackBar(rl_add_product, message);
                    }

                } else {
                    Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnPrevious:
                activity.getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private String checkValidation() {

        if (strSellerSKU.equals(""))
            return "Please enter seller sku code";
        else if (strProductName.equals(""))
            return "Please enter product name";
        else if (strDescription.equals(""))
            return "Please enter product description";
        else if (!rbYes.isChecked() && !rbNo.isChecked())
            return "Please select product has variant or not";
        else return "true";
    }

//    private void getData() {
//
//        if (CommonDataUtility.checkConnection(activity)) {
//
//            imgError.setVisibility(View.GONE);
//            getCategoryBrandData();
//
//        } else {
//
//            ll_one.setVisibility(View.GONE);
//            imgError.setVisibility(View.VISIBLE);
//            Glide.with(activity).load(R.drawable.no_wifi).into(imgError);
//        }
//    }

//    private void getCategoryBrandData() {
//
//        BrandArrayList = new ArrayList<>();
//        CategoryArrayList = new ArrayList<>();
//
//        progressHUD = KProgressHUD.create(activity)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setCancellable(false).show();
//
//        JSONObject obj = new JSONObject();
//        try {
//
//            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
//            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
//            System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData param --> " + obj.toString());
//
//        } catch (Exception e) {
//            System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData param error --> " + e.toString());
//        }
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                StaticDataUtility.SERVER_URL + StaticDataUtility.ADD_P_CATEGORY_GET, obj,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        System.out.println(StaticDataUtility.APP_TAG + " getCategoryBrandData response --> " + response);
//
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response.toString());
//                            final String message = jsonObject.optString("message");
//                            final String success = jsonObject.optString("success");
//
//                            if (success.equals("1")) {
//
//                                if (jsonObject.has("brand_res")) {
//                                    JSONArray res_cat = jsonObject.optJSONArray("brand_res");
//
//                                    for (int i = 0; i < res_cat.length(); i++) {
//
//                                        JSONObject res_cat_object = res_cat.optJSONObject(i);
//                                        BrandArrayList.add(new BrandModel(res_cat_object.optString("brand_id"), res_cat_object.optString("brand_name")));
//                                    }
//                                }
//
//                                if (jsonObject.has("cat_res")) {
//                                    JSONArray cat_name = jsonObject.optJSONArray("cat_res");
//
//                                    for (int i = 0; i < cat_name.length(); i++) {
//
//                                        JSONObject get_cat_object = cat_name.optJSONObject(i);
//                                        CategoryArrayList.add(new CategoryModel(get_cat_object.optString("category_ids"), get_cat_object.optString("category"), "0"));
//                                    }
//                                }
//
//                                progressHUD.dismiss();
//
//                            } else {
//
//                                progressHUD.dismiss();
//                                Toast.makeText(activity, "Something problem, Try again!!", Toast.LENGTH_SHORT).show();
//                                noDataError();
//                            }
//
//                        } catch (Exception e) {
//                            System.out.println(StaticDataUtility.APP_TAG + " error --> " + e.toString());
//                            progressHUD.dismiss();
//                            noDataError();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressHUD.dismiss();
//                imgError.setVisibility(View.VISIBLE);
//
//                System.out.println(StaticDataUtility.APP_TAG + " error --> " + error.getMessage());
//
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Glide.with(activity).load(R.drawable.ico_wifi_off_svg).into(imgError);
//                } else {
//                    noDataError();
//                }
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//        };
//
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        // Adding request to request queue
//        Volley.newRequestQueue(activity).add(jsonObjReq);
//    }
//
//    private void noDataError() {
//        ll_one.setVisibility(View.GONE);
//        imgError.setVisibility(View.VISIBLE);
//        Glide.with(activity).load(R.drawable.no_data).into(imgError);
//    }
}