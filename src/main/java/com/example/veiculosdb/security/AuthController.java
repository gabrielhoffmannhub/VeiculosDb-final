package com.example.veiculosdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok("Token é válido");
        } else {
            return ResponseEntity.status(401).body("Token inválido");
        }
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameFromToken(@RequestParam String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return ResponseEntity.ok(username);
    }
}
