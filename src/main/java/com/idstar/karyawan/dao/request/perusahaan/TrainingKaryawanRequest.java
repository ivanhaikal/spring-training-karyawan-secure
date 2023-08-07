package com.idstar.karyawan.dao.request.perusahaan;

import com.idstar.karyawan.validation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingKaryawanRequest {

    @NotNull
    private Long id;

    private KaryawanOnlyIdRequest karyawan;

    private TrainingOnlyIdRequest training;

    @NotBlank
    @ValidDate
    private String tanggal;
}
