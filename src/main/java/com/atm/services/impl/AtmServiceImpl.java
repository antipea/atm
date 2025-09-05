package com.atm.services.impl;

import com.atm.entity.UserEntity;
import com.atm.repository.UserRepository;
import com.atm.responce.UserResponse;
import com.atm.services.AtmServices;
import org.springframework.stereotype.Service;

@Service
public class AtmServiceImpl implements AtmServices {
    private final UserRepository userRepository;
    public AtmServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserResponse userAuth(String cardNumber, String password) {

        UserResponse userResponse = new UserResponse();

        UserEntity user = userRepository.findUserEntityByCardNumberAndPinCode(cardNumber, password);

        userResponse.setUserName(user.getUserName());

        return userResponse;
    }

}
