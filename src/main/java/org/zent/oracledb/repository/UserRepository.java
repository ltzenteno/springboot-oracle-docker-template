package org.zent.oracledb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zent.oracledb.model.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
