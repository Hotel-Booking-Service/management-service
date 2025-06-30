package com.hbs.managamentservice.config.debezium;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DebeziumConfig {

    private final DebeziumProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Bean
    public ApplicationRunner connectorInitializer() {
        return args -> {
            if (!properties.isEnabled()) return;

            String connectorName = properties.getConnectorName();
            String connectUrl = properties.getConnectUrl();

            try {
                ResponseEntity<String> existing = restTemplate.getForEntity(
                        connectUrl + "/connectors/" + connectorName, String.class);
                if (existing.getStatusCode().is2xxSuccessful()) {
                    log.info("Connector '{}' already exists", connectorName);
                    return;
                }
            } catch (HttpClientErrorException.NotFound ignored) {
            }

            Map<String, Object> payload = getStringObjectMap(connectorName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(connectUrl + "/connectors", request, String.class);
                log.info("Debezium connector registration status: {}", response.getStatusCode());
            } catch (Exception e) {
                log.error("Failed to register Debezium connector", e);
            }
        };
    }

    private Map<String, Object> getStringObjectMap(String connectorName) {
        Map<String, Object> config = new HashMap<>();
        config.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        config.put("database.hostname", properties.getConfig().getDatabase().getHostname());
        config.put("database.port", properties.getConfig().getDatabase().getPort());
        config.put("database.user", properties.getConfig().getDatabase().getUser());
        config.put("database.password", properties.getConfig().getDatabase().getPassword());
        config.put("database.dbname", properties.getConfig().getDatabase().getDbname());
        config.put("database.server.name", "dbserver1");
        config.put("plugin.name", "pgoutput");
        config.put("topic.prefix", properties.getConfig().getTopic().getPrefix());
        config.put("slot.name", properties.getConfig().getSlot());
        config.put("publication.name", properties.getConfig().getPublication());
        config.put("table.include.list", properties.getConfig().getTableIncludeList());
        config.put("key.converter", "org.apache.kafka.connect.json.JsonConverter");
        config.put("value.converter", "org.apache.kafka.connect.json.JsonConverter");
        config.put("transforms", "RenameTopic,SanitizeUnderscore");
        config.put("transforms.RenameTopic.type", "org.apache.kafka.connect.transforms.RegexRouter");
        config.put("transforms.RenameTopic.regex", ".*\\.public\\.(.*)");
        config.put("transforms.RenameTopic.replacement", "management-$1");
        config.put("transforms.SanitizeUnderscore.type", "org.apache.kafka.connect.transforms.RegexRouter");
        config.put("transforms.SanitizeUnderscore.regex", "_");
        config.put("transforms.SanitizeUnderscore.replacement", "-");

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", connectorName);
        payload.put("config", config);
        return payload;
    }
}
