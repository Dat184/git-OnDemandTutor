package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.response.MessageResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<ResponseObject> sendMessage(
            @RequestParam("senderId") Long senderId,
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("studentId") Long studentId,
            @RequestParam(value = "messageText", required = false) String messageText,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            MessageRequest messageRequest = new MessageRequest(senderId, tutorId, studentId, messageText, file);

            MessageResponse message = messageService.sendMessage(messageRequest);
            ResponseObject response = new ResponseObject("success", "Message sent", message);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "File processing error"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ResponseObject> getMessagesByChatId(@PathVariable Long chatId) {
        try {
            List<MessageResponse> messages = messageService.getMessagesByChatId(chatId);
            ResponseObject response = new ResponseObject("success", "Messages found", messages);
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ResponseObject> deleteMessage(@PathVariable Long messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("success", "Message deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", "Message not deleted"));
        }
    }
}
