package com.atm.services;

import com.atm.responce.UserResponse;

public interface AtmServices {

    UserResponse userAuth(String cardNumber, String password);

}
