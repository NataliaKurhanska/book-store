package bookstore.dto;

import bookstore.validation.Email;
import bookstore.validation.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Password
public class UserRegistrationRequestDto {
    @NotBlank(message = "Login can't be null or blank!")
    @Email
    private String email;
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    @NotBlank
    @Length(min = 6, max = 20)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
