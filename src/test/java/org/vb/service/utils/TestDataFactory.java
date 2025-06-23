package org.vb.service.utils;

import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.model.entity.Reserva;

import java.time.Instant;
import java.util.UUID;

public class TestDataFactory {
    public static CreateReservaDTO createReservaDTO(){
        CreateReservaDTO dto = new CreateReservaDTO();
        dto.setModalidad("presencial");
        dto.setClienteId(UUID.fromString("92256a21-5329-4daf-a481-5138bbf763c0"));
        dto.setEntrenadorId(UUID.fromString("1d0e247c-51fc-45b0-9f02-bb0f2dfd099f"));
        dto.setFechaReserva(Instant.parse("2025-06-23T12:00:00.000Z"));
        return dto;
    }

    public static Reserva createReservaEntity(){
        Reserva reserva = new Reserva();
        reserva.setModalidad("presencial");
        reserva.setClienteId(UUID.fromString("92256a21-5329-4daf-a481-5138bbf763c0"));
        reserva.setEntrenadorId(UUID.fromString("1d0e247c-51fc-45b0-9f02-bb0f2dfd099f"));
        reserva.setFechaReserva(Instant.parse("2025-06-23T12:00:00.000Z"));
        return reserva;
    }

    public static ReservaResponseDTO reservaResponseDTO(){
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setModalidad("presencial");
        dto.setClienteId(UUID.fromString("92256a21-5329-4daf-a481-5138bbf763c0"));
        dto.setEntrenadorId(UUID.fromString("1d0e247c-51fc-45b0-9f02-bb0f2dfd099f"));
        dto.setFechaReserva(Instant.parse("2025-06-23T12:00:00.000Z"));
        return dto;
    }
}
