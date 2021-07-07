package com.techyasoft.adminpanel.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.techyasoft.adminpanel.Adapters.ProfileDetailAdapter;
import com.techyasoft.adminpanel.Adapters.TourSheetAdapter;
import com.techyasoft.adminpanel.Interfaces.GetHistoryQueryListener;
import com.techyasoft.adminpanel.Interfaces.GetTourQueryListener;
import com.techyasoft.adminpanel.Queries.GetHistoryQuery;
import com.techyasoft.adminpanel.Queries.GetTourQuery;
import com.techyasoft.adminpanel.R;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.Utils.Helper;
import com.techyasoft.adminpanel.databinding.ActivityTourSheetBinding;
import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.History;
import com.techyasoft.adminpanel.model.Tour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TourSheetActivity extends AppCompatActivity {

    final String TAG = TourSheetActivity.class.getSimpleName();
    ActivityTourSheetBinding binding;
    Tour guardDetail;
    List<History> list = new ArrayList<>();
    List keyList = new ArrayList<>();
    TourSheetAdapter adapter;
    boolean isLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        guardDetail = (Tour) getIntent().getExtras().getSerializable(Constants.PARAMS1);

        hideUi();
        binding.recycler.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new TourSheetAdapter(this,list);
        binding.recycler.setAdapter(adapter);

        binding.refresher.setOnRefreshListener(this::getData2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoaded)
            getData2();
    }

//    void getData(){
//        list.putAll(Helper.ConvertStringToHashMap(guardDetail.getDetails()));
//        keyList.addAll(Arrays.asList(list.keySet().toArray()));
//        Log.i(TAG,keyList.size()+ " size");
//        if (list.size() > 0){
//            adapter.notifyItemInserted(list.size());
//            isLoaded = true;
//            binding.alertTitle.setVisibility(View.GONE);
//        }else {
//            binding.alertTitle.setVisibility(View.VISIBLE);
//        }
//
//    }

    void getData2(){
        GetHistoryQuery query = new GetHistoryQuery(new GetHistoryQueryListener() {
            @Override
            public void OnQuerySuccess(List<History> histories) {
                Log.i(TAG,histories.size()+" sise");
                list.clear();
                list.addAll(histories);
                if (list.size() > 0){
                    adapter.notifyItemInserted(histories.size());
                    isLoaded = true;
                    binding.refresher.setRefreshing(false);
                    binding.alertTitle.setVisibility(View.GONE);
                }else {
                    binding.alertTitle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotFound() {
                Log.i(TAG,"Not found");
                binding.alertTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnQueryFail(String msg) {
                Log.i(TAG,msg);
                binding.alertTitle.setVisibility(View.VISIBLE);
            }
        },this,guardDetail);


        new Thread(new Runnable() {
            @Override
            public void run() {
                query.executeQuery();
            }
        }).start();
    }

    public void hideUi(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}