package bookstore.service;

import bookstore.model.Role;

public interface RoleService {
    Role getByName(Role.RoleName name);
}
