package com.idstar.karyawan.dao.request.perusahaan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingWithoutIdRequest {

    @NotBlank
    @Size(max = 255)
    private String tema;

    @NotBlank
    @Size(max = 255)
    private String pengajar;
}
