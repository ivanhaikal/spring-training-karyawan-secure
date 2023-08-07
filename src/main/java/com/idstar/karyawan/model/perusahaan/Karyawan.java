package com.idstar.karyawan.model.perusahaan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "karyawan")
@Where(clause = "deleted_date is null")
@EntityListeners({AuditingEntityListener.class})
public class Karyawan {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "status")
    private String status;

    @Column(name = "alamat")
    private String alamat;

    @OneToOne
    @JoinColumn(name = "detail_karyawan", referencedColumnName = "id")
    private DetailKaryawan detailKaryawan;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    @OneToMany(mappedBy = "karyawan")
    private List<Rekening> rekenings;

    @OneToMany(mappedBy = "karyawan")
    private List<TrainingKaryawan> trainingKaryawans;
}
