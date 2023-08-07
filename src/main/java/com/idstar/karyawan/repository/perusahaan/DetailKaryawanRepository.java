package com.idstar.karyawan.repository.perusahaan;

import com.idstar.karyawan.model.perusahaan.DetailKaryawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailKaryawanRepository extends JpaRepository<DetailKaryawan, Long> {
}
