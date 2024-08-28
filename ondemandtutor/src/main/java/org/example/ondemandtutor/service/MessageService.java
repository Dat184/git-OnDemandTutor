package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.response.MessageResponse;
import org.example.ondemandtutor.mapper.MessageMapper;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.repository.MessageRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MessageService {
    final MessageRepository messageRepository;
    final ChatService chatService;
    final FirebaseStorageService firebaseStorageService;
    final MessageMapper messageMapper;

    public MessageResponse sendMessage(MessageRequest messageRequest) throws IOException {
        Chat chat = chatService.createChat(messageRequest.getTutorId(), messageRequest.getStudentId());

        String fileUrl = null;
        if(messageRequest.getFile() != null) {
            String fileName = UUID.randomUUID().toString() + "_" + messageRequest.getFile().getOriginalFilename();
            InputStream inputStream = messageRequest.getFile().getInputStream();
            fileUrl = firebaseStorageService.uploadFile(fileName, inputStream, messageRequest.getFile().getContentType());
        }

        Message message = messageMapper.toMessage(messageRequest);

        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());

        if(fileUrl != null) {
            message.setName(messageRequest.getFile().getOriginalFilename());
            message.setType(messageRequest.getFile().getContentType());
            message.setFileUrl(fileUrl);
        }

        return messageMapper.toMessageResponse(messageRepository.save(message));
    }

    public List<MessageResponse> getMessagesByChatId(Long chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);
        return messageMapper.toMessageResponseList(messages);
    }


    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        if(message.getFileUrl() != null){
            firebaseStorageService.deleteFile(message.getFileUrl());
        }
        messageRepository.delete(message);
    }
}
