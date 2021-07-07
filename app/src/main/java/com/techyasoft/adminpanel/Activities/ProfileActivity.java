package com.techyasoft.adminpanel.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.techyasoft.adminpanel.Adapters.ProfileAdapter;
import com.techyasoft.adminpanel.Interfaces.ProfileQueryListener;
import com.techyasoft.adminpanel.Queries.ProfileQuery;
import com.techyasoft.adminpanel.R;
import com.techyasoft.adminpanel.databinding.ActivityProfileBinding;
import com.techyasoft.adminpanel.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    final String TAG = ProfileActivity.class.getSimpleName();
    ActivityProfileBinding binding;
    ProfileAdapter adapter;
    List<Profile> list = new ArrayList<>();
    boolean isLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideUi();
        binding.recycler.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new ProfileAdapter(this,list);
        binding.recycler.setAdapter(adapter);

        binding.refresher.setOnRefreshListener(this::getData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoaded)
            getData();
    }

    void getData(){
        ProfileQuery profileQuery = new ProfileQuery(this,
                new ProfileQueryListener() {
                    @Override
                    public void OnQuerySuccess(List<Profile> profiles) {
                        Log.i(TAG,profiles.size()+" sise");
                        list.clear();
                        list.addAll(profiles);
                        if (list.size() > 0){
                            adapter.notifyItemInserted(profiles.size());
                            isLoaded = true;
                            binding.refresher.setRefreshing(false);
                            binding.alertTitle.setVisibility(View.GONE);
                        }else {
                            binding.alertTitle.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void OnQueryFail(String msg) {
                        Log.i(TAG,msg);
                        binding.alertTitle.setVisibility(View.VISIBLE);
                    }
                });

        new Thread(() -> profileQuery.executeQuery()).start();
    }

    public void hideUi(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}