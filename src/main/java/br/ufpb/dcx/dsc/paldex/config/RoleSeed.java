package br.ufpb.dcx.dsc.paldex.config;

import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import br.ufpb.dcx.dsc.paldex.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleSeed {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);
            System.out.println("Role ADMIN created");
        }

        if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);
            roleRepository.save(userRole);
            System.out.println("Role USER created");
        }
    }
}
