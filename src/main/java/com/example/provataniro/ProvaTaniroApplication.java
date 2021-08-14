package com.example.provataniro;

import com.example.provataniro.services.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ProvaTaniroApplication implements CommandLineRunner, WebMvcConfigurer {
    @Resource
    private StorageService storageService;

    public static void main(String[] args) { SpringApplication.run(ProvaTaniroApplication.class, args);
    }


    @Override
    public void run(String... arg) throws Exception {
        storageService.excluirTodos();
        storageService.init();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

    }
}
