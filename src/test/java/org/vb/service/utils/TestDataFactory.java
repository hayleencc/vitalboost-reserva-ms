package org.vb.service.utils;

import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.request.UpdateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.enums.EstadoReserva;
import org.vb.model.entity.Reserva;

import java.time.Instant;
import java.util.UUID;

public class TestDataFactory {
    public static final UUID CLIENTE_ID_FIJO = UUID.fromString("1d0e247c-51fc-45b0-9f02-bb0f2dfd099f");
    public static final UUID ENTRENADOR_ID_FIJO = UUID.fromString("2a1b3c4d-51fc-45b0-9f02-bb0f2dfd099f");
    public static final UUID RESERVA_ID = UUID.fromString("44b93209-8ee9-4803-a913-82fb36f50c0d");
    public static final Instant FECHA_RESERVA = Instant.parse("2025-06-23T12:00:00.000Z");

    public static CreateReservaDTO createReservaDTO(){
        CreateReservaDTO dto = new CreateReservaDTO();
        dto.setModalidad("presencial");
        dto.setClienteId(CLIENTE_ID_FIJO);
        dto.setEntrenadorId(ENTRENADOR_ID_FIJO);
        dto.setFechaReserva(FECHA_RESERVA);
        return dto;
    }

    public static ReservaResponseDTO reservaResponseDTO(){
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(RESERVA_ID);
        dto.setModalidad("PRESENCIAL");
        dto.setClienteId(CLIENTE_ID_FIJO);
        dto.setEntrenadorId(ENTRENADOR_ID_FIJO);
        dto.setFechaReserva(Instant.parse("2025-06-23T12:00:00.000Z"));
        return dto;
    }

    public static Reserva createReservaEntity(){
        return new Reserva(RESERVA_ID, "PENDIENTE", "PRESENCIAL", CLIENTE_ID_FIJO, ENTRENADOR_ID_FIJO, FECHA_RESERVA);
    }

    public static UpdateReservaDTO updateReservaDTO(){
        UpdateReservaDTO dto = new UpdateReservaDTO();
        dto.setEstado(EstadoReserva.TERMINADA);
        return dto;
    }

}
