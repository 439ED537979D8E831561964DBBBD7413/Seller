package com.winsant.seller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.model.BrandModel;
import com.winsant.seller.utils.CommonDataUtility;

import java.util.ArrayList;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<BrandModel> brandModelList;
    private onClickListener clickListener;
    private Intent intent;

    public BrandListAdapter(Activity activity, ArrayList<BrandModel> brandModelList, onClickListener clickListener) {
        this.activity = activity;
        this.brandModelList = brandModelList;
        this.clickListener = clickListener;
    }

    public interface onClickListener {
        void onClick(String brand_id, String brand_name);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle, RequestStatus;

        ViewHolder(final View itemView) {
            super(itemView);

            categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
            RequestStatus = (TextView) itemView.findViewById(R.id.RequestStatus);
            RequestStatus.setVisibility(View.GONE);

            categoryTitle.setGravity(Gravity.CENTER);
            categoryTitle.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));

            if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else if (activity.getResources().getBoolean(R.bool.isTablet)) {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            } else {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (clickListener != null)
                        clickListener.onClick(brandModelList.get(getAdapterPosition()).getBrand_id(), brandModelList.get(getAdapterPosition()).getBrand_name());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BrandModel brandModel = brandModelList.get(position);
        holder.categoryTitle.setText(brandModel.getBrand_name());
    }

    @Override
    public int getItemCount() {
        return brandModelList.size();
    }
}
