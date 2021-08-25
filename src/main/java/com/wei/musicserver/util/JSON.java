package com.wei.musicserver.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSON {

    private static ObjectMapper om = new ObjectMapper();

    static {
        om = om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
    }

    public static JsonNode begin(String json) throws JsonProcessingException {
        return om.readTree(json);
    }

    public static JsonNode getWithString(JsonNode j, String s){
        return j.get(s);
    }

    public static JsonNode getWithIndex(JsonNode j, int i){
        return j.get(i);
    }

    public static String getStringWithString(JsonNode j, String s){
        return j.get(s).toString().replaceAll("\"","");
    }
}
