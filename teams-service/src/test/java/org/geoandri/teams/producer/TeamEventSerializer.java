package org.geoandri.teams.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.geoandri.teams.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TeamEventSerializer implements Serializer<TeamEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamEventSerializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, TeamEvent data) {
        try {
            if (data == null){
                LOGGER.info("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing TeamEvent to byte[]");
        }
    }



    @Override
    public void close() {
        Serializer.super.close();
    }
}
