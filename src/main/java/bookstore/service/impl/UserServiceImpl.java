package bookstore.service.impl;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.UserRepository;
import bookstore.service.RoleService;
import bookstore.service.ShoppingCartService;
import bookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email is already registered");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleService.getByName(Role.RoleName.USER)));
        User savedUser = userRepository.save(user);
        shoppingCartService.registerNewShoppingCart(savedUser);
        return userMapper.toDto(savedUser);
    }
}
