package com.anso.mapalao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   //开启定时器
@MapperScan("com.anso.mapalao.dao")
@ComponentScan("com.anso.mapalao")
public class MapalaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapalaoApplication.class, args);
	}
}
