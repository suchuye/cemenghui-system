package cn.neu.edu.cemenghuisystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.neu.edu.cemenghuisystem.mapper")
public class CemenghuiSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CemenghuiSystemApplication.class, args);
	}

}
