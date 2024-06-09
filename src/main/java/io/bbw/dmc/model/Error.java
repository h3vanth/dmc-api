package io.bbw.dmc.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

@Getter
@Setter
public class Error {
    private List<String> messages;

    private DateTime timestamp;

    public Error(String message) {
        timestamp = DateTime.now(DateTimeZone.UTC);
        this.messages = List.of(message);
    }

    public Error(List<String> messages) {
        timestamp = DateTime.now(DateTimeZone.UTC);
        this.messages = messages;
    }
}
