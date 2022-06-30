package br.bunny.controller;

import br.bunny.dto.UserDTO;
import br.bunny.model.User;
import br.bunny.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers(){
        try {
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        try{
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
