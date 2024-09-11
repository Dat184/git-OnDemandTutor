package org.example.ondemandtutor.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ChatRequest;
import org.example.ondemandtutor.dto.response.ChatResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(path = "/v1/chat")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ChatController {
    ChatService chatService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteChatById(@PathVariable Long id) {
        try {
            chatService.deleteChatById(id);
            return ResponseEntity.ok().body(new ResponseObject("success", "Chat deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createChat(@RequestBody ChatRequest chatRequest) {
        try{
            ChatResponse chatResponse = chatService.createChat(chatRequest);
            return ResponseEntity.ok().body(new ResponseObject("success", "Chat created", chatResponse));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage()));
        }
    }

    @GetMapping("/getChat")
    public ResponseEntity<ResponseObject> getChatBySenderId() {
        return ResponseEntity.ok().body(new ResponseObject("success", "Chat retrieved", chatService.getChatBySenderId()));
    }

    @GetMapping("/getChat/{id}")
    public ResponseEntity<ResponseObject> getChatById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(new ResponseObject("success", "Chat retrieved", chatService.getChatById(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage()));
        }
    }

}
