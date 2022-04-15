package org.geoandri.teams.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.geoandri.teams.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TeamEventDeserializer implements Deserializer<TeamEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamEventDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public TeamEvent deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                LOGGER.info("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(bytes, "UTF-8"), TeamEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to TeamEvent");
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
