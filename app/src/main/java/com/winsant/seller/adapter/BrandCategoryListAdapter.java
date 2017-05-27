package com.winsant.seller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.model.CategoryModel;
import com.winsant.seller.utils.CommonDataUtility;

import java.util.ArrayList;

public class BrandCategoryListAdapter extends RecyclerView.Adapter<BrandCategoryListAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<CategoryModel> categoryModelList;
    private onClickListener clickListener;
    private Intent intent;

    public BrandCategoryListAdapter(Activity activity, ArrayList<CategoryModel> categoryModelList, onClickListener clickListener) {
        this.activity = activity;
        this.categoryModelList = categoryModelList;
        this.clickListener = clickListener;
    }

    public interface onClickListener {
        void onClick(int position, String category_id, String category_name, String friendly_url, String isChecked);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle, RequestStatus;
        CheckBox checkBox;

        ViewHolder(final View itemView) {
            super(itemView);

            categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
            RequestStatus = (TextView) itemView.findViewById(R.id.RequestStatus);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            RequestStatus.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);

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

                    updateSelected(getAdapterPosition());
                }
            });
        }
    }

    private void updateSelected(int position) {

        CategoryModel categoryModel = categoryModelList.get(position);

        if (categoryModel.getIsChecked().equals("1")) {
            categoryModelList.set(position, new CategoryModel(categoryModel.getCategory_id(), categoryModel.getCategory_name(), categoryModel.getFriendly_url(), "0"));
        } else {
            categoryModelList.set(position, new CategoryModel(categoryModel.getCategory_id(), categoryModel.getCategory_name(), categoryModel.getFriendly_url(), "1"));
        }

        if (clickListener != null)
            clickListener.onClick(position, categoryModelList.get(position).getCategory_id(), categoryModelList.get(position).getCategory_name(),
                    categoryModelList.get(position).getRequest_status(), categoryModelList.get(position).getIsChecked());

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final CategoryModel categoryModel = categoryModelList.get(position);
        holder.categoryTitle.setText(categoryModel.getCategory_name());

        if (categoryModel.getIsChecked().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelected((int) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
}
