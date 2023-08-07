package com.idstar.karyawan.controller.perusahaan;

import com.idstar.karyawan.dao.request.perusahaan.KaryawanOnlyIdRequest;
import com.idstar.karyawan.dao.request.perusahaan.KaryawanRequest;
import com.idstar.karyawan.dao.request.perusahaan.KaryawanWithoutIdRequest;
import com.idstar.karyawan.dao.response.GeneralResponse;
import com.idstar.karyawan.dao.response.KaryawanResponse;
import com.idstar.karyawan.service.perusahaan.KaryawanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/idstar/karyawan")
public class KaryawanController {

    @Autowired
    private KaryawanService karyawanService;

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse<KaryawanResponse>> save(@RequestBody KaryawanWithoutIdRequest request) {
        GeneralResponse<KaryawanResponse> response = karyawanService.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse<KaryawanResponse>> update(@RequestBody KaryawanRequest request) {
        GeneralResponse<KaryawanResponse> response = karyawanService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse<Page<KaryawanResponse>>> list(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        GeneralResponse<Page<KaryawanResponse>> response = karyawanService.list(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<KaryawanResponse>> get(@PathVariable("id") Long id) {
        GeneralResponse<KaryawanResponse> response = karyawanService.get(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse<String>> delete(@RequestBody KaryawanOnlyIdRequest request) {
        GeneralResponse<String> response = karyawanService.delete(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
