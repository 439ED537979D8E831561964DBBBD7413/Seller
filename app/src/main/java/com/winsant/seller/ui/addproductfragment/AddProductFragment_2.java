package com.winsant.seller.ui.addproductfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.winsant.seller.R;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.ui.MyApplication;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Developer on 5/11/2017.
 */

public class AddProductFragment_2 extends AddBaseFragment implements View.OnClickListener {

    private KProgressHUD progressHUD;
    private String BrandName = "", CategoryName = "";
    private RadioButton rbYes, rbNo;
    private LinearLayout rl_add_product;

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

        rl_add_product = (LinearLayout) rootView.findViewById(R.id.rl_add_product);

        rbYes = (RadioButton) rootView.findViewById(R.id.rbYes);
        rbNo = (RadioButton) rootView.findViewById(R.id.rbNo);

        TextView txtBrandName = (TextView) rootView.findViewById(R.id.txtBrandName);
        txtBrandName.setText(BrandName);

        TextView txtCategoryName = (TextView) rootView.findViewById(R.id.txtCategoryName);
        txtCategoryName.setText(CategoryName);

        edtSellerSKU = (EditText) rootView.findViewById(R.id.edtSellerSKU);
        edtProductName = (EditText) rootView.findViewById(R.id.edtProductName);
        edtEAN = (EditText) rootView.findViewById(R.id.edtEAN);
        edtUPC = (EditText) rootView.findViewById(R.id.edtUPC);
        edtMPN = (EditText) rootView.findViewById(R.id.edtMPN);
        edtDescription = (EditText) rootView.findViewById(R.id.edtDescription);

        rootView.findViewById(R.id.btnNext).setOnClickListener(this);

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
                        AddProductDetailsData();
                    } else {
                        CommonDataUtility.showSnackBar(rl_add_product, message);
                    }

                } else {
                    CommonDataUtility.showSnackBar(rl_add_product, getString(R.string.no_internet));
                }

                break;

//            case R.id.btnPrevious:
//                activity.getSupportFragmentManager().popBackStack();
//                break;
        }
    }

    private String checkValidation() {

        if (strSellerSKU.equals(""))
            return "Please enter seller sku code";
        else if (strProductName.equals(""))
            return "Please enter product name";
        else if (strDescription.equals(""))
            return "Please enter product description";
        else if (strDescription.length() < 120)
            return "Please enter minimum 120 character for product description";
        else if (!rbYes.isChecked() && !rbNo.isChecked())
            return "Please select product has variant or not";
        else return "true";
    }

    private void AddProductDetailsData() {


        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            obj.put("add_p_id", MyApplication.getInstance().getPreferenceUtility().getString("product_id"));
            obj.put("product_name", strProductName);
            obj.put("seller_sku", strSellerSKU);
            obj.put("product_detail", strDescription);
            obj.put("is_variant", rbYes.isChecked() ? "1" : "0");
            obj.put("EAN", strEAN);
            obj.put("UPC", strUPC);
            obj.put("MPN", strMPN);

            System.out.println(StaticDataUtility.APP_TAG + " AddProductDetailsData param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " AddProductDetailsData param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.ADD_P_DETAIL_POST, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " AddProductDetailsData response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {
                                progressHUD.dismiss();
                                activity.getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, new AddProductFragment_2()).addToBackStack(null).commit();
                            } else {

                                progressHUD.dismiss();
                                CommonDataUtility.showSnackBar(rl_add_product, message);
                            }

                        } catch (Exception e) {
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
                    CommonDataUtility.showSnackBar(rl_add_product, "Something problem, Try again!!");
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
}