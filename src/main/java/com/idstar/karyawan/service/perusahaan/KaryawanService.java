package com.idstar.karyawan.service.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.KaryawanOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.KaryawanRequest;
import com.idstar.karyawan.dao.request.perusahaan.KaryawanWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.KaryawanResponse;
import org.springframework.data.domain.Page;

public interface KaryawanService {

    GeneralResponse<KaryawanResponse> save(KaryawanWithoutIdRequest request);

    GeneralResponse<KaryawanResponse> update(KaryawanRequest request);

    GeneralResponse<Page<KaryawanResponse>> list(Integer page, Integer size);

    GeneralResponse<KaryawanResponse> get(Long id);

    GeneralResponse<String> delete(KaryawanOnlyIdRequest request);
}
