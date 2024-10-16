package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.Photo;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.PhotoRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, PhotoRepository photoRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));
    }

    public User createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Photo photo = new Photo("www.exemplo.com/foto.png");
        photoRepository.save(photo);
        user.setPhoto(photo);
        return userRepository.save(user);
    }
    public User updateUser(Long userId, User u) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));
        user.setEmail(u.getEmail());
        user.setName(u.getName());
        return userRepository.save(user);
    }
    public void deleteUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));
        userRepository.delete(u);
    }

}
