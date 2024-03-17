package ru.oleg.hackathon.domain.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    TEACHER("TEACHER"),
    
    STUDENT("STUDENT");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
