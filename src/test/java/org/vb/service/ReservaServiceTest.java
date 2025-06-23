package org.vb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.enums.EstadoReserva;
import org.vb.exception.HorarioNoDisponibleException;
import org.vb.mapper.ReservaMapper;
import org.vb.model.entity.Reserva;
import org.vb.repository.ReservaRepository;
import org.vb.service.utils.TestDataFactory;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;


    @Test
    void createReserva_shouldReturnReservaResponseDTO() {
        CreateReservaDTO dto = TestDataFactory.createReservaDTO();
        Reserva reserva = TestDataFactory.createReservaEntity();
        ReservaResponseDTO response = TestDataFactory.reservaResponseDTO();

        when(reservaMapper.toEntity(dto)).thenReturn(reserva);
        when(reservaRepository.buscarReservasExistentesConEntrenador(dto.getEntrenadorId(), dto.getFechaReserva()))
                .thenReturn(Collections.emptyList());
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(reservaMapper.toResponseDTO(reserva)).thenReturn(response);

        ReservaResponseDTO resultado = reservaService.createReserva(dto);

        assertNotNull(resultado);
        assertEquals(response.getModalidad(), resultado.getModalidad());
        assertEquals(response.getClienteId(), resultado.getClienteId());
        verify(reservaRepository).buscarReservasExistentesConEntrenador(dto.getEntrenadorId(), dto.getFechaReserva());
        verify(reservaRepository).save(reserva);
        verify(reservaMapper).toEntity(dto);
        verify(reservaMapper).toResponseDTO(reserva);
    }

    @Test
    void createReserva_horarioOcupado_lanzaHorarioNoDisponibleException() {
        CreateReservaDTO dto = TestDataFactory.createReservaDTO();
        Reserva reserva = TestDataFactory.createReservaEntity();

        when(reservaRepository.buscarReservasExistentesConEntrenador(dto.getEntrenadorId(), dto.getFechaReserva()))
                .thenReturn(List.of(reserva));

        assertThrows(HorarioNoDisponibleException.class, () -> {
            reservaService.createReserva(dto);
        });

        verify(reservaRepository).buscarReservasExistentesConEntrenador(dto.getEntrenadorId(), dto.getFechaReserva());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void createReserva_asignaEstadoPendiente() {
        CreateReservaDTO dto = TestDataFactory.createReservaDTO();
        Reserva reserva = TestDataFactory.createReservaEntity();

        when(reservaMapper.toEntity(dto)).thenReturn(reserva);
        when(reservaRepository.buscarReservasExistentesConEntrenador(dto.getEntrenadorId(), dto.getFechaReserva()))
                .thenReturn(Collections.emptyList());
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(reservaMapper.toResponseDTO(reserva)).thenReturn(new ReservaResponseDTO());

        reservaService.createReserva(dto);

        assertEquals(EstadoReserva.PENDIENTE.name(), reserva.getEstado());
    }


}
