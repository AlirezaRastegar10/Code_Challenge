package com.alireza.java_code_challenge.service.confirmationcode;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    @Override
    public String generateRandomCode() {
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
