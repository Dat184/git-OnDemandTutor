package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.pojo.request.MessageRequest;
import org.example.ondemandtutor.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest messageRequest) {
        Message message = messageService.sendMessage(messageRequest);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
