package org.vb.model.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservas")
@EntityListeners(AuditingEntityListener.class)

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "modalidad", nullable = false)
    private String modalidad;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "entrenador_id", nullable = false)
    private UUID entrenadorId;

    @Column(name = "fecha_reserva", nullable = false)
    private Instant fechaReserva;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Instant fechaRegistro;

    public Reserva(){}

    public Reserva(UUID id, String estado, String modalidad, UUID clienteId, UUID entrenadorId, Instant fechaReserva) {
        this.id = id;
        this.estado = estado;
        this.modalidad = modalidad;
        this.clienteId = clienteId;
        this.entrenadorId = entrenadorId;
        this.fechaReserva = fechaReserva;
    }

    public UUID getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public UUID getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(UUID entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Instant getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Instant fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

}
