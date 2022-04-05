package com.algaworks.algafood;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AlgaworksAlgafoodApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        var app = new SpringApplication(AlgaworksAlgafoodApiApplication.class);
        app.addListeners(new Base64ProtocolResolver());
        app.run(args);

        //SpringApplication.run(AlgaworksAlgafoodApiApplication.class, args);
    }

}
