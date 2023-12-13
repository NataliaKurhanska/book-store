package bookstore.service;

import bookstore.dto.UserRegistrationRequestDto;
import bookstore.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request);
}
