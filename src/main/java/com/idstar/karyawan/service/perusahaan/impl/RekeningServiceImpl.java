package com.idstar.karyawan.service.perusahaan.impl;

import com.idstar.karyawan.dao.request.perusahaan.RekeningOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.RekeningRequest;
import com.idstar.karyawan.dao.request.perusahaan.RekeningWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.KaryawanOnlyIdNamaResponse;
import com.idstar.karyawan.dao.response.RekeningResponse;
import com.idstar.karyawan.model.perusahaan.Karyawan;
import com.idstar.karyawan.model.perusahaan.Rekening;
import com.idstar.karyawan.repository.perusahaan.KaryawanRepository;
import com.idstar.karyawan.repository.perusahaan.RekeningRepository;
import com.idstar.karyawan.service.ValidationService;
import com.idstar.karyawan.service.perusahaan.RekeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class RekeningServiceImpl implements RekeningService {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    @Override
    public GeneralResponse<RekeningResponse> save(RekeningWithoutIdRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());

        Karyawan karyawan = karyawanRepository.findById(request.getKaryawan().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID karyawan not found")
        );

        Rekening rekening = new Rekening();
        rekening.setNama(request.getNama());
        rekening.setJenis(request.getJenis());
        rekening.setRekening(request.getRekening());
        rekening.setKaryawan(karyawan);
        rekeningRepository.save(rekening);

        return new GeneralResponse<>(HttpStatus.OK.value(), rekeningResponse(rekening), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @Override
    public GeneralResponse<RekeningResponse> update(RekeningRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());

        Rekening rekening = rekeningRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID rekening not found")
        );

        Karyawan karyawan = karyawanRepository.findById(request.getKaryawan().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID karyawan not found")
        );

        rekening.setNama(request.getNama());
        rekening.setJenis(request.getJenis());
        rekening.setRekening(request.getRekening());
        rekening.setKaryawan(karyawan);
        rekeningRepository.save(rekening);

        return new GeneralResponse<>(HttpStatus.OK.value(), rekeningResponse(rekening), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<Page<RekeningResponse>> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Rekening> rekenings = rekeningRepository.findAll(pageable);
        return new GeneralResponse<>(HttpStatus.OK.value(),
                rekenings.map(this::rekeningResponse),
                HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<RekeningResponse> get(Long id) {
        Rekening rekening = rekeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID rekening not found")
        );

        return new GeneralResponse<>(HttpStatus.OK.value(), rekeningResponse(rekening), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @Override
    public GeneralResponse<String> delete(RekeningOnlyIdRequest request) {
        validationService.validate(request);

        Rekening rekening = rekeningRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID rekening not found")
        );

        rekening.setDeletedDate(new Date());
        rekeningRepository.save(rekening);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success delete", HttpStatus.OK.getReasonPhrase());
    }

    public RekeningResponse rekeningResponse(Rekening rekening) {
        return new RekeningResponse(rekening.getCreatedDate(), rekening.getUpdatedDate(), rekening.getDeletedDate(),
                rekening.getId(), rekening.getNama(), rekening.getJenis(), rekening.getRekening(),
                new KaryawanOnlyIdNamaResponse(rekening.getKaryawan().getId(), rekening.getKaryawan().getNama()));
    }
}
