package com.sumal.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "api-key")
@Data
public class ApiKeyClientsProperties {

    private Map<String, String> clients = new HashMap<>();
}
