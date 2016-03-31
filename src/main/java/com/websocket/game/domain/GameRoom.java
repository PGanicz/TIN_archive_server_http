package com.websocket.game.domain;

import io.netty.util.internal.ConcurrentSet;

import java.util.Set;

/**
 * Created by piotr on 31.03.16.
 */
public class GameRoom
{
    Set<Player> players = new ConcurrentSet<Player>();

}
