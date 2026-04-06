package com.chatop.api.mappers;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)   // Géré dans le service
    @Mapping(target = "rental", ignore = true) // Géré dans le service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Message toEntity(MessageRequest request);
}
