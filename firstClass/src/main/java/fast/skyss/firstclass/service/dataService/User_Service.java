package fast.skyss.firstclass.service.dataService;

import fast.skyss.firstclass.entity.DTO.UserRegistrationDTO;
import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Slf4j
@Service
@Transactional
public class User_Service {


    @Autowired
    private UserRepository userRepository;
    private static final long MAX_AVATAR_SIZE = 5 * 1024 * 1024; // 5MB

    @Value("classpath:static/avatar.jpg")  // Inject the default avatar image resource
    private Resource defaultAvatar;  // Use Spring's Resource type

    @Autowired
    private ResourceLoader resourceLoader;

    private final PasswordEncoder passwordEncoder;

    public User_Service(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User_x registerUser(UserRegistrationDTO dto) throws IOException {

        log.info("Starting user registration for username: {}", dto.getUsername());

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User_x user = new User_x();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));  // Encode the password
        user.setEmail(dto.getEmail());
        user.setRole("USER");
        log.info("User registration successful for username: {}", dto.getUsername());
        MultipartFile avatarFile = dto.getAvatar();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String contentType = avatarFile.getContentType();
            if (avatarFile.getSize() > MAX_AVATAR_SIZE) {
                throw new RuntimeException("Avatar file size exceeds 5MB limit");
            }

            assert contentType != null;
            if (isAllowedType(contentType)) {
                user.setAvatarData(avatarFile.getBytes());
                user.setAvatarType(contentType);
            } else {
                throw new RuntimeException("Invalid file type");
            }
        } else {
            // Load default avatar image if no avatar is provided
            InputStream inputStream = defaultAvatar.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            user.setAvatarData(outputStream.toByteArray());
            user.setAvatarType("image/jpg");
            log.info("Default avatar used for user: {}", user.getUsername());
        }
        log.info("User registered successfully: {}", user.getUsername());
        return userRepository.save(user);

    }

    public User_x findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean isAllowedType(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("application/pdf");
    }

    public User_x findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User_x createDeanUser(String username, String password, String email) {
        User_x dean = new User_x();
        dean.setUsername(username);
        dean.setPassword(passwordEncoder.encode(password));
        dean.setEmail(email);
        return userRepository.save(dean);
    }

    public boolean isDean(String name) {
        User_x user = findByUsername(name);
        return name.toLowerCase().equals("dean")
                && user.getEmail().toLowerCase().contains("dean")//???
                && user.getRole().equals("DEAN");
    }

}
