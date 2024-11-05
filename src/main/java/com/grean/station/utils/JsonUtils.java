//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
    private static final ObjectMapper DEFAULT_MAPPING = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toJsonString(Object obj) {
        try {
            return DEFAULT_MAPPING.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            return !Objects.isNull(obj) ? obj.toString() : null;
        }
    }

    public static JsonNode parseObject(String jsonBody) {
        try {
            JsonNode jsonNode = DEFAULT_MAPPING.readTree(jsonBody);
            if (jsonNode == null) {
                jsonNode = NullNode.getInstance();
            }

            return (JsonNode)jsonNode;
        } catch (IOException var2) {
            return NullNode.getInstance();
        }
    }

    public static String toJsonStringIgnoreNull(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            return !Objects.isNull(obj) ? obj.toString() : null;
        }
    }

    public static ObjectNode createObjectNode() {
        return DEFAULT_MAPPING.createObjectNode();
    }

    public static ObjectNode convert2JsonTree(Object pojo) {
        return (ObjectNode)DEFAULT_MAPPING.valueToTree(pojo);
    }

    public static <T> T copyMapInfo2Model(Map map, Class<T> modelClazz) throws IOException {
        return DEFAULT_MAPPING.readValue(toJsonString(map), modelClazz);
    }
}
