package com.techyasoft.adminpanel.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.techyasoft.adminpanel.Adapters.BoxSheetAdapter;
import com.techyasoft.adminpanel.Adapters.TourSheetAdapter;
import com.techyasoft.adminpanel.R;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.Utils.Helper;
import com.techyasoft.adminpanel.databinding.ActivityBoxSheetBinding;
import com.techyasoft.adminpanel.model.GuardDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BoxSheetActivity extends AppCompatActivity {

    final String TAG = BoxSheetActivity.class.getSimpleName();
    ActivityBoxSheetBinding binding;
    GuardDetail guardDetail;
    int key;
    BoxSheetAdapter adapter;
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoxSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        guardDetail = (GuardDetail) getIntent().getExtras().getSerializable(Constants.PARAMS1);
        key = getIntent().getExtras().getInt(Constants.PARAMS2);
        Log.i(TAG,key + "");

        hideUi();
        binding.recycler.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new BoxSheetAdapter(this,list);
        binding.recycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    void getData(){
        HashMap<Integer,List<String>> hashMap = Helper.ConvertStringToList(guardDetail.getBox());
        if (hashMap != null){
            if (hashMap.size() > 0){
                List<String> stringList = hashMap.get(key);
                if (stringList != null){
                    list.addAll(stringList);
                    binding.alertTitle.setVisibility(View.GONE);
                    adapter.notifyItemInserted(list.size());
                }else {
                    binding.alertTitle.setVisibility(View.VISIBLE);
                }

            }else {
                binding.alertTitle.setVisibility(View.VISIBLE);
            }
        }else {
            binding.alertTitle.setVisibility(View.VISIBLE);
        }


    }

    public void hideUi(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}