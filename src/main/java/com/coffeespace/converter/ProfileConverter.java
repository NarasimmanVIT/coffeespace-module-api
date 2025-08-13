
package com.coffeespace.converter;

import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileConverter {

    public Profile toEntity(RegisterRequest req) {
        log.debug("Converting RegisterRequest to Profile entity");
        Profile p = new Profile();
        p.setFirstname(req.getFirstName());
        p.setLastname(req.getLastName());
        p.setEmail(req.getEmail());
        p.setDob(req.getDob() != null ? String.valueOf(req.getDob()) : null);
        return p;
    }
}

