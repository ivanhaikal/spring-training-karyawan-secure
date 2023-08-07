package com.idstar.karyawan.dao.request;

import lombok.Data;

@Data
public class UserUpdateModel {

    public Long id;
    public String email;
    public String otp;
    public String username;
    public String newPassword;
    public Boolean enabled;

}
