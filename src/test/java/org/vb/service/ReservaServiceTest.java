package org.vb.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.request.UpdateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.enums.EstadoReserva;
import org.vb.exception.HorarioNoDisponibleException;
import org.vb.mapper.ReservaMapper;
import org.vb.model.entity.Reserva;
import org.vb.repository.ReservaRepository;
import org.vb.service.utils.TestDataFactory;

import java.util.*;

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
        String clienteId = "string-como-cliente-id";
        List<Reserva> reservas = new ArrayList<>();
        when(reservaRepository.findByClienteId(clienteId)).thenReturn(reservas);

        List<ReservaResponseDTO> result = reservaService.getReservasPorCliente(clienteId);

        assertEquals(0, result.size());
        verify(reservaRepository).findByClienteId(clienteId);
    }

    @Test
    void getReservasPorCliente_siExistenCreadas_retornaLista() {
        String clienteId= TestDataFactory.CLIENTE_ID_FIJO;
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
        String entrenadorId = "string-como-entrenador-id";
        List<Reserva> reservas = new ArrayList<>();
        when(reservaRepository.findByEntrenadorId(entrenadorId)).thenReturn(reservas);

        List<ReservaResponseDTO> result = reservaService.getReservasPorEntrenador(entrenadorId);

        assertEquals(0, result.size());
        verify(reservaRepository).findByEntrenadorId(entrenadorId);
    }

    @Test
    void getReservasPorEntrenador_siExistenCreadas_retornaLista() {
        String entrenadorId= TestDataFactory.ENTRENADOR_ID_FIJO;
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

    @Test
    void getReservaById_enviandoIdExistente_retornaReserva() {
        UUID reservaId = TestDataFactory.RESERVA_ID;
        Reserva reserva = TestDataFactory.createReservaEntity();
        ReservaResponseDTO responseDTO = TestDataFactory.reservaResponseDTO();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
        when(reservaMapper.toResponseDTO(reserva)).thenReturn(responseDTO);


        ReservaResponseDTO result = reservaService.getReservaById(reservaId);

        assertNotNull(result);
        assertEquals(reservaId, result.getId());
        assertEquals("PRESENCIAL", result.getModalidad());
        verify(reservaRepository).findById(reservaId);
        verify(reservaMapper).toResponseDTO(reserva);
    }

    @Test
    void getReservaById_nonExistingId_shouldThrowException() {
        UUID id = UUID.randomUUID();

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            reservaService.getReservaById(id);
        });

        assertTrue(exception.getMessage().contains(id.toString()));
        verify(reservaRepository).findById(id);
    }

    @Test
    void updateReserva_enviandoIdExistente_retornaReservaActualizada() {
        CreateReservaDTO dto = TestDataFactory.createReservaDTO();
        Reserva existingReserva = TestDataFactory.createReservaEntity();
        UpdateReservaDTO reservaUpdateDTO = TestDataFactory.updateReservaDTO();
        UUID reservaId = existingReserva.getId();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(existingReserva));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doAnswer(invocation -> {
            UpdateReservaDTO dtoArg = invocation.getArgument(0);
            Reserva reservaArg = invocation.getArgument(1);

            if (dtoArg.getEstado() != null) {
                reservaArg.setEstado(dtoArg.getEstado().name());
            }
            return null;
        }).when(reservaMapper).updateReservaFromDto(any(UpdateReservaDTO.class), any(Reserva.class));

        ReservaResponseDTO expectedResponseDTO = new ReservaResponseDTO();
        expectedResponseDTO.setId(reservaId);
        expectedResponseDTO.setEstado(reservaUpdateDTO.getEstado().name());

        when(reservaMapper.toResponseDTO(any(Reserva.class))).thenReturn(expectedResponseDTO);

        ReservaResponseDTO result = reservaService.updateReserva(reservaId, reservaUpdateDTO);

        assertNotNull(result);
        assertEquals(reservaId, result.getId());
        assertEquals(EstadoReserva.TERMINADA.name(), result.getEstado());

        verify(reservaRepository).findById(reservaId);
        verify(reservaMapper).updateReservaFromDto(eq(reservaUpdateDTO), any(Reserva.class));
        verify(reservaRepository).save(any(Reserva.class));
        verify(reservaMapper).toResponseDTO(any(Reserva.class));
    }
}
