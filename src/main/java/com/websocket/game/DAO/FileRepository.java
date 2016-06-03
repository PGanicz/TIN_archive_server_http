package com.websocket.game.DAO;

import com.websocket.game.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by piotr on 02.06.16.
 */
public interface FileRepository extends JpaRepository<File, Long> {

    Set<File> findByUserId( Long id);
}
