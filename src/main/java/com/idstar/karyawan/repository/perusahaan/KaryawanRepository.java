package com.idstar.karyawan.repository.perusahaan;

import com.idstar.karyawan.model.perusahaan.Karyawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Long> {
}
