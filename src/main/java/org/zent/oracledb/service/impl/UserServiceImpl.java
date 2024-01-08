package org.zent.oracledb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zent.oracledb.dto.CreateUserDTO;
import org.zent.oracledb.dto.UserDTO;
import org.zent.oracledb.model.auth.User;
import org.zent.oracledb.repository.UserRepository;
import org.zent.oracledb.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDTO createUser(CreateUserDTO dto) {
        var user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        User saved = userRepository.save(user);

        return entityToUserDTO(saved);
    }

    private UserDTO entityToUserDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
