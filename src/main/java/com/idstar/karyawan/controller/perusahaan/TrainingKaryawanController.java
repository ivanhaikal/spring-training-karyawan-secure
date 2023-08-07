package com.idstar.karyawan.controller.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanRequest;
import com.idstar.karyawan.dao.request.perusahaan.TrainingKaryawanWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.TrainingKaryawanResponse;
import com.idstar.karyawan.service.perusahaan.TrainingKaryawanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/idstar/karyawan-training")
public class TrainingKaryawanController {

    @Autowired
    private TrainingKaryawanService trainingKaryawanService;

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse<TrainingKaryawanResponse>> save(@RequestBody TrainingKaryawanWithoutIdRequest request) {
        GeneralResponse<TrainingKaryawanResponse> response = trainingKaryawanService.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse<TrainingKaryawanResponse>> update(@RequestBody TrainingKaryawanRequest request) {
        GeneralResponse<TrainingKaryawanResponse> response = trainingKaryawanService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse<Page<TrainingKaryawanResponse>>> list(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        GeneralResponse<Page<TrainingKaryawanResponse>> response = trainingKaryawanService.list(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralResponse<TrainingKaryawanResponse>> get(@PathVariable("id") Long id) {
        GeneralResponse<TrainingKaryawanResponse> response = trainingKaryawanService.get(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse<String>> delete(@RequestBody TrainingKaryawanOnlyIdRequest request) {
        GeneralResponse<String> response = trainingKaryawanService.delete(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
