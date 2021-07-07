package com.techyasoft.adminpanel;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.techyasoft.adminpanel.Activities.ProfileActivity;
import com.techyasoft.adminpanel.Connections.mConnection;
import com.techyasoft.adminpanel.Interfaces.mConnectionListener;
import com.techyasoft.adminpanel.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements mConnectionListener {


    final String TAG=MainActivity.class.getSimpleName();


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        hideUi();
        new mConnection(this,this);

    }

    public void hideUi(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void OnSuccess() {
        binding.text.setText("Success");
        openScreen();
    }

    @Override
    public void OnFail(String msg) {
        binding.text.setText(msg);
        binding.text.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }

    public void openScreen(){
        Intent intent=new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();

    }

}