package com.techyasoft.adminpanel.Interfaces;


import com.techyasoft.adminpanel.model.Profile;

import java.util.List;

public interface ProfileQueryListener {
    void OnQuerySuccess(List<Profile> profiles);
    void OnQueryFail(String msg);
}
