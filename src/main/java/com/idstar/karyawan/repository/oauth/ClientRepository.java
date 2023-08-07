package com.idstar.karyawan.repository.oauth;

import com.idstar.karyawan.model.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}
