package com.chatop.api.mappers;

import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalDto;
import com.chatop.api.models.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Indique à MapStruct que c'est un mapper et déclarer comme component Pour pouvoir l'injecter avec @Autowired
public interface RentalMapper {

    @Mapping(source = "owner.id", target = "owner_id")
    @Mapping(source = "createdAt", target = "created_at")
    @Mapping(source = "updatedAt", target = "updated_at")
    RentalDto toDto(Rental rental);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true) // On gère l'owner manuellement dans le service car mapStruct ne peut pas deviner l'objet User compléet en bdd
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Rental toEntity(RentalCreateRequest dto);
}