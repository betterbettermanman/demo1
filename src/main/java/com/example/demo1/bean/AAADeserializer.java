package com.example.demo1.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AAADeserializer extends JsonDeserializer<String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return mapper.readValue(jsonParser.getText(), String.class);
    }
}
