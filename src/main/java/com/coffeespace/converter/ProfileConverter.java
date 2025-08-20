package com.coffeespace.converter;

import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileConverter implements Converter<RegisterRequest, Profile> {

    @Override
    public Profile modelToEntity(RegisterRequest req) {
        log.debug("Converting RegisterRequest to Profile entity");
        Profile p = new Profile();
        p.setFirstname(req.getFirstName());
        p.setLastname(req.getLastName());
        p.setEmail(req.getEmail());
        p.setContactNumber(req.getContactNumber());
        p.setDob(req.getDob());
        p.setCity(req.getCity());
        return p;
    }

    @Override
    public RegisterRequest entityToModel(Profile profile) {
        log.debug("Converting Profile entity to RegisterRequest");
        return RegisterRequest.builder()
                .firstName(profile.getFirstname())
                .lastName(profile.getLastname())
                .email(profile.getEmail())
                .contactNumber(profile.getContactNumber())
                .dob(profile.getDob())
                .city(profile.getCity())
                .build();
    }
}
