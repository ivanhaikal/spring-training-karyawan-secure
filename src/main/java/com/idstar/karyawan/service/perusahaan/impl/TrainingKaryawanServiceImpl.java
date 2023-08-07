package com.idstar.karyawan.service.perusahaan.impl;

import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanWithoutIdRequest;
import com.idstar.karyawan.dao.response.*;
import com.idstar.karyawan.model.perusahaan.Karyawan;
import com.idstar.karyawan.model.perusahaan.Training;
import com.idstar.karyawan.model.perusahaan.TrainingKaryawan;
import com.idstar.karyawan.repository.perusahaan.KaryawanRepository;
import com.idstar.karyawan.repository.perusahaan.TrainingKaryawanRepository;
import com.idstar.karyawan.repository.perusahaan.TrainingRepository;
import com.idstar.karyawan.service.ValidationService;
import com.idstar.karyawan.service.perusahaan.TrainingKaryawanService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TrainingKaryawanServiceImpl implements TrainingKaryawanService {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingKaryawanRepository trainingKaryawanRepository;

    @Autowired
    private ValidationService validationService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Transactional
    @SneakyThrows
    @Override
    public GeneralResponse<TrainingKaryawanResponse> save(TrainingKaryawanWithoutIdRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());
        validationService.validate(request.getTraining());

        Karyawan karyawan = karyawanRepository.findById(request.getKaryawan().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID karyawan not found")
        );

        Training training = trainingRepository.findById(request.getTraining().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training not found")
        );

        TrainingKaryawan trainingKaryawan = new TrainingKaryawan();
        trainingKaryawan.setTanggal(dateFormat.parse(request.getTanggal()));
        trainingKaryawan.setKaryawan(karyawan);
        trainingKaryawan.setTraining(training);
        trainingKaryawanRepository.save(trainingKaryawan);

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingKaryawanResponse(trainingKaryawan), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @SneakyThrows
    @Override
    public GeneralResponse<TrainingKaryawanResponse> update(TrainingKaryawanRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());
        validationService.validate(request.getTraining());

        TrainingKaryawan trainingKaryawan = trainingKaryawanRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training karyawan not found")
        );

        Karyawan karyawan = karyawanRepository.findById(request.getKaryawan().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID karyawan not found")
        );

        Training training = trainingRepository.findById(request.getTraining().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training not found")
        );

        trainingKaryawan.setTanggal(dateFormat.parse(request.getTanggal()));
        trainingKaryawan.setKaryawan(karyawan);
        trainingKaryawan.setTraining(training);
        trainingKaryawanRepository.save(trainingKaryawan);

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingKaryawanResponse(trainingKaryawan), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<Page<TrainingKaryawanResponse>> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingKaryawan> trainingKaryawans = trainingKaryawanRepository.findAll(pageable);
        return new GeneralResponse<>(HttpStatus.OK.value(),
                trainingKaryawans.map(this::trainingKaryawanResponse),
                HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<TrainingKaryawanResponse> get(Long id) {
        TrainingKaryawan trainingKaryawan = trainingKaryawanRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training karyawan not found")
        );

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingKaryawanResponse(trainingKaryawan), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @Override
    public GeneralResponse<String> delete(TrainingKaryawanOnlyIdRequest request) {
        validationService.validate(request);

        TrainingKaryawan trainingKaryawan = trainingKaryawanRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training karyawan not found")
        );

        trainingKaryawan.setDeletedDate(new Date());
        trainingKaryawanRepository.save(trainingKaryawan);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success delete", HttpStatus.OK.getReasonPhrase());
    }

    private TrainingKaryawanResponse trainingKaryawanResponse(TrainingKaryawan trainingKaryawan) {
        return new TrainingKaryawanResponse(trainingKaryawan.getCreatedDate(), trainingKaryawan.getUpdatedDate(),
                trainingKaryawan.getDeletedDate(), trainingKaryawan.getId(),
                new KaryawanResponse(trainingKaryawan.getKaryawan().getCreatedDate(), trainingKaryawan.getKaryawan().getUpdatedDate(),
                        trainingKaryawan.getKaryawan().getDeletedDate(), trainingKaryawan.getKaryawan().getId(),
                        trainingKaryawan.getKaryawan().getNama(), trainingKaryawan.getKaryawan().getDob(),
                        trainingKaryawan.getKaryawan().getStatus(), trainingKaryawan.getKaryawan().getAlamat(),
                        new DetailKaryawanResponse(trainingKaryawan.getKaryawan().getDetailKaryawan().getCreatedDate(),
                                trainingKaryawan.getKaryawan().getDetailKaryawan().getUpdatedDate(),
                                trainingKaryawan.getKaryawan().getDetailKaryawan().getDeletedDate(),
                                trainingKaryawan.getKaryawan().getDetailKaryawan().getId(),
                                trainingKaryawan.getKaryawan().getDetailKaryawan().getNik(),
                                trainingKaryawan.getKaryawan().getDetailKaryawan().getNpwp())),
                new TrainingResponse(
                        trainingKaryawan.getTraining().getCreatedDate(), trainingKaryawan.getTraining().getUpdatedDate(),
                        trainingKaryawan.getTraining().getDeletedDate(), trainingKaryawan.getTraining().getId(),
                        trainingKaryawan.getTraining().getTema(), trainingKaryawan.getTraining().getPengajar()),
                trainingKaryawan.getTanggal());
    }
}
