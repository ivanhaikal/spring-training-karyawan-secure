package com.idstar.karyawan.service;

import com.idstar.karyawan.dao.request.LoginModel;
import com.idstar.karyawan.dao.request.RegisterModel;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel);

    public Map login(LoginModel objLogin);

    public Map getDetailProfile(Principal principal);

}
