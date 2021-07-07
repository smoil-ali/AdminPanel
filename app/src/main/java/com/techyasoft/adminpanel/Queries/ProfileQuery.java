package com.techyasoft.adminpanel.Queries;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.techyasoft.adminpanel.Connections.mConnection;
import com.techyasoft.adminpanel.Interfaces.ProfileQueryListener;
import com.techyasoft.adminpanel.model.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileQuery {
    final String TAG = ProfileQuery.class.getSimpleName();
    Context context;
    ProfileQueryListener listener;
    int size=0;
    String query;

    public ProfileQuery(Context context, ProfileQueryListener listener) {
        this.context = context;
        this.listener = listener;
        query ="select * from Profile";
    }

    public void executeQuery(){
        if (mConnection.connection!=null){
            try {
                Log.i(TAG,query);
                Statement statement=mConnection.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                List<Profile> list = new ArrayList<>();
                while (resultSet.next()){
                    Profile mProfile= new Profile();
                    mProfile.setId(resultSet.getString(1));
                    mProfile.setUserName(resultSet.getString(2));
                    mProfile.setPassword(resultSet.getString(3));
                    list.add(mProfile);
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.OnQuerySuccess(list);
                    }
                });
            } catch (SQLException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.OnQueryFail(e.getMessage());
                    }
                });
            }
        }else {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.OnQueryFail("Connection is null");
                }
            });
        }
    }
}
