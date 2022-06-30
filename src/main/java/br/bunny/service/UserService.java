package br.bunny.service;

import br.bunny.dto.UserDTO;
import br.bunny.model.User;
import br.bunny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    public UserDTO save (User user){
        return mapper.map(repository.save(user), UserDTO.class);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
