package com.techyasoft.adminpanel.Interfaces;

import com.techyasoft.adminpanel.model.GuardDetail;
import com.techyasoft.adminpanel.model.Profile;

import java.util.List;

public interface ProfileDetailQueryListener {
    void OnQuerySuccess(List<GuardDetail> detailList);
    void OnQueryFail(String msg);
}
