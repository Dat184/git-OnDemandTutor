package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ChatRequest;
import org.example.ondemandtutor.dto.response.ChatResponse;
import org.example.ondemandtutor.pojo.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {


    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "recipientName", source = "recipient.name")
    @Mapping(target = "userNameRecipient", source = "recipient.username")
    @Mapping(target = "imgUrl", source = "recipient.imgUrl")
    ChatResponse toChatResponse(Chat chat);
}
