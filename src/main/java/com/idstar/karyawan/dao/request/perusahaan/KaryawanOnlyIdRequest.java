package com.idstar.karyawan.dao.request.perusahaan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KaryawanOnlyIdRequest {

    @NotNull
    private Long id;
}
