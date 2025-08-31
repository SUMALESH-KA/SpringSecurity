package com.sumal.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "security.api-key-clients")
@Data
public class ApiKeyClientsProperties {

    private Map<String, String> clients;
}
