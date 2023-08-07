package com.idstar.karyawan.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingKaryawanResponse {

    private Date createdDate;

    private Date updatedDate;

    private Date deletedDate;

    private Long id;

    private KaryawanResponse karyawan;

    private TrainingResponse training;

    private Date tanggal;
}
