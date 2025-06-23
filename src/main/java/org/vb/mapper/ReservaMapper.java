package org.vb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.model.entity.Reserva;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ReservaMapper {
    @Mapping(source = "clienteId", target = "clienteId")
    @Mapping(source = "entrenadorId", target = "entrenadorId")
    public abstract Reserva toEntity(CreateReservaDTO reservaDTO);
    public abstract ReservaResponseDTO toResponseDTO(Reserva reserva);

}