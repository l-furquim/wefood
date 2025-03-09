package com.lucas.profile_ms.domains.profile;

import com.lucas.profile_ms.domains.profile.enums.ProfileType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profile implements UserDetails {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private ProfileType type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.type == ProfileType.WAITING_CONFIRMATION)return List.of();

        if(this.type == ProfileType.RESTAURANT) return List.of(new SimpleGrantedAuthority("ROLE_RESTAURANT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_COMUM"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
