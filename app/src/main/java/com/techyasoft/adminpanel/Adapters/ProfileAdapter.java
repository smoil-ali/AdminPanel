package com.techyasoft.adminpanel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.techyasoft.adminpanel.Activities.ProfileDetailActivity;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.databinding.ItemViewBinding;
import com.techyasoft.adminpanel.model.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHodler> {
    final String TAG = ProfileAdapter.class.getSimpleName();
    Context context;
    List<Profile> list;

    public ProfileAdapter(Context context, List<Profile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
       Profile profile = list.get(position);
       holder.binding.title.setText(profile.getUserName());

       holder.binding.container.setOnClickListener(v -> {
           Intent intent = new Intent(context, ProfileDetailActivity.class);
           intent.putExtra(Constants.PARAMS1,profile);
           context.startActivity(intent);
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        ItemViewBinding binding;
        public ViewHodler(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
