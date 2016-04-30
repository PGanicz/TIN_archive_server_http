package com.websocket.game.DAO;

import com.websocket.game.domain.ActivateUser;
import com.websocket.game.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivateUserRepository extends JpaRepository<ActivateUser, Long> {

    ActivateUser findActivateUserByActivateString(String activateString);

    List<ActivateUser> findActivateUsersByUser(User user);
}
