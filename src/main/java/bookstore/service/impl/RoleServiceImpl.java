package bookstore.service.impl;

import bookstore.exception.EntityNotFoundException;
import bookstore.model.Role;
import bookstore.repository.RoleRepository;
import bookstore.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getByName(Role.RoleName name) {
        return roleRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Can't get role by name: " + name));
    }
}
