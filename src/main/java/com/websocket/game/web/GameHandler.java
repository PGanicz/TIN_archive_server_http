package com.websocket.game.web;

import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.GameMessage;
import com.websocket.game.domain.GameRoom;
import com.websocket.game.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by piotr on 31.03.16.
 */
public class GameHandler extends TextWebSocketHandler{

    private Map<String,Player> players = new ConcurrentHashMap<String,Player>();
    private Map<String,GameRoom> games = new ConcurrentHashMap<String,GameRoom>();

    @Autowired
    public UserRepository userRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connected");
        Player p = new Player(userRepository.findUserByUsername(session.getPrincipal().getName()),session);
        players.put(session.getId(),p);
        System.out.println(p);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        players.remove(session.getId());
        System.out.println("Connection closed with status "+status);
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: "+message);

        GameMessage mes = GameMessage.getMessage(message); //extracting the payload
        switch (mes){

            case INIT:
                break;
            case JOIN:
                break;
            case LEAVE:
                break;
            case START:
                break;
            case MOVE:
                break;
        }
    }


}
