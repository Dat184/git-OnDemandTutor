package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Message;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.repository.MessageRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(MessageRequest messageRequest) throws IOException {
        Chat chat = chatService.createChat(messageRequest.getTutorId(), messageRequest.getStudentId());
        User sender = userRepository.getReferenceById(messageRequest.getSenderId());
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setMessageText(messageRequest.getMessageText());
        if(messageRequest.getFile()!= null) {
            message.setFileData(messageRequest.getFile().getBytes());
            message.setFileName(messageRequest.getFile().getOriginalFilename());
            message.setFileType(messageRequest.getFile().getContentType());
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}
