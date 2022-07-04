package br.bunny.service;

import br.bunny.dto.UserDTO;
import br.bunny.model.User;
import br.bunny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    final private UserRepository repository;
    final private ModelMapper mapper;

    public List<UserDTO> findAll(){
        return repository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .toList();
    }

    public UserDTO findById(Long id){
        return mapper.map(repository.findById(id), UserDTO.class);
    }

    public boolean existsById(Long id){
        return repository.existsById(id);
    }

    public UserDTO save (User user){
        return mapper.map(repository.save(user), UserDTO.class);
    }

    public UserDTO activateOrDeactivate(Long id){
        Optional <User> user = repository.findById(id);

        if(user.isEmpty())
            return null;

        user.get().setAtivo(!user.get().isAtivo());
        return mapper.map(repository.save(user.get()), UserDTO.class);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
