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
    private String clienteId;

    @Column(name = "entrenador_id", nullable = false)
    private String entrenadorId;

    @Column(name = "fecha_reserva", nullable = false)
    private Instant fechaReserva;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Instant fechaRegistro;

    public Reserva(){}

    public Reserva(UUID id, String estado, String modalidad, String clienteId, String entrenadorId, Instant fechaReserva) {
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

    public String getClienteId() {
        return clienteId;
    }

    public String getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(String entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public void setClienteId(String clienteId) {
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
