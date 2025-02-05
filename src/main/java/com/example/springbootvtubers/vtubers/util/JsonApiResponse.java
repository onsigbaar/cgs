package com.example.springbootvtubers.vtubers.util;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@Data
@Builder
public class JsonApiResponse {

    private DataWrapper data; // Use a custom wrapper to handle both single and multiple data objects
    private Map<String, Object> meta;

    @Data
    @Builder
    public static class DataObject {
        private String type;
        private String id;
        private Map<String, Object> attributes;
        private Map<String, Relationship> relationships;
    }

    @Data
    @Builder
    public static class Relationship {
        private DataObject data;
    }

    // Custom wrapper to handle both single and multiple data objects
    @Data
    @Builder
    @JsonSerialize(using = DataWrapperSerializer.class) // Use the custom serializer
    public static class DataWrapper {
        private DataObject singleData; // For single data object
        private List<DataObject> multipleData; // For multiple data objects

        // Helper method to check if the wrapper contains single or multiple data
        public boolean isSingleData() {
            return singleData != null;
        }
    }

    // Custom serializer for DataWrapper
    public static class DataWrapperSerializer extends JsonSerializer<DataWrapper> {
        @Override
        public void serialize(DataWrapper value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value.isSingleData()) {
                gen.writeObject(value.singleData); // Serialize single data object
            } else {
                gen.writeObject(value.multipleData); // Serialize multiple data objects
            }
        }
    }
}
