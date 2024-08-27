package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseObject> deleteChatById(@PathVariable Long id) {
//        try {
//            chatService.deleteChatById(id);
//            return ResponseEntity.ok().body(new ResponseObject("success", "Chat deleted"));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage()));
//        }
//    }

}
