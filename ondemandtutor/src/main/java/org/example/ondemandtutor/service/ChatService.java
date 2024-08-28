package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ChatRequest;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;



@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
    ChatRepository chatRepository;
    UserRepository userRepository;
    // Tạo chat giữa tutor và student
    public Chat createChat(ChatRequest chatRequest) {
        User sender = userRepository.findById(chatRequest.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("SenderId not found"));
        User recipient = userRepository.findById(chatRequest.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("RecipientId not found"));

        // Kiểm tra chat đã tồn tại chưa
        Optional<Chat> existingChat = chatRepository.findBySenderAndRecipient(sender, recipient);
        if (existingChat.isPresent()) {
            return existingChat.get();
        }
        // Tạo phòng chat mới
        Chat chatroom = Chat.builder()
                .sender(sender)
                .recipient(recipient)
                .build();
        return chatRepository.save(chatroom);
    }



    public void deleteChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        chatRepository.delete(chat);
    }
}