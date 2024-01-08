package org.zent.oracledb.service;

import org.zent.oracledb.dto.CreateUserDTO;
import org.zent.oracledb.dto.UserDTO;

public interface UserService {
    UserDTO createUser(CreateUserDTO dto);
}
