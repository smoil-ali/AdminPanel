package com.techyasoft.adminpanel.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techyasoft.adminpanel.Activities.BoxSheetActivity;
import com.techyasoft.adminpanel.Activities.TourSheetActivity;
import com.techyasoft.adminpanel.Interfaces.CheckCardNumerQueryListener;
import com.techyasoft.adminpanel.Queries.CheckCardNumberQuery;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.databinding.ItemSheetBinding;
import com.techyasoft.adminpanel.model.CardData;
import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.History;

import java.util.HashMap;
import java.util.List;

public class TourSheetAdapter extends RecyclerView.Adapter<TourSheetAdapter.ViewHolder> {
    final String TAG = TourSheetAdapter.class.getSimpleName();
    Context context;
    List<History> list;

    public TourSheetAdapter(Context context, List<History> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSheetBinding binding = ItemSheetBinding.inflate(LayoutInflater.from(context),
                parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TourSheetAdapter.ViewHolder holder, int position) {
       History history = list.get(position);
       getInfo(history.getCard_number(),holder);
        holder.binding.title.setText(history.getCard_number());
        holder.binding.title2.setText(history.getComment());

//        holder.binding.container.setOnClickListener(v -> {
//            Intent intent = new Intent(context, BoxSheetActivity.class);
//            intent.putExtra(Constants.PARAMS1,guardDetail);
//            intent.putExtra(Constants.PARAMS2,key);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSheetBinding binding;
        public ViewHolder(@NonNull ItemSheetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    void getInfo(String cardNumber,ViewHolder holder){

        CheckCardNumberQuery query = new CheckCardNumberQuery(context, new CheckCardNumerQueryListener() {
            @Override
            public void onFound(CardData data) {
                Log.i(TAG,data.toString());
                holder.binding.title3.setText(data.getBox_name());
            }

            @Override
            public void onNotFound() {
                Log.i(TAG,"Not Found");
            }

            @Override
            public void onFailed(String msg) {
                Log.i(TAG,msg);
            }
        },cardNumber);
        new Thread(new Runnable() {
            @Override
            public void run() {
                query.executeQuery();
            }
        }).start();
    }
}
