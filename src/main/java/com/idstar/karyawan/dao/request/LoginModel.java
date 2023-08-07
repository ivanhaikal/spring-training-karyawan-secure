package com.idstar.karyawan.dao.request;

import lombok.Data;

@Data
public class LoginModel {

    private String email;

    private String password;
}