package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    public Role showRoleById(Long id) {
        return roleRepository.getById(id);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");
    }

    private void createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }


//    public Set<Role> getRolesByIds(List<Long> roleIds) {
//        List<Role> roles = roleRepository.findAllById(roleIds);
//        return new HashSet<>(roles);
//    }


    public Set<Role> getRolesByIds(List<Long> roleIds) {
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }

}
