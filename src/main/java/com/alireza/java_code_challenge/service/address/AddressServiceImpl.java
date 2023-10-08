package com.alireza.java_code_challenge.service.address;


import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.AddressMapperImpl;
import com.alireza.java_code_challenge.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapperImpl addressMapper;

    @Override
    public Address save(RegisterAddress registerAddress, Province province) {
        var address = addressMapper.addressDtoToAddress(registerAddress);
        address.setProvince(province);
        return addressRepository.save(address);
    }
}
