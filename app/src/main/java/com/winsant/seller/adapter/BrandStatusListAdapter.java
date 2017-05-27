package com.winsant.seller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.winsant.seller.R;
import com.winsant.seller.model.BrandStatusModel;

import java.util.ArrayList;

public class BrandStatusListAdapter extends RecyclerView.Adapter<BrandStatusListAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<BrandStatusModel> brandStatusModelList;
    private Intent intent;

    public BrandStatusListAdapter(Activity activity, ArrayList<BrandStatusModel> brandStatusModelList) {
        this.activity = activity;
        this.brandStatusModelList = brandStatusModelList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TableRow tblRequestTime, tblAdminTime, tblComments;
        TextView BrandTitle, categoryTitle, RequestId, RequestStatus, RequestTime, AdminTime, Comments;

        ViewHolder(final View itemView) {
            super(itemView);

            tblRequestTime = (TableRow) itemView.findViewById(R.id.tblRequestTime);
            tblAdminTime = (TableRow) itemView.findViewById(R.id.tblAdminTime);
            tblComments = (TableRow) itemView.findViewById(R.id.tblComments);

            BrandTitle = (TextView) itemView.findViewById(R.id.BrandTitle);
            categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
            RequestId = (TextView) itemView.findViewById(R.id.RequestId);
            RequestStatus = (TextView) itemView.findViewById(R.id.RequestStatus);
            RequestTime = (TextView) itemView.findViewById(R.id.RequestTime);
            AdminTime = (TextView) itemView.findViewById(R.id.AdminTime);
            Comments = (TextView) itemView.findViewById(R.id.Comments);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_status_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BrandStatusModel brandStatusModel = brandStatusModelList.get(position);

        holder.BrandTitle.setText(brandStatusModel.getBrand_name());
        holder.categoryTitle.setText(brandStatusModel.getCategory_name());
        holder.RequestId.setText(brandStatusModel.getBrand_request_id());

        if (brandStatusModel.getStatus().equals("0"))
            holder.RequestStatus.setText(activity.getString(R.string.pending));
        else if (brandStatusModel.getStatus().equals("2"))
            holder.RequestStatus.setText(activity.getString(R.string.rejected));

        if (brandStatusModel.getComment().equals("") || brandStatusModel.getComment().equals("null")) {
            holder.tblComments.setVisibility(View.GONE);
        } else {
            holder.Comments.setText(brandStatusModel.getComment());
        }

        if (brandStatusModel.getRequest_datetime().equals("") || brandStatusModel.getRequest_datetime().equals("null")) {
            holder.tblRequestTime.setVisibility(View.GONE);
        } else {
            holder.RequestTime.setText(brandStatusModel.getRequest_datetime());
        }

        if (brandStatusModel.getAdmin_datetime().equals("") || brandStatusModel.getAdmin_datetime().equals("null")) {
            holder.tblAdminTime.setVisibility(View.GONE);
        } else {
            holder.AdminTime.setText(brandStatusModel.getAdmin_datetime());
        }
    }

    @Override
    public int getItemCount() {
        return brandStatusModelList.size();
    }
}
