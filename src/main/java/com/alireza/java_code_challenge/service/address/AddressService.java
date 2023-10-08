package com.alireza.java_code_challenge.service.address;

import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.Province;

public interface AddressService {

    Address save(RegisterAddress registerAddress, Province province);
}
