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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_karyawan")
@Where(clause = "deleted_date is null")
@EntityListeners({AuditingEntityListener.class})
public class DetailKaryawan {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik")
    private String nik;

    @Column(name = "npwp")
    private String npwp;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    @OneToOne(mappedBy = "detailKaryawan")
    private Karyawan karyawan;
}
