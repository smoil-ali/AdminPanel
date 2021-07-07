package com.techyasoft.adminpanel.Queries;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.techyasoft.adminpanel.Connections.mConnection;
import com.techyasoft.adminpanel.Interfaces.CheckCardNumerQueryListener;
import com.techyasoft.adminpanel.model.CardData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckCardNumberQuery {
    final String TAG=CheckCardNumberQuery.class.getSimpleName();
    Context context;
    CheckCardNumerQueryListener listener;
    int size=0;
    String query;

    public CheckCardNumberQuery(Context context, CheckCardNumerQueryListener listener, String cardNumber) {
        this.context = context;
        this.listener = listener;
        query="select * from CardData"+
        "where card_number='"+cardNumber+"'";
    }

    public void executeQuery(){
        if (mConnection.connection!=null){
            try {
                Log.i(TAG,query);
                Statement statement=mConnection.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                CardData data =new CardData();
                while (resultSet.next()){
                    size++;
                    data.setId(resultSet.getString(1));
                    data.setBox_name(resultSet.getString(2));
                    data.setCard_number(resultSet.getString(3));
                }
                if (size>0){
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFound(data);
                        }
                    });
                }else {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onNotFound();
                        }
                    });
                }

            } catch (SQLException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailed(e.getMessage());
                    }
                });
            }
        }else {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onFailed("Connection is null");
                }
            });
        }
    }
}
