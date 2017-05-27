package com.winsant.seller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.winsant.seller.R;
import com.winsant.seller.model.CategoryModel;
import com.winsant.seller.utils.CommonDataUtility;
import com.winsant.seller.utils.StaticDataUtility;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<CategoryModel> categoryModelList;
    private onClickListener clickListener;
    private Intent intent;

    public CategoryListAdapter(Activity activity, ArrayList<CategoryModel> categoryModelList, onClickListener clickListener) {
        this.activity = activity;
        this.categoryModelList = categoryModelList;
        this.clickListener = clickListener;
    }

    public interface onClickListener {
        void onClick(int position, String category_id);

        void onStatusClick(String category_id);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle, RequestStatus;

        ViewHolder(final View itemView) {
            super(itemView);

            categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
            RequestStatus = (TextView) itemView.findViewById(R.id.RequestStatus);

            categoryTitle.setTypeface(CommonDataUtility.setRobotoRegularTypeFace(activity));
            RequestStatus.setTypeface(CommonDataUtility.setRobotoMediumTypeFace(activity));

            if (activity.getResources().getBoolean(R.bool.isLargeTablet)) {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                RequestStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            } else if (activity.getResources().getBoolean(R.bool.isTablet)) {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                RequestStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else {
                categoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                RequestStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        }
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

        switch (categoryModel.getRequest_status()) {
            case "0":
                holder.RequestStatus.setText(R.string.pending);
                break;
            case "1":
                holder.RequestStatus.setText(R.string.start_selling);
                break;
            case "2":
                holder.RequestStatus.setText(R.string.rejected);
                break;
            case "3":
                holder.RequestStatus.setText(R.string.apply_to_sell);
                break;
        }

        holder.RequestStatus.setTag(position);
        holder.RequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (int) v.getTag();

                System.out.println(StaticDataUtility.APP_TAG + " position --> " + pos);

                if (categoryModelList.get(pos).getRequest_status().equals("0")) {
                    if (clickListener != null)
                        clickListener.onStatusClick(categoryModelList.get(pos).getCategory_id());
                } else if (categoryModelList.get(pos).getRequest_status().equals("1")) {
                    Toast.makeText(activity, "Now you can start selling in this category", Toast.LENGTH_SHORT).show();
//                    if (clickListener != null)
//                        clickListener.onClick(pos, categoryModelList.get(pos).getCategory_id());
                } else if (categoryModelList.get(pos).getRequest_status().equals("2")) {
                    Toast.makeText(activity, "Your request has been rejected!!", Toast.LENGTH_SHORT).show();
//                    if (clickListener != null)
//                        clickListener.onClick(pos, categoryModelList.get(pos).getCategory_id());
                } else if (categoryModelList.get(pos).getRequest_status().equals("3")) {
                    if (clickListener != null)
                        clickListener.onClick(pos, categoryModelList.get(pos).getCategory_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
}
