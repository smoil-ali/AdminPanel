package com.techyasoft.adminpanel.Interfaces;


import com.techyasoft.adminpanel.model.CardData;

public interface CheckCardNumerQueryListener {
    void onFound(CardData data);
    void onNotFound();
    void onFailed(String msg);
}
