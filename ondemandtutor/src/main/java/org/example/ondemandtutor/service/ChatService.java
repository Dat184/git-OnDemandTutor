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
import org.example.ondemandtutor.pojo.Role;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.ChatRepository;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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
        Optional<Chat> existingChat1 = chatRepository.findBySenderAndRecipient(user, recipient);
        Optional<Chat> existingChat2 = chatRepository.findBySenderAndRecipient(recipient, user);
        if (existingChat1.isPresent()) {
            return chatMapper.toChatResponse(existingChat1.get());
        } else if (existingChat2.isPresent()) {
            return chatMapper.toChatResponse(existingChat2.get());
        } else{
            // Tạo phòng chat mới
            Chat chatroom = new Chat();
            chatroom.setSender(user);
            chatroom.setRecipient(recipient);
            return chatMapper.toChatResponse(chatRepository.save(chatroom));
        }
    }

    public List<ChatResponse> getChatBySenderId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Chat> chats;
        if (currentUser.getRole() == Role.Student) {
            chats = chatRepository.findBySender(currentUser);
        } else {
            chats = chatRepository.findByRecipient(currentUser);
        }
        return chats.stream().map(chat ->
                        ChatResponse.builder()
                        .id(chat.getId())
                        .senderId(chat.getSender().getId())

                        .recipientId(chat.getRecipient().getId())
                        .recipientName(currentUser.equals(chat.getSender())
                                ? chat.getRecipient().getName()
                                : chat.getSender().getName())
                        .userNameRecipient(currentUser.equals(chat.getSender())
                                ? chat.getRecipient().getUsername()
                                : chat.getSender().getUsername())
                        .imgUrl(currentUser.equals(chat.getSender())
                                ? chat.getRecipient().getImgUrl()
                                : chat.getSender().getImgUrl())
                        .build())
                .collect(Collectors.toList());
    }


    public void deleteChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        chatRepository.delete(chat);
    }

    public ChatResponse getChatById(Long chatId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        return ChatResponse.builder()
                .id(chat.getId())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .recipientName(currentUser.equals(chat.getSender())
                        ? chat.getRecipient().getName()
                        : chat.
                        getSender().getName())
                .userNameRecipient(currentUser.equals(chat.getSender())
                        ? chat.getRecipient().getUsername()
                        : chat.getSender().getUsername())
                .imgUrl(currentUser.equals(chat.getSender())
                        ? chat.getRecipient().getImgUrl()
                        : chat.getSender().getImgUrl())
                .build();
    }
}