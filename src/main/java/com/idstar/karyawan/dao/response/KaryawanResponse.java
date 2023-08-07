package com.idstar.karyawan.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KaryawanResponse {

    private Date createdDate;

    private Date updatedDate;

    private Date deletedDate;

    private Long id;

    private String nama;

    private Date dob;

    private String status;

    private String alamat;

    private DetailKaryawanResponse detailKaryawan;
}
