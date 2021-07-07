package com.techyasoft.adminpanel.Interfaces;



import com.techyasoft.adminpanel.model.History;
import com.techyasoft.adminpanel.model.Tour;

import java.util.List;

public interface GetHistoryQueryListener {
    void OnQuerySuccess(List<History> histories);
    void onNotFound();
    void OnQueryFail(String msg);
}
