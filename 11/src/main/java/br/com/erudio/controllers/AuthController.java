package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.security.AccountCredentialVO;
import br.com.erudio.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@Tag(name = "Authentication Endpoint")
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthServices services;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates an user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialVO data) {
        if (Objects.isNull(data) || checkParamsValidation(data.getUsername(), data.getPassword()))  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        var token = services.signin(data);
        if (Objects.isNull(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        } else {
            return token;
        }
    }
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable(value = "username") String username,
                                       @RequestHeader(value = "Authorization") String refreshToken) {
        if (checkParamsValidation(username, refreshToken))  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        var token = services.refreshToken(username, refreshToken);
        if (Objects.isNull(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        } else {
            return token;
        }
    }

    private boolean checkParamsValidation(String username, String value) {
        return Objects.isNull(value) || value.isBlank()
                || Objects.isNull(username) || username.isBlank();
    }
}
