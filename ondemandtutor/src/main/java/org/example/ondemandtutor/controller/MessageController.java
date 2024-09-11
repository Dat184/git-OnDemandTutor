package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.response.FileResponse;
import org.example.ondemandtutor.dto.response.MessageResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;
    SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/send")
    public ResponseEntity<ResponseObject> sendMessage(@Payload MessageRequest messageRequest) {
        try {

            MessageResponse message = messageService.sendMessage(messageRequest);
            messagingTemplate.convertAndSendToUser(messageRequest.getUserNameRecipient(),"/queue/messages", new MessageResponse(
                    message.getId(),
                    message.getCreatedAt(),
                    message.getMessageText(),
                    message.getFileUrl(),
                    message.getUserNameSender(),
                    message.getUserNameRecipient(),
                    message.getChatId(),
                    message.getTypeMessage(),
                    message.getRecipientImgUrl()
            ));
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

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileResponse fileResponse = messageService.sendFile(file);
            ResponseObject response = new ResponseObject("success", "File uploaded", fileResponse);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "File processing error"));
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
