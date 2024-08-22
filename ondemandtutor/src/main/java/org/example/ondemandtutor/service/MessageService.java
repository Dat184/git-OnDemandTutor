package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.repository.MessageRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(MessageRequest messageRequest) {
        Chat chat = chatService.createChat(messageRequest.getTutorId(), messageRequest.getStudentId());
        User sender = userRepository.getReferenceById(messageRequest.getSenderId());
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setMessageText(messageRequest.getMessageText());
        message.setFileData(messageRequest.getFileData());
        message.setFileName(messageRequest.getFileName());
        message.setFileType(messageRequest.getFileType());

        return messageRepository.save(message);
    }
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }

    public Message deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        messageRepository.delete(message);
        return message;
    }
}
