package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<Message> foundMessage = messageRepository.findById(id);
        return foundMessage.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query message successfully", foundMessage)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Message with id = " + id, "")
                );
    }

    @GetMapping("/chat/{chatId}")
    public List<Message> findByChatId(@PathVariable long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    @GetMapping("/sender/{senderId}")
    public List<Message> findBySenderId(@PathVariable long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertMessage(@RequestBody Message newMessage) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert message successfully", messageRepository.save(newMessage))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateMessage(@PathVariable long id, @RequestBody Message newMessage) {
        Message updatedMessage = messageRepository.findById(id)
                .map(message -> {
                    message.setCreatedAt(newMessage.getCreatedAt());
                    message.setMessageText(newMessage.getMessageText());
                    message.setSender(newMessage.getSender());
                    message.setChat(newMessage.getChat());
                    return messageRepository.save(message);
                }).orElseGet(() -> {
                    newMessage.setId(id);
                    return messageRepository.save(newMessage);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update message successfully", updatedMessage)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteMessage(@PathVariable long id) {
        boolean exists = messageRepository.existsById(id);
        if (exists) {
            messageRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete message successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Message with id = " + id, "")
            );
        }
    }
}
