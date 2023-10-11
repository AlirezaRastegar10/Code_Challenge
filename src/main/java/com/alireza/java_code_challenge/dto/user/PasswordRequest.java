package com.alireza.java_code_challenge.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PasswordRequest implements Serializable {

    @NotBlank(message = "currentPassword cannot be empty.")
    @Size(min = 8, max = 8, message = "currentPassword must be 8 characters.")
    @Pattern(regexp = "^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", message = "the currentPassword must contain numbers and letters.")
    String currentPassword;

    @NotBlank(message = "newPassword cannot be empty.")
    @Size(min = 8, max = 8, message = "newPassword must be 8 characters.")
    @Pattern(regexp = "^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", message = "the newPassword must contain numbers and letters.")
    String newPassword;

    @NotBlank(message = "confirmationPassword cannot be empty.")
    @Size(min = 8, max = 8, message = "confirmationPassword must be 8 characters.")
    @Pattern(regexp = "^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", message = "the confirmationPassword must contain numbers and letters.")
    String confirmationPassword;
}
