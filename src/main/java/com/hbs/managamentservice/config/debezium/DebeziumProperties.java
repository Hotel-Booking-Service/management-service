package com.hbs.managamentservice.config.debezium;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "debezium")
public class DebeziumProperties {
    private boolean enabled;
    private String connectorName;
    private String connectUrl;
    private Config config;

    @Data
    public static class Config {
        private Topic topic;
        private Database database;
        private String slot;
        private String publication;
        private String tableIncludeList;

        @Data
        public static class Database {
            private String hostname;
            private String port;
            private String user;
            private String password;
            private String dbname;
        }

        @Data
        public static class Topic {
            private String prefix;
        }
    }
}
