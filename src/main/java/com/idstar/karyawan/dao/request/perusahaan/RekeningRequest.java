package com.idstar.karyawan.dao.request.perusahaan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RekeningRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nama;

    @NotBlank
    @Size(max = 255)
    private String jenis;

    @NotBlank
    @Size(max = 255)
    private String rekening;

    private KaryawanOnlyIdRequest karyawan;
}
