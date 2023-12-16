package io.bbw.dmc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    // TODO: do min max validation wherever applicable
    @NotBlank(message = "Passcode must not be blank")
    private String passcode;

    private String[] categories;
}
