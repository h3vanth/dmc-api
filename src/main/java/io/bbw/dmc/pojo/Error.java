package io.bbw.dmc.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private List<String> errorMessages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public Error(List<String> errorMessages) {
        timestamp = new Date();
        this.errorMessages = errorMessages;
    }
}
