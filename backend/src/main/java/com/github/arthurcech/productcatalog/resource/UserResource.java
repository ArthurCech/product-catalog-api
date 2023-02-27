package com.github.arthurcech.productcatalog.resource;

import com.github.arthurcech.productcatalog.dto.user.UserDTO;
import com.github.arthurcech.productcatalog.dto.user.UserInsertDTO;
import com.github.arthurcech.productcatalog.dto.user.UserUpdateDTO;
import com.github.arthurcech.productcatalog.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(
            @RequestBody @Valid UserInsertDTO userInsertDTO
    ) {
        UserDTO userDTO = userService.create(userInsertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @PutMapping(value = "/{id}")
    public UserDTO update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateDTO userUpdateDTO
    ) {
        return userService.update(id, userUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
