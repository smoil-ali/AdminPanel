package com.techyasoft.adminpanel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techyasoft.adminpanel.Activities.ProfileDetailActivity;
import com.techyasoft.adminpanel.Activities.TourSheetActivity;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.databinding.DetailViewBinding;
import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.Tour;

import java.util.List;

public class ProfileDetailAdapter extends RecyclerView.Adapter<ProfileDetailAdapter.ViewHolder> {
    final String TAG = ProfileDetailAdapter.class.getSimpleName();
    Context context;
    List<Tour> list;

    public ProfileDetailAdapter(Context context, List<Tour> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailViewBinding binding = DetailViewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDetailAdapter.ViewHolder holder, int position) {
        Tour model = list.get(position);
        holder.binding.title.setText(model.getTour_number());
        holder.binding.title2.setText(model.getTotal_swipes());
        holder.binding.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, TourSheetActivity.class);
            intent.putExtra(Constants.PARAMS1,model);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DetailViewBinding binding;
        public ViewHolder(@NonNull DetailViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
