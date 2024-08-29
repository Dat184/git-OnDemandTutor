package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.MessageRequest;
import org.example.ondemandtutor.dto.response.MessageResponse;
import org.example.ondemandtutor.pojo.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage(MessageRequest messageRequest);

    @Mapping(target = "sendId", source = "send.id")
    @Mapping(target = "chatId", source = "chat.id")
    MessageResponse toMessageResponse(Message message);


    List<MessageResponse> toMessageResponseList(List<Message> messages);
}
