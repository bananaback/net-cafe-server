package dev.bananaftmeo.netcafeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NetcafeserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetcafeserverApplication.class, args);
	}

}
