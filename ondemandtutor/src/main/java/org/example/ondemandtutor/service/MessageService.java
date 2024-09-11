package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.response.FileResponse;
import org.example.ondemandtutor.dto.response.MessageResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.MessageMapper;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.MessageRepository;

import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {
    MessageRepository messageRepository;
    FirebaseStorageService firebaseStorageService;
    MessageMapper messageMapper;

    public MessageResponse sendMessage(MessageRequest messageRequest) throws IOException {
        Message message = messageMapper.toMessage(messageRequest);
        message.setCreatedAt(LocalDateTime.now());
        return messageMapper.toMessageResponse(messageRepository.save(message));
    }

    public FileResponse sendFile(MultipartFile file) throws IOException{
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String fileUrl = firebaseStorageService.uploadFile(fileName, inputStream, file.getContentType());
        return new FileResponse(file.getOriginalFilename(), fileUrl);
    }

    public List<MessageResponse> getMessagesByChatId(Long chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);
        return messageMapper.toMessageResponseList(messages);
    }


    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        messageRepository.delete(message);
    }
}
