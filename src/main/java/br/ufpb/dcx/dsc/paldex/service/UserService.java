package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.config.CustomUserDetails;
import br.ufpb.dcx.dsc.paldex.exception.*;
import br.ufpb.dcx.dsc.paldex.model.Photo;
import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.PhotoRepository;
import br.ufpb.dcx.dsc.paldex.repository.RoleRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    private final RoleRepository roleRepository;
    private static final String DEFAULT_PHOTO_URL = "www.exemplo.com/foto.png";

    public UserService(UserRepository userRepository, PhotoRepository photoRepository,RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));
    }

    public User createUser(User user) {
        checkIfUserHasPermission();

        if (user.getPhoto() == null) {
            Photo defaultPhoto = new Photo(DEFAULT_PHOTO_URL);
            photoRepository.save(defaultPhoto);
            user.setPhoto(defaultPhoto);
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new ItemNotFoundException("Role USER not found"));
            user.setRoles(Collections.singletonList(userRole));
        }

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User u) {
        checkIfUserHasPermission();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));

        user.setEmail(u.getEmail());
        user.setName(u.getName());

        if (u.getUsername() != null && !u.getUsername().isEmpty()) {
            user.setUsername(u.getUsername());
        }

        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            user.setPassword(u.getPassword());
        }

        if (u.getPhoto() != null) {
            user.setPhoto(u.getPhoto());
        }

        return userRepository.save(user);
    }

    private void checkIfUserHasPermission() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails authenticatedUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User authenticatedUser = userRepository.findByEmail(authenticatedUserDetails.getUsername())
                    .orElseThrow(() -> new ItemNotFoundException("Authenticated user not found."));

            boolean isUser = authenticatedUser.getRoles()
                    .stream()
                    .anyMatch(role -> role.getName().equals(RoleName.ROLE_USER));

            if (isUser) {
                throw new ForbiddenActionException("Users with role USER cannot create or update other users.");
            }
        } else {
            throw new RuntimeException("Invalid user session.");
        }
    }


    public void deleteUser(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("Invalid user session.");
        }

        CustomUserDetails authenticatedUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User authenticatedUser = userRepository.findByEmail(authenticatedUserDetails.getUsername())
                .orElseThrow(() -> new ItemNotFoundException("Authenticated user not found."));

        if (authenticatedUser.getUserId().equals(userId) &&
                authenticatedUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN))) {
            throw new CannotDeleteAdminException("Admin account cannot be deleted.");
        }

        if (!authenticatedUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN)) &&
                !authenticatedUser.getUserId().equals(userId)) {
            throw new UnauthorizedDeletionException("You can only delete your own account.");
        }

        userRepository.delete(u);
    }

}
