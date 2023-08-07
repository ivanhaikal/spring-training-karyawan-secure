package com.idstar.karyawan.dao.request.perusahaan;

import com.idstar.karyawan.validation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingKaryawanWithoutIdRequest {

    private KaryawanOnlyIdRequest karyawan;

    private TrainingOnlyIdRequest training;

    @NotBlank
    @ValidDate
    private String tanggal;
}
