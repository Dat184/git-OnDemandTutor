package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<ResponseObject> sendMessage(
            @RequestParam("senderId") Long senderId,
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("studentId") Long studentId,
            @RequestParam("messageText") String messageText,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setSenderId(senderId);
            messageRequest.setTutorId(tutorId);
            messageRequest.setStudentId(studentId);
            messageRequest.setMessageText(messageText);
            messageRequest.setFile(file);

            Message message = messageService.sendMessage(messageRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("success", "Message sent", message));

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
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("success", "Messages retrieved", messages));
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
