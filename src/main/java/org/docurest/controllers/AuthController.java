package org.docurest.controllers;

import jakarta.annotation.security.PermitAll;
import lombok.Data;
import org.docurest.infra.InfraAware;
import org.docurest.queries.Filter;
import org.docurest.security.JwtUtil;
import org.docurest.security.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AuthController extends InfraAware {

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Autowired
    JwtUtil jwtUtil;

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authenticationRequest) {
        var userAuthDetailResults = infra.select(UserAuthDetails.class, loginFilter(authenticationRequest));
        var userAuthDetails = userAuthDetailResults.findFirst().orElseThrow();
        return ResponseEntity.ok(jwtUtil.generateToken(userAuthDetails.getDocId()));
    }

    protected abstract Filter loginFilter(AuthRequest authenticationRequest);

}

