package com.chat_app.chat.controller;

import com.chat_app.chat.entity.ChatMessage;
import com.chat_app.chat.entity.Users;
import com.chat_app.chat.model.LoginRequest;
import com.chat_app.chat.model.Message;
import com.chat_app.chat.model.UserDto;
import com.chat_app.chat.repository.ChatMessageRepository;
import com.chat_app.chat.repository.UserRepository;
import com.chat_app.chat.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        // Find the user by username
//        Users user = userRepository.findByUsername(loginRequest.getUsername());
//        if (user == null) {
//            return ResponseEntity.status(404).body("User not found");
//        }
//
//        // Validate the password
//        if (!user.getPassword().equals(loginRequest.getPassword())) {
//            return ResponseEntity.status(401).body("Invalid password");
//        }
//
//        // Successful login
//        return ResponseEntity.ok("Login successful");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Users user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Set session attribute
        session.setAttribute("user", user);

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }


    @GetMapping("/search")
    public ResponseEntity<Users> findByUsername(@RequestParam String username) {
        Users user = userService.findByUsername(username);
        if (user == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Make sure to hash the password before saving
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }


//    // Handles public chat messages and broadcasts them to the public chatroom.
//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message receiveMessage(Message message) throws InterruptedException {
//        System.out.println(message);
//        // Simulate delay for demonstration (optional)
//        Thread.sleep(1000);
//        return message;
//    }


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(Message message) throws InterruptedException {
        // Save to the database
        chatMessageRepository.save(new ChatMessage(
                message.getSenderName(),
                message.getReceiverName(),
                message.getMessage(),
                message.getMedia(),
                message.getMediaType(),
                message.getStatus(),
                System.currentTimeMillis()  // Current timestamp
        ));

        // Simulate delay for demonstration (optional)
        Thread.sleep(1000);

        return message;
    }

//    // Handles private messages and sends them to the specified user.
//    @MessageMapping("/private-message")
//    public void privateMessage(Message message) {
//        String receiver = message.getReceiverName();
//        System.out.println("Sending private message to: " + receiver);
//        simpMessagingTemplate.convertAndSendToUser(receiver, "/private", message);
//    }


    @MessageMapping("/private-message")
    public void privateMessage(Message message) {
        String receiver = message.getReceiverName();
        simpMessagingTemplate.convertAndSendToUser(receiver, "/private", message);

        // Save private message to the database
        chatMessageRepository.save(new ChatMessage(
                message.getSenderName(),
                message.getReceiverName(),
                message.getMessage(),
                message.getMedia(),
                message.getMediaType(),
                message.getStatus(),
                System.currentTimeMillis()  // Current timestamp
        ));
    }

    @GetMapping("/api/messages/history/{user1}/{user2}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2
    ) {
        List<ChatMessage> messages = chatMessageRepository.findByReceiverNameOrSenderName(user1, user2);
        return ResponseEntity.ok(messages);
    }
}

