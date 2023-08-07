package com.idstar.karyawan.service.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.RekeningOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.RekeningRequest;
import com.idstar.karyawan.dao.request.perusahaan.RekeningWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.RekeningResponse;
import org.springframework.data.domain.Page;

public interface RekeningService {

    GeneralResponse<RekeningResponse> save(RekeningWithoutIdRequest request);

    GeneralResponse<RekeningResponse> update(RekeningRequest request);

    GeneralResponse<Page<RekeningResponse>> list(Integer page, Integer size);

    GeneralResponse<RekeningResponse> get(Long id);

    GeneralResponse<String> delete(RekeningOnlyIdRequest request);
}
