package br.bunny.controller;

import br.bunny.dto.UserDTO;
import br.bunny.exception.user.NotFoundException;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.model.user.User;
import br.bunny.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable UUID id) {
        if (!userService.existsUserById(id))
            throw new NotFoundException("Usuário não encontrado.");
        return ResponseEntity.ok(mapper.map(userService.findUserById(id), UserDTO.class));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userRequest) {
        if (userService.existsUserByEmail(userRequest.getEmail()))
            throw new BadRequestException("Já existe um usuário cadastrado com este email.");
        User user = new User();
        mapper.map(userRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(userService.saveUser(user), UserDTO.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> activateOrDesactivateUser(@PathVariable("id") UUID id, boolean ativo) {
        if (!userService.existsUserById(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(userService.activateOrDesactivateUser(id, ativo), UserDTO.class));
    }
}
