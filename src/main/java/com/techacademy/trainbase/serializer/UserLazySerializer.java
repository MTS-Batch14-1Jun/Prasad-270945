package com.techacademy.trainbase.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.techacademy.trainbase.entity.User;
import org.hibernate.Hibernate;

import java.io.IOException;

public class UserLazySerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null || !Hibernate.isInitialized(value)) {
            // write JSON null so property will be null/omitted depending on inclusion settings
            gen.writeNull();
            return;
        }

        gen.writeStartObject();
        if (value.getId() != null) {
            gen.writeNumberField("id", value.getId());
        }
        if (value.getUsername() != null) {
            gen.writeStringField("username", value.getUsername());
        }
        gen.writeEndObject();
    }
}

