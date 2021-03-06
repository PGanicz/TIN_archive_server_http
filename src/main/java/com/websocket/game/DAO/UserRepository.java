package com.websocket.game.DAO;

import com.gs.collections.impl.utility.internal.IterableIterate;
import com.websocket.game.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);
    Iterable<User>  findByActiveFalse();
}
