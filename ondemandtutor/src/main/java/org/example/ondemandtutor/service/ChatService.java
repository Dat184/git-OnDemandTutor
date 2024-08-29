package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ChatRequest;
import org.example.ondemandtutor.dto.response.ChatResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.ChatMapper;
import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
    ChatRepository chatRepository;
    UserRepository userRepository;
    ChatMapper chatMapper;
    // Tạo chat giữa tutor và student
    public ChatResponse createChat(ChatRequest chatRequest) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User recipient = userRepository.findById(chatRequest.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("RecipientId not found"));

        // Kiểm tra chat đã tồn tại chưa
        Optional<Chat> existingChat = chatRepository.findBySenderAndRecipient(user, recipient);
        if (existingChat.isPresent()) {
            return chatMapper.toChatResponse(existingChat.get());
        }
        // Tạo phòng chat mới
        Chat chatroom = new Chat();
        chatroom.setSender(user);
        chatroom.setRecipient(recipient);
        chatMapper.toChat(chatRequest);

        return chatMapper.toChatResponse(chatRepository.save(chatroom));
    }



    public void deleteChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        chatRepository.delete(chat);
    }
}