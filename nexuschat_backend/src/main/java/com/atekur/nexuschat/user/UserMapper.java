package com.atekur.nexuschat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public User fromTokenAttributes(Map<String, Object> claims) {

        User user = new User();
        if (claims.containsKey("sub")){
            user.setId(claims.get("sub").toString());
        }
        if (claims.containsKey("given_name")){
            user.setFirstName(claims.get("given_name").toString());
        } else if (claims.containsKey("nickname")) {
            user.setFirstName(claims.get("nickname").toString());
        }
        if (claims.containsKey("family_name")){
            user.setLastName(claims.get("family_name").toString());
        }
        if (claims.containsKey("email")){
            user.setEmail(claims.get("email").toString());
        }
        user.setLastSeen(LocalDateTime.now());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}
