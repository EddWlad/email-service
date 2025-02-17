package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.security.JwtRequest;
import com.tidsec.mail_service.security.JwtResponse;
import com.tidsec.mail_service.security.JwtTokenUtil;
import com.tidsec.mail_service.security.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    private void authenticate(String username, String password) throws Exception {
        try {
            System.out.println("Autenticando usuario: " + username);
            System.out.println("Autenticando contraseña: " + password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("Autenticación exitosa para usuario: " + username);

        } catch (DisabledException e) {
            System.out.println("Usuario deshabilitado: " + username);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("Credenciales inválidas para usuario: " + username);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception {
        System.out.println("Inicio del método login");
        authenticate(req.getUsername(), req.getPassword());
        System.out.println("Autenticación exitosa");

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(req.getUsername());
        System.out.println("Usuario cargado: " + userDetails.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("Token: " + token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
