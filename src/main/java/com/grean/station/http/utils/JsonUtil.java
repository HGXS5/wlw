//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static volatile ObjectMapper objectMapper = null;

    public JsonUtil() {
    }

    public static ObjectMapper objectMapperInstance() {
        if (objectMapper == null) {
            Class var0 = JsonUtil.class;
            synchronized(JsonUtil.class) {
                if (objectMapper == null) {
                    objectMapper = objectMapper();
                }
            }
        }

        return objectMapper;
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setDefaultPropertyInclusion(Include.NON_NULL);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.enable(new Feature[]{Feature.ALLOW_UNQUOTED_FIELD_NAMES, Feature.ALLOW_SINGLE_QUOTES, Feature.ALLOW_UNQUOTED_CONTROL_CHARS, Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, Feature.ALLOW_NUMERIC_LEADING_ZEROS, Feature.ALLOW_TRAILING_COMMA});
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtil.DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtil.DATE_TIME_FORMATTER));
        om.registerModule(javaTimeModule);
        return om;
    }

    @Nonnull
    public static String toJson(@Nullable Object obj) {
        if (obj == null) {
            return "{}";
        } else {
            ObjectMapper objectMapper = objectMapperInstance();

            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException var3) {
                log.error("json序列化错误", var3);
                return "";
            }
        }
    }

    @Nonnull
    public static byte[] toJsonByte(@Nullable Object obj) {
        if (obj == null) {
            return new byte[0];
        } else {
            ObjectMapper objectMapper = objectMapperInstance();

            try {
                return objectMapper.writeValueAsBytes(obj);
            } catch (JsonProcessingException var3) {
                log.error("json序列化错误", var3);
                return new byte[0];
            }
        }
    }

    @Nonnull
    public static Map<String, String> toMapFromBytes(byte[] bytes) {
        if (bytes == null) {
            return new HashMap();
        } else {
            ObjectMapper objectMapper = objectMapperInstance();

            try {
                return (Map)objectMapper.readValue(bytes, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException var3) {
                log.error("json序列化错误", var3);
                return new HashMap();
            }
        }
    }

    @Nonnull
    public static String toJson(@Nonnull Object obj, @Nonnull ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var3) {
            log.error("json序列化错误", var3);
            return "";
        }
    }

    @Nonnull
    public static <T> T toObject(@Nonnull String json, @Nonnull TypeReference<T> returnType) throws Exception {
        ObjectMapper objectMapper = objectMapperInstance();
        return objectMapper.readValue(json, returnType);
    }

    @Nonnull
    public static <T> T toObject(@Nonnull String json, @Nonnull TypeReference<T> returnType, @Nonnull ObjectMapper objectMapper) throws Exception {
        return objectMapper.readValue(json, returnType);
    }

    @Nonnull
    public static Map<String, String> toHashMap(@Nonnull String json) throws Exception {
        return (Map)toObject(json, new TypeReference<Map<String, String>>() {
        });
    }

    @Nonnull
    public static Map<String, String> toHashMap(@Nonnull String json, @Nonnull ObjectMapper objectMapper) throws Exception {
        return (Map)toObject(json, new TypeReference<Map<String, String>>() {
        }, objectMapper);
    }
}
