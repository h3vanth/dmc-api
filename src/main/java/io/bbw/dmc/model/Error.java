package io.bbw.dmc.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private List<String> messages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public Error(String message) {
        timestamp = new Date();
        this.messages = List.of(message);
    }

    public Error(List<String> messages) {
        timestamp = new Date();
        this.messages = messages;
    }
}
