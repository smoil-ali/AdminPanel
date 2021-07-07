package com.techyasoft.adminpanel.Queries;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.techyasoft.adminpanel.Connections.mConnection;
import com.techyasoft.adminpanel.Interfaces.ProfileDetailQueryListener;
import com.techyasoft.adminpanel.Interfaces.ProfileQueryListener;
import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileDetailQuery {
    final String TAG = ProfileDetailQuery.class.getSimpleName();
    Context context;
    ProfileDetailQueryListener listener;
    int id;
    String query;

    public ProfileDetailQuery(Context context, ProfileDetailQueryListener listener, int id) {
        this.context = context;
        this.listener = listener;
        this.id = id;
        query = "select * from Guard_Detail " +
                "where guard_id='"+id+"';";

    }

    public void executeQuery(){
        if (mConnection.connection!=null){
            try {
                Log.i(TAG,query);
                Statement statement=mConnection.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                List<GuardDetail> list = new ArrayList<>();
                while (resultSet.next()){
                    GuardDetail model = new GuardDetail();
                    model.setGuard_id(resultSet.getString(1));
                    model.setDetails(resultSet.getString(2));
                    model.setBox(resultSet.getString(3));
                    model.setDate(resultSet.getString(4));
                    model.setTour_counter(resultSet.getString(5));
                    list.add(model);
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
