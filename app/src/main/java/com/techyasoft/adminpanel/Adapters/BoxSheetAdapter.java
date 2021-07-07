package com.techyasoft.adminpanel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techyasoft.adminpanel.databinding.ItemSheetBinding;
import com.techyasoft.adminpanel.databinding.ItemViewBinding;

import java.util.HashMap;
import java.util.List;

public class BoxSheetAdapter extends RecyclerView.Adapter<BoxSheetAdapter.ViewHolder> {
    final String TAG = BoxSheetAdapter.class.getSimpleName();
    Context context;
    List<String> list;

    public BoxSheetAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(context),
                parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.binding.title.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       ItemViewBinding binding;
        public ViewHolder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
