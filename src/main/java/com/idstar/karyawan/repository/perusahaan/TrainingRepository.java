package com.idstar.karyawan.repository.perusahaan;

import com.idstar.karyawan.model.perusahaan.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
