package com.techyasoft.adminpanel.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.techyasoft.adminpanel.Adapters.ProfileAdapter;
import com.techyasoft.adminpanel.Adapters.ProfileDetailAdapter;
import com.techyasoft.adminpanel.Interfaces.GetTourQueryListener;
import com.techyasoft.adminpanel.Interfaces.ProfileDetailQueryListener;
import com.techyasoft.adminpanel.Interfaces.ProfileQueryListener;
import com.techyasoft.adminpanel.Queries.GetTourQuery;
import com.techyasoft.adminpanel.Queries.ProfileDetailQuery;
import com.techyasoft.adminpanel.Queries.ProfileQuery;
import com.techyasoft.adminpanel.Utils.Constants;
import com.techyasoft.adminpanel.databinding.ActivityProfileDetailBinding;
import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.Profile;
import com.techyasoft.adminpanel.model.Tour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileDetailActivity extends AppCompatActivity {

    final String TAG = ProfileDetailActivity.class.getSimpleName();
    ActivityProfileDetailBinding binding;
    Profile profile;
    ProfileDetailAdapter adapter;
    List<Tour> list = new ArrayList<>();
    boolean isLoaded;
    SimpleDateFormat sdf;
    String yy,mm,dd;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profile = (Profile) getIntent().getExtras().getSerializable(Constants.PARAMS1);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());

        hideUi();
        binding.recycler.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new ProfileDetailAdapter(this,list);
        binding.recycler.setAdapter(adapter);

        binding.refresher.setOnRefreshListener(this::getData2);



        binding.dateContainer.setOnClickListener(v -> {
            showDatePicker();
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoaded)
            getData2();
    }

//    void getData(){
//        ProfileDetailQuery profileDetailQuery = new ProfileDetailQuery(this, new ProfileDetailQueryListener() {
//            @Override
//            public void OnQuerySuccess(List<GuardDetail> detailList) {
//                Log.i(TAG,detailList.size()+" sise");
//                list.clear();
//                list.addAll(detailList);
//                if (list.size() > 0){
//                    adapter.notifyItemInserted(detailList.size());
//                    isLoaded = true;
//                    binding.refresher.setRefreshing(false);
//                    binding.alertTitle.setVisibility(View.GONE);
//                }else {
//                    binding.alertTitle.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            @Override
//            public void OnQueryFail(String msg) {
//                binding.alertTitle.setVisibility(View.VISIBLE);
//                Log.i(TAG,msg);
//            }
//        },Integer.parseInt(profile.getId()));
//
//        new Thread(() -> profileDetailQuery.executeQuery()).start();
//    }

    void getData2(){
        GetTourQuery query = new GetTourQuery(new GetTourQueryListener() {
            @Override
            public void OnQuerySuccess(List<Tour> tours) {
                Log.i(TAG,tours.size()+" sise");
                list.clear();
                list.addAll(tours);
                if (list.size() > 0){
                    adapter.notifyItemInserted(tours.size());
                    isLoaded = true;
                    binding.refresher.setRefreshing(false);
                    binding.recycler.setVisibility(View.VISIBLE);
                    binding.alertTitle.setVisibility(View.GONE);
                }else {
                    binding.alertTitle.setVisibility(View.VISIBLE);
                    binding.recycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNotFound() {
                Log.i(TAG,"Not found");
                binding.alertTitle.setVisibility(View.VISIBLE);
                binding.recycler.setVisibility(View.GONE);
            }

            @Override
            public void OnQueryFail(String msg) {
                Log.i(TAG,msg);
                binding.alertTitle.setVisibility(View.VISIBLE);
                binding.recycler.setVisibility(View.GONE);
            }
        },this,profile.getId(),date);


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

    void showDatePicker(){
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(ProfileDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int m, int d) {
                yy = year+"";
                m+=1;
                mm = m < 10 ? "0"+m: m+"";
                dd = d < 10 ? "0"+d: d+"";
                date = yy+"-"+mm+"-"+dd;
                Log.i(TAG,date);
                binding.dateTitle.setText(date);
                getData2();
            }
        },year,month,day);
        dialog.setTitle("Select date");
        dialog.show();
    }
}