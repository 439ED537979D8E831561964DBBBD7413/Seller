package com.winsant.seller.ui.addproductfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.winsant.seller.ui.AddNewProductActivity;
import com.winsant.seller.ui.HomeActivity;


/**
 * Created by Developer on 2/15/2017.
 */

public class AddBaseFragment extends Fragment {

    public AddNewProductActivity activity;
    private MenuItem cart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AddNewProductActivity) getActivity();
    }
}
