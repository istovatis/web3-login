package com.emfisis.vcportal.utils.RestCalling;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
        if (localTime == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(localTime.toString());
    }

    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return LocalTime.parse(jsonReader.nextString());
    }
}
