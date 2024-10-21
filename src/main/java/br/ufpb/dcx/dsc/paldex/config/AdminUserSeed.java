package br.ufpb.dcx.dsc.paldex.config;

import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.RoleRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminUserSeed {

    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        System.out.println("Initializing AdminUserSeed...");

        if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
            User admin = new User();
            admin.setName("Administrator");
            admin.setEmail(ADMIN_EMAIL);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));

            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            admin.setRoles(Collections.singletonList(adminRole));

            userRepository.save(admin);
        }
    }

}
