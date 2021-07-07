package com.techyasoft.adminpanel.Queries;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.techyasoft.adminpanel.Connections.mConnection;
import com.techyasoft.adminpanel.Interfaces.GetHistoryQueryListener;
import com.techyasoft.adminpanel.Interfaces.GetTourQueryListener;
import com.techyasoft.adminpanel.model.History;
import com.techyasoft.adminpanel.model.Tour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetHistoryQuery {
    final String TAG = GetHistoryQuery.class.getSimpleName();
    GetHistoryQueryListener listener;
    Context context;
    String query;

    public GetHistoryQuery(GetHistoryQueryListener listener, Context context, Tour tour) {
        this.listener = listener;
        this.context = context;
        int tour_number = Integer.parseInt(tour.getTour_number()) - 1;
        query = "select * from History " +
                "where guard_id='"+tour.getGuard_id()+"' and date='"+tour.getDate()+"' and tour_counter='"+tour_number+"';";
    }

    public void executeQuery(){
        if (mConnection.connection!=null){
            try {
                Log.i(TAG,query);
                Statement statement=mConnection.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                List<History> histories = new ArrayList<>();
                while (resultSet.next()){
                    History data = new History();
                    data.setId(resultSet.getString(1));
                    data.setGuard_id(resultSet.getString(2));
                    data.setCard_number(resultSet.getString(3));
                    data.setDate(resultSet.getString(4));
                    data.setComment(resultSet.getString(5));
                    data.setTour_counter(resultSet.getString(6));
                    histories.add(data);
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.OnQuerySuccess(histories);
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
