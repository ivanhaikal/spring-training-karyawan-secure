package com.idstar.karyawan.repository.perusahaan;

import com.idstar.karyawan.model.perusahaan.TrainingKaryawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingKaryawanRepository extends JpaRepository<TrainingKaryawan, Long> {
}
