package org.vivek.module6;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vivek.module6.entity.UserEntity;
import org.vivek.module6.services.JWTService;

@SpringBootTest
class Module5ApplicationTests {

    @Autowired
    private JWTService jwtService;

    @Test
    void contextLoads() {

        UserEntity userEntity = new UserEntity(4L, "vivek@gmail.com", "1234");

        String token = jwtService.generateToken(userEntity);
        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);
        System.out.println(id);
    }
}
