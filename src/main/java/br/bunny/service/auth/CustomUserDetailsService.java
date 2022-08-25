package br.bunny.service.auth;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.repository.person.PhysicalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PhysicalPersonRepository physicalPersonRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PhysicalPerson> user = physicalPersonRepository.findByEmailIgnoreCase(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found by email"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(user.get().getEmail(), user.get().getPassword(), authorities);
    }
}
