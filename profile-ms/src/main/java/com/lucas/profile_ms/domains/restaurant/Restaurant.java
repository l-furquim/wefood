package com.lucas.profile_ms.domains.restaurant;

import com.lucas.profile_ms.domains.restaurant.enums.RestaurantAccountType;
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
@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 14)
    private String cnpj;

    @Column(name = "domain_email")
    private String domainEmail;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String address;

    @Column(name = "account_type")
    private RestaurantAccountType accountType;

    private String password;

    @Column(name = "is_main_account")
    private Boolean isMainAccount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.accountType.equals(RestaurantAccountType.MAIN)){
            return List.of(new SimpleGrantedAuthority("MAIN_ROLE"));
        }
        if(this.accountType.equals(RestaurantAccountType.WAITING_CONFIRMATION)){
            return List.of(new SimpleGrantedAuthority("WAITING_CONFIRMATION_ROLE"));
        }

        return List.of(new SimpleGrantedAuthority("BRANCH_ROLE"));
    }

    @Override
    public String getUsername() {
        return domainEmail;
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

