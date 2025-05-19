package com.bytedev.storage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class TestReportConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectDir = System.getProperty("user.dir");
        String testReportsPath = projectDir + "/build/reports/tests/test/";
        
        File testReportsDir = new File(testReportsPath);
        if (testReportsDir.exists() && testReportsDir.isDirectory()) {
            System.out.println("Mapeando relatórios de teste em: " + testReportsPath);
            registry.addResourceHandler("/test-reports/**")
                    .addResourceLocations("file:" + testReportsPath);
        } else {
            System.out.println("Diretório de relatórios de teste não encontrado em: " + testReportsPath);
        }
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/test-reports", "/test-reports/index.html");
    }
}