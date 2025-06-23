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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    @Test
    void getReservasPorCliente_siNoExistenCreadas_retornaListaVacia() {
        UUID clienteId = UUID.randomUUID();
        List<Reserva> reservas = new ArrayList<>();
        when(reservaRepository.findByClienteId(clienteId)).thenReturn(reservas);

        List<ReservaResponseDTO> result = reservaService.getReservasPorCliente(clienteId);

        assertEquals(0, result.size());
        verify(reservaRepository).findByClienteId(clienteId);
    }

    @Test
    void getReservasPorCliente_siExistenCreadas_retornaLista() {
        UUID clienteId= TestDataFactory.CLIENTE_ID_FIJO;
        Reserva reserva = TestDataFactory.createReservaEntity();
        ReservaResponseDTO responseDTO = TestDataFactory.reservaResponseDTO();
        List<Reserva> reservas = List.of(reserva);

        when(reservaRepository.findByClienteId(clienteId)).thenReturn(reservas);
        when(reservaMapper.toResponseDTO(reserva)).thenReturn(responseDTO);
        List<ReservaResponseDTO> result = reservaService.getReservasPorCliente(clienteId);


        assertEquals(1, result.size());
        assertEquals(clienteId, result.get(0).getClienteId());
        verify(reservaRepository).findByClienteId(clienteId);
    }

    @Test
    void getReservasPorEntrenador_siNoExistenCreadas_retornaListaVacia() {
        UUID entrenadorId = UUID.randomUUID();
        List<Reserva> reservas = new ArrayList<>();
        when(reservaRepository.findByEntrenadorId(entrenadorId)).thenReturn(reservas);

        List<ReservaResponseDTO> result = reservaService.getReservasPorEntrenador(entrenadorId);

        assertEquals(0, result.size());
        verify(reservaRepository).findByEntrenadorId(entrenadorId);
    }

    @Test
    void getReservasPorEntrenador_siExistenCreadas_retornaLista() {
        UUID entrenadorId= TestDataFactory.ENTRENADOR_ID_FIJO;
        Reserva reserva = TestDataFactory.createReservaEntity();
        ReservaResponseDTO responseDTO = TestDataFactory.reservaResponseDTO();
        List<Reserva> reservas = List.of(reserva);

        when(reservaRepository.findByEntrenadorId(entrenadorId)).thenReturn(reservas);
        when(reservaMapper.toResponseDTO(reserva)).thenReturn(responseDTO);
        List<ReservaResponseDTO> result = reservaService.getReservasPorEntrenador(entrenadorId);


        assertEquals(1, result.size());
        assertEquals(entrenadorId, result.get(0).getEntrenadorId());
        verify(reservaRepository).findByEntrenadorId(entrenadorId);
    }
}
