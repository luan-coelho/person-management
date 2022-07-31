package br.bunny.service;

import br.bunny.model.user.User;
import br.bunny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    final private UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsUserById(UUID id) {
        return userRepository.existsById(id);
    }

    @Transactional
    public User saveUser(@Valid User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar usuário.");
        }
    }

    @Transactional
    public User activateOrDesactivateUser(UUID id, boolean ativo) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        user.get().setAtivo(ativo);
        return userRepository.save(user.get());
    }
}
