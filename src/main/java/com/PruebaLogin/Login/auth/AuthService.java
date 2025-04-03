package com.PruebaLogin.Login.auth;

import com.PruebaLogin.Login.JWT.JwtService;
import com.PruebaLogin.Login.User.Role;
import com.PruebaLogin.Login.User.User;
import com.PruebaLogin.Login.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User.UserBuilder userBuilder = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firtsname(request.getFirtsname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER);

        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            userBuilder.profileImage(request.getProfileImage());
        }

        User user = userBuilder.build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .profileImage(user.getProfileImage())
                .build();
    }

    public AuthResponse edit(EditRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Usuario "+ request.getId() +"+no encontrado"));

        if (request.getUsername() !=null){
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getCountry() != null ){
            user.setCountry(request.getCountry());
        }
        if (request.getFirtsname() !=null){
            user.setFirtsname(request.getFirtsname());
        }
        if (request.getLastname() !=null){
            user.setLastname(request.getLastname());
        }
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            user.setProfileImage(request.getProfileImage());
        }

        userRepository.save(user);
        String  token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .profileImage(user.getProfileImage())
                .build();
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("Intento de eliminar usuario con ID " + id + ", pero no fue encontrado.");
                    return new RuntimeException("Usuario no encontrado");
                });
        userRepository.delete(user);
    }
}
