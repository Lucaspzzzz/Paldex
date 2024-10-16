package br.ufpb.dcx.dsc.paldex;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.record.RecordModule;
@SpringBootApplication
public class PaldexApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper().registerModule(new RecordModule());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(PaldexApplication.class, args);
    }

}
