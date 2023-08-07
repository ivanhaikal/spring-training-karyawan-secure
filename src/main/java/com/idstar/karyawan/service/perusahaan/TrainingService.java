package com.idstar.karyawan.service.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.TrainingOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.TrainingResponse;
import org.springframework.data.domain.Page;

public interface TrainingService {

    GeneralResponse<TrainingResponse> save(TrainingWithoutIdRequest request);

    GeneralResponse<TrainingResponse> update(TrainingRequest request);

    GeneralResponse<Page<TrainingResponse>> list(Integer page, Integer size);

    GeneralResponse<TrainingResponse> get(Long id);

    GeneralResponse<String> delete(TrainingOnlyIdRequest request);
}
