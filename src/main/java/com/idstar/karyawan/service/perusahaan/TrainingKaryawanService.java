package com.idstar.karyawan.service.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.TrainingKaryawanResponse;
import org.springframework.data.domain.Page;

public interface TrainingKaryawanService {

    GeneralResponse<TrainingKaryawanResponse> save(TrainingKaryawanWithoutIdRequest request);

    GeneralResponse<TrainingKaryawanResponse> update(TrainingKaryawanRequest request);

    GeneralResponse<Page<TrainingKaryawanResponse>> list(Integer page, Integer size);

    GeneralResponse<TrainingKaryawanResponse> get(Long id);

    GeneralResponse<String> delete(TrainingKaryawanOnlyIdRequest request);
}
