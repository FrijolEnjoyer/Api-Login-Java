package com.PruebaLogin.Login.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping(value = "register")
    public  ResponseEntity<AuthResponse> reqister(@RequestBody RegisterRequest request)
    {
        return  ResponseEntity.ok(authService.register(request));
    }

    /// Tengo que manejar el body de respuesta al usuario no encontrado
    @PutMapping(value = "edit")
    public ResponseEntity<AuthResponse> edit(@RequestBody EditRequest request)
    {
        return  ResponseEntity.ok(authService.edit(request));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        authService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
