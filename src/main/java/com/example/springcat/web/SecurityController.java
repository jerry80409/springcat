package com.example.springcat.web;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/user/secur")
class SecurityController {

    @PostMapping("/login")
    ResponseEntity<String> login(@Valid @RequestBody Login login) {
        return ResponseEntity.ok(login.toString());
    }
}
