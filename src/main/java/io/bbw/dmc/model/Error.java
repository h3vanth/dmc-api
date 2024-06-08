package io.bbw.dmc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.List;

@Getter
@Setter
public class Error {
    private List<String> messages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private DateTime timestamp;

    public Error(String message) {
        timestamp = DateTime.now();
        this.messages = List.of(message);
    }

    public Error(List<String> messages) {
        timestamp = DateTime.now();
        this.messages = messages;
    }
}
