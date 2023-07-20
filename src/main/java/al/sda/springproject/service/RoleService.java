package al.sda.springproject.service;

import al.sda.springproject.entity.Role;
import al.sda.springproject.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final UserRoleRepository userRoleRepository;

    public RoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public List<Role> getAllRoles() {
        return this.userRoleRepository.findAll();
    }


}
