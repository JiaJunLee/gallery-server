package com.oocl.ita.gallery

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class GalleryApplication extends SpringBootServletInitializer{

	static void main(String[] args) {
		SpringApplication.run(GalleryApplication, args)
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GalleryApplication.class);
	}
}
