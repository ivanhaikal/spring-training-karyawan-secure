package com.idstar.karyawan.repository.perusahaan;

import com.idstar.karyawan.model.perusahaan.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekeningRepository extends JpaRepository<Rekening, Long> {
}
