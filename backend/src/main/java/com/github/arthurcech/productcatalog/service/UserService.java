package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.User;
import com.github.arthurcech.productcatalog.dto.user.UserDTO;
import com.github.arthurcech.productcatalog.dto.user.UserInsertDTO;
import com.github.arthurcech.productcatalog.dto.user.UserUpdateDTO;
import com.github.arthurcech.productcatalog.exception.DatabaseException;
import com.github.arthurcech.productcatalog.exception.ResourceNotFoundException;
import com.github.arthurcech.productcatalog.mapper.UserMapper;
import com.github.arthurcech.productcatalog.repository.RoleRepository;
import com.github.arthurcech.productcatalog.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper.INSTANCE::toUserDTO);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public UserDTO create(UserInsertDTO userInsertDTO) {
        try {
            User user = UserMapper.INSTANCE.toUser(userInsertDTO);
            userInsertDTO.getRoles().forEach(roleDto -> {
                user.getRoles().add(roleRepository.getOne(roleDto.getId()));
            });
            user.setPassword(bCryptPasswordEncoder.encode(userInsertDTO.getPassword()));
            userRepository.save(user);
            return UserMapper.INSTANCE.toUserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Role not found");
        }
    }

    @Transactional
    public UserDTO update(
            Long id,
            UserUpdateDTO userUpdateDTO
    ) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            UserMapper.INSTANCE.updateUserFromDTO(userUpdateDTO, user);
            user.getRoles().clear();
            userUpdateDTO.getRoles().forEach(roleDto -> {
                user.getRoles().add(roleRepository.getOne(roleDto.getId()));
            });
            userRepository.save(user);
            return UserMapper.INSTANCE.toUserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Role not found");
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("User not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

}
