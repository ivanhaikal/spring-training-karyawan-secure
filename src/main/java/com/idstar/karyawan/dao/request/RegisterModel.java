package com.idstar.karyawan.dao.request;


import com.idstar.karyawan.validation.anotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Data
public class RegisterModel {
    public Long id;

    public String email;

    @ValidPassword
    @NotEmpty(message = "password is mandatory")
    public String password;

    public String name;

    public String gender;

    public String domicile;

    public String phone_number;

    public String interest;

    public Date tanggalLahir;

    public String tempatLahir;

    public String namaIbuKandung;
}