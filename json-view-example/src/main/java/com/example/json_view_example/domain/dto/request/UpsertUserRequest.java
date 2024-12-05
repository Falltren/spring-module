package com.example.json_view_example.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertUserRequest {

    @Length(min = 3, max = 30, message = "The username should be between 3 and 30 characters")
    @NotBlank(message = "The username should be between 3 and 30 characters")
    private String username;

    @Length(max = 30, message = "The length of the email should be no more than 30 characters")
    @Email(regexp = "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$", message = "Incorrect email")
    private String email;

}
