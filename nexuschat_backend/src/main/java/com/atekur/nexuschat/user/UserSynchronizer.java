package com.atekur.nexuschat.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public void synchronizedWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        getUserEmail(token).ifPresent(userMail -> {
            log.info("Synchronizing user having email {}", userMail);
//            Optional<User> optUser = userRepository.findUserByEmail(userMail);
            User user = userMapper.fromTokenAttributes(token.getClaims());
//            optUser.ifPresent(value -> user.setId(optUser.get().getId()));
            userRepository.save(user);
        });
    }

    private Optional<String> getUserEmail(Jwt token){
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")){
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }
}
