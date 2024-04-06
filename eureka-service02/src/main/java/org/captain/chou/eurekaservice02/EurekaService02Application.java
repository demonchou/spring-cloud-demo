package org.captain.chou.eurekaservice02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaService02Application
{

	public static void main(String[] args)
	{
		SpringApplication.run(EurekaService02Application.class, args);
	}

}
