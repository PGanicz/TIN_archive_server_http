package com.websocket.game.DAO;

import com.websocket.game.domain.ActivateUser;
import com.websocket.game.domain.File;
import com.websocket.game.domain.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by piotr on 02.06.16.
 */
public interface FileVersionReposiotry extends JpaRepository<FileVersion, Long> {
    Set<FileVersion> findByFileId(Long id);
}
