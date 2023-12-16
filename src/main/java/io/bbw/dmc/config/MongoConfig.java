package io.bbw.dmc.config;

import io.bbw.dmc.model.converter.OrderStatusToStringConverter;
import io.bbw.dmc.model.converter.StringToOrderStatusConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(System.getenv("DB_URI"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return System.getenv("DB_NAME");
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
        converterConfigurationAdapter.registerConverters(List.of(new OrderStatusToStringConverter(), new StringToOrderStatusConverter()));
    }
}
