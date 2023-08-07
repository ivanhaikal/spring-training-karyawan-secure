package com.idstar.karyawan;

import com.idstar.karyawan.controller.fileupload.FileStorageProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition
@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class KaryawanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaryawanApplication.class, args);
	}

}
