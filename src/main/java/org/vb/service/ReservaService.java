package org.vb.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.request.UpdateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.enums.EstadoReserva;
import org.vb.enums.ModalidadReserva;
import org.vb.exception.HorarioNoDisponibleException;
import org.vb.mapper.ReservaMapper;
import org.vb.model.entity.Reserva;
import org.vb.repository.ReservaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    public ReservaResponseDTO createReserva(CreateReservaDTO reserva) {
        reserva.setModalidad(ModalidadReserva.fromString(reserva.getModalidad()).name());
        List<Reserva> reservasExistentes = reservaRepository.buscarReservasExistentesConEntrenador(reserva.getEntrenadorId(), reserva.getFechaReserva());
        if (!reservasExistentes.isEmpty()) {
            throw new HorarioNoDisponibleException("El horario seleccionado no está disponible");
        }
        Reserva nuevoReserva = reservaMapper.toEntity(reserva);
        nuevoReserva.setEstado(EstadoReserva.PENDIENTE.name());
        Reserva reservaGuardado = reservaRepository.save(nuevoReserva);
        return reservaMapper.toResponseDTO(reservaGuardado);
    }

    public List<ReservaResponseDTO> getReservasPorCliente(String id) {
        List<Reserva> reservas = reservaRepository.findByClienteId(id);
        return reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
    }

    public List<ReservaResponseDTO> getReservasPorEntrenador(String id) {
        List<Reserva> reservas = reservaRepository.findByEntrenadorId(id);
        return reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
    }

    public ReservaResponseDTO getReservaById(UUID id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la reserva con ID: " + id));
        return reservaMapper.toResponseDTO(reserva);
    }

    public ReservaResponseDTO updateReserva(UUID id, UpdateReservaDTO reservaToUpdate) {
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró al reserva con ID: " + id));

        reservaMapper.updateReservaFromDto(reservaToUpdate, existingReserva);

        Reserva reservaGuardado = reservaRepository.save(existingReserva);
        return reservaMapper.toResponseDTO(reservaGuardado);
    }
}
