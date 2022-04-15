package org.geoandri.developers.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.geoandri.developers.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TeamEventDeserializer implements Deserializer<TeamEvent> {
    private static Logger LOGGER = LoggerFactory.getLogger(TeamEventDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public TeamEvent deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(bytes, "UTF-8"), TeamEvent.class);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }


    @Override
    public void close() {
        Deserializer.super.close();
    }
}
