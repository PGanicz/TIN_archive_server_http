package com.websocket.game.DAO;

import com.websocket.game.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by piotr on 30.03.16.
 */

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
}
