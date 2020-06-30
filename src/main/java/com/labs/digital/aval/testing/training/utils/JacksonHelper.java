package com.labs.digital.aval.testing.training.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public final class JacksonHelper {
    private static JacksonHelper INSTANCE;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModules(new JavaTimeModule(), new Jdk8Module(),new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

    private JacksonHelper() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public synchronized static JacksonHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JacksonHelper();
        }
        return INSTANCE;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
