package com.atm.controllers;

import com.atm.request.UserAuthRequest;
import com.atm.responce.UserResponse;
import com.atm.services.impl.AtmServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseControllers {

    private final AtmServiceImpl atmServiceImpl;
    public BaseControllers(AtmServiceImpl atmServiceImpl) {
        this.atmServiceImpl = atmServiceImpl;
    }

    @GetMapping(value = "/hello")
    public String Hello() {

        return "hello";
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<UserResponse> AuthUser(@RequestBody UserAuthRequest userAuthRequest) {

        UserResponse userResponse = atmServiceImpl.userAuth(userAuthRequest.getCardNumber(), userAuthRequest.getPassword());
        //return "true";
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
