
package com.TechCart.user;

import com.TechCart.security.JwtService;
import com.TechCart.user.dto.RegisterRequest;
import com.TechCart.user.dto.UserResponse;
import com.TechCart.user.dto.LoginRequest;
import com.TechCart.user.dto.LoginResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // USER REGISTRATION
    public UserResponse registerUser(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber());

        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getUserId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getCreatedAt()
        );
    }

    // USER LOGIN
    public LoginResponse loginUser(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getUserId(),
                user.getFullName(),
                user.getEmail()
        );
    }
}
