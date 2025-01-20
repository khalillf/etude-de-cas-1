package com.devoir.microservicecommandes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mes-config-ms")
public class ApplicationPropertiesConfiguration {
    // Getter and Setter
    private int commandesLast;

}