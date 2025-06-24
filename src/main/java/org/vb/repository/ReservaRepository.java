package org.vb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vb.model.entity.Reserva;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    @Query("SELECT r FROM Reserva r WHERE r.entrenadorId = :entrenadorId AND r.fechaReserva = :fechaReserva AND (r.estado = 'CONFIRMADA' OR r.estado = 'PENDIENTE')")
    List<Reserva> buscarReservasExistentesConEntrenador(String entrenadorId, Instant fechaReserva);

    List<Reserva> findByClienteId(String clienteId);
    List<Reserva> findByEntrenadorId(String entrenadorId);

}
