package com.websocket.game.DAO;

import com.websocket.game.domain.ActivateUser;
import com.websocket.game.domain.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by piotr on 02.06.16.
 */
public interface FileVersionReposiotry extends JpaRepository<FileVersion, Long> {
}
