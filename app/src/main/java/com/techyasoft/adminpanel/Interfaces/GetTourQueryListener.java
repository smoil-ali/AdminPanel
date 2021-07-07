package com.techyasoft.adminpanel.Interfaces;



import com.techyasoft.adminpanel.model.Tour;

import java.util.List;

public interface GetTourQueryListener {
    void OnQuerySuccess(List<Tour> tours);
    void onNotFound();
    void OnQueryFail(String msg);
}
