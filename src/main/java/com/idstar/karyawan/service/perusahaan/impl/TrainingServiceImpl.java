package com.idstar.karyawan.service.perusahaan.impl;

import com.idstar.karyawan.dao.request.perusahaan.TrainingOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.TrainingResponse;
import com.idstar.karyawan.model.perusahaan.Training;
import com.idstar.karyawan.repository.perusahaan.TrainingRepository;
import com.idstar.karyawan.service.ValidationService;
import com.idstar.karyawan.service.perusahaan.TrainingService;
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
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    @Override
    public GeneralResponse<TrainingResponse> save(TrainingWithoutIdRequest request) {
        validationService.validate(request);

        Training training = new Training();
        training.setTema(request.getTema());
        training.setPengajar(request.getPengajar());
        trainingRepository.save(training);

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingResponse(training), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @Override
    public GeneralResponse<TrainingResponse> update(TrainingRequest request) {
        validationService.validate(request);

        Training training = trainingRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training not found")
        );

        training.setTema(request.getTema());
        training.setPengajar(request.getPengajar());
        trainingRepository.save(training);

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingResponse(training), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<Page<TrainingResponse>> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Training> trainings = trainingRepository.findAll(pageable);
        return new GeneralResponse<>(HttpStatus.OK.value(),
                trainings.map(this::trainingResponse),
                HttpStatus.OK.getReasonPhrase());
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralResponse<TrainingResponse> get(Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training not found")
        );

        return new GeneralResponse<>(HttpStatus.OK.value(), trainingResponse(training), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    @Override
    public GeneralResponse<String> delete(TrainingOnlyIdRequest request) {
        validationService.validate(request);

        Training training = trainingRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID training not found")
        );

        training.setDeletedDate(new Date());
        trainingRepository.save(training);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success delete", HttpStatus.OK.getReasonPhrase());
    }

    private TrainingResponse trainingResponse(Training training) {
        return new TrainingResponse(training.getCreatedDate(), training.getUpdatedDate(), training.getDeletedDate(),
                training.getId(), training.getTema(), training.getPengajar());
    }
}
