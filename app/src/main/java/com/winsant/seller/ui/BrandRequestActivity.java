package com.winsant.seller.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.winsant.seller.adapter.BrandCategoryListAdapter;
import com.winsant.seller.adapter.BrandListAdapter;
import com.winsant.seller.imgcrop.CropImage;
import com.winsant.seller.imgcrop.CropImageView;
import com.winsant.seller.kprogresshud.KProgressHUD;
import com.winsant.seller.model.BrandModel;
import com.winsant.seller.model.CategoryModel;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer on 5/11/2017.
 */

public class BrandRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private ImageView imgError;
    private ImageView imgBrandLogo, imgTrademark, imgDocument;
    private NestedScrollView ns_view;
    private TextView txtBrandName, txtCategoryName;
    private RadioButton rbManufacture, rbOwner, rbYes, rbNo;
    private CardView card_manufacture, card_owner;
    private EditText edtBrandWebSite;

    private ArrayList<BrandModel> BrandArrayList;
    private ArrayList<CategoryModel> CategoryArrayList;
    private KProgressHUD progressHUD;
    private AlertDialog alertdialog;

    private String strBrandLogo = "", strTrademark = "", strDocument = "";
    private String strBrandLogoName = "", strTrademarkName = "", strDocumentName = "";
    private String BrandId = "", CategoryId = "", type = "";
    private Bitmap bitmap;
    private Uri mCropImageUri;
    private TextView mToolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_request);

        activity = BrandRequestActivity.this;
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
        mToolbar_title.setText(getString(R.string.title_activity_brand_request));

        txtBrandName = (TextView) findViewById(R.id.txtBrandName);
        txtCategoryName = (TextView) findViewById(R.id.txtCategoryName);
        txtBrandName.setOnClickListener(this);
        txtCategoryName.setOnClickListener(this);

        edtBrandWebSite = (EditText) findViewById(R.id.edtBrandWebSite);

        imgBrandLogo = (ImageView) findViewById(R.id.imgBrandLogo);
        imgTrademark = (ImageView) findViewById(R.id.imgTrademark);
        imgDocument = (ImageView) findViewById(R.id.imgDocument);

        imgBrandLogo.setOnClickListener(this);
        imgTrademark.setOnClickListener(this);
        imgDocument.setOnClickListener(this);

        rbManufacture = (RadioButton) findViewById(R.id.rbManufacture);
        rbOwner = (RadioButton) findViewById(R.id.rbOwner);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);

        card_manufacture = (CardView) findViewById(R.id.card_manufacture);
        card_owner = (CardView) findViewById(R.id.card_owner);

        rbManufacture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    card_manufacture.setVisibility(View.VISIBLE);
                } else {
                    card_manufacture.setVisibility(View.GONE);
                }
            }
        });

        rbOwner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    card_owner.setVisibility(View.VISIBLE);
                } else {
                    card_owner.setVisibility(View.GONE);
                }
            }
        });

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        ns_view = (NestedScrollView) findViewById(R.id.ns_view);
        imgError = (ImageView) findViewById(R.id.imgError);
        getData();
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

            case R.id.imgBrandLogo:
                type = "logo";
                if (checkPermission())
                    onSelectImageClick();
                break;
            case R.id.imgTrademark:
                type = "trademark";
                if (checkPermission())
                    onSelectImageClick();
                break;
            case R.id.imgDocument:
                type = "document";
                if (checkPermission())
                    onSelectImageClick();
                break;

            case R.id.btnSubmit:

                if (CommonDataUtility.checkConnection(activity)) {

                    String message = CheckValidation();

                    if (message.equals("true")) {
                        brand_request();
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private String CheckValidation() {

        if (BrandId.equals("")) {
            return "Please select brand name";
        } else if (CategoryId.equals("")) {
            return "Please select category name";
        } else if (rbManufacture.isChecked()) {
            return ManufactureValidation();
        } else if (rbOwner.isChecked()) {
            return OwnerValidation();
        } else
            return "true";
    }

    private String ManufactureValidation() {
        if (strBrandLogo.equals("")) {
            return "Please select brand logo";
        } else if (strTrademark.equals("")) {
            return "Please select brand logo";
        } else return "true";
    }

    private String OwnerValidation() {
        if (strDocument.equals("")) {
            return "Please select authorization document";
        } else return "true";
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

        BrandArrayList = new ArrayList<>();
        CategoryArrayList = new ArrayList<>();

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
                StaticDataUtility.SERVER_URL + StaticDataUtility.BRAND_REGISTRY, obj,
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
                                        BrandArrayList.add(new BrandModel(res_cat_object.optString("brand_id"), res_cat_object.optString("brand_name")));
                                    }
                                }

                                if (jsonObject.has("cat_name")) {
                                    JSONArray cat_name = jsonObject.optJSONArray("cat_name");

                                    for (int i = 0; i < cat_name.length(); i++) {

                                        JSONObject get_cat_object = cat_name.optJSONObject(i);
                                        CategoryArrayList.add(new CategoryModel(get_cat_object.optString("category_id"), get_cat_object.optString("category_name"),
                                                get_cat_object.optString("friendly_url"), "0"));
                                    }
                                }

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

    private void brand_request() {

        progressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false).show();

        JSONObject obj = new JSONObject();
        try {

            obj.put("seller_id", MyApplication.getInstance().getPreferenceUtility().getSellerId());
            obj.put("token_id", MyApplication.getInstance().getPreferenceUtility().getToken());
            obj.put("category_id", CategoryId);
            obj.put("brand_name", txtBrandName.getText().toString());
            obj.put("brand_id", BrandId);
            obj.put("brand_relationship", rbManufacture.isChecked() ? "0" : "1");
            obj.put("upc", rbYes.isChecked() ? "Yes" : "No");
            obj.put("brand_website", edtBrandWebSite.getText().toString());

            obj.put("brand_logo_img_name", strBrandLogoName.replace(".jpg", "").replace(".png", "").replace(".jpeg", ""));
            obj.put("trad_doc_img_name", strTrademarkName.replace(".jpg", "").replace(".png", "").replace(".jpeg", ""));
            obj.put("auth_letter_img_name", strDocumentName.replace(".jpg", "").replace(".png", "").replace(".jpeg", ""));

            obj.put("brand_logo_img", "data:image/jpg;base64," + strBrandLogo);
            obj.put("trad_doc_img", "data:image/jpg;base64," + strTrademark);
            obj.put("auth_letter_img", "data:image/jpg;base64," + strDocument);

            System.out.println(StaticDataUtility.APP_TAG + " brand_request param --> " + obj.toString());

        } catch (Exception e) {
            System.out.println(StaticDataUtility.APP_TAG + " brand_request param error --> " + e.toString());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                StaticDataUtility.SERVER_URL + StaticDataUtility.BRAND_REQUEST, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(StaticDataUtility.APP_TAG + " brand_request response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String message = jsonObject.optString("message");
                            final String success = jsonObject.optString("success");

                            if (success.equals("1")) {

                                progressHUD.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                finish();

                            } else {

                                progressHUD.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            System.out.println(StaticDataUtility.APP_TAG + " error --> " + e.toString());
                            progressHUD.dismiss();
                            Toast.makeText(activity, "Something went wrong.Please try again later", Toast.LENGTH_SHORT).show();
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

    public void ShowBrandListDialog() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_list, null);

        ImageView imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        view.findViewById(R.id.tblBrand).setVisibility(View.VISIBLE);
        final EditText edtBrand = (EditText) view.findViewById(R.id.edtBrand);

        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtBrand.getText().toString().equals("")) {
                    Toast.makeText(activity, "Please enter brand name", Toast.LENGTH_SHORT).show();
                } else {
                    BrandId = "0";
                    txtBrandName.setText(edtBrand.getText().toString());
                    alertdialog.dismiss();
                }
            }
        });

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

        ImageView imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnOk.setVisibility(View.VISIBLE);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();

                String names = null;
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder categoryId = new StringBuilder();

                for (int i = 0; i < CategoryArrayList.size(); i++) {
                    if (CategoryArrayList.get(i).getIsChecked().equals("1")) {
                        stringBuilder.append(CategoryArrayList.get(i).getCategory_name()).append(",");
                        categoryId.append(CategoryArrayList.get(i).getCategory_id()).append(",");
                    }
                }

                CategoryId = categoryId.toString();
                names = stringBuilder.toString();

                if (names.endsWith(",")) {
                    names = names.substring(0, names.length() - 1);
                }
                if (CategoryId.endsWith(",")) {
                    CategoryId = CategoryId.substring(0, CategoryId.length() - 1);
                }

                txtCategoryName.setText(names);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new BrandCategoryListAdapter(activity, CategoryArrayList,
                new BrandCategoryListAdapter.onClickListener() {
                    @Override
                    public void onClick(int position, String category_id, String category_name, String friendly_url, String isChecked) {
                    }
                }));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertdialog = alertDialogBuilder.create();
        alertdialog.setView(view);
        alertdialog.setCancelable(false);
        alertdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertdialog.show();
    }

    public void onSelectImageClick() {
        if (CommonDataUtility.checkConnection(activity)) {
            CropImage.startPickImageActivity(activity);
        } else {
            Toast.makeText(activity,
                    activity.getString(R.string.no_internet),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(activity, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(activity, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                File file = new File(getRealPathFromURI(result.getUri()));
                bitmap = CommonDataUtility.decodeFile(file, 400, 400);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                if (bitmap != null) {

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);

                    if (type.equals("logo")) {
                        imgBrandLogo.setImageBitmap(bitmap);
                        strBrandLogo = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                        strBrandLogoName = file.getName();
                    }
                    if (type.equals("trademark")) {
                        imgTrademark.setImageBitmap(bitmap);
                        strTrademark = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                        strTrademarkName = file.getName();
                    }
                    if (type.equals("document")) {
                        imgDocument.setImageBitmap(bitmap);
                        strDocument = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                        strDocumentName = file.getName();
                    }
                } else {
                    Toast.makeText(activity, "problem in getting image.please try again!!", Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(activity, "cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int permissionCamera = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA);
            int readPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                boolean isCamera = perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean isStorage = perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                boolean isStorageWrite = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (isCamera && isStorage && isStorageWrite)
                    onSelectImageClick();
                else
                    Toast.makeText(activity, "Please grant both permission to work camera properly!!", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onSelectImageClick();
                else
                    Toast.makeText(activity, "Storage permission denied!!", Toast.LENGTH_SHORT).show();
                break;

            case 3:

                if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // required permissions granted, start crop image activity
                    startCropImageActivity(mCropImageUri);
                } else {
                    Toast.makeText(activity, "cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(activity);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
