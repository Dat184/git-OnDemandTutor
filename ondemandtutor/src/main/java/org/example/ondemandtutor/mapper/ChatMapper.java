package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ChatRequest;
import org.example.ondemandtutor.dto.response.ChatResponse;
import org.example.ondemandtutor.pojo.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
//    @Mapping(target = "sender.id", source = "senderId")
//    @Mapping(target = "recipient.id", source = "recipientId")
//    Chat toChat(ChatRequest chatRequest);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    ChatResponse toChatResponse(Chat chat);
}
