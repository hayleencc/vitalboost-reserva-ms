package org.vb.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.UUID;

public class CreateReservaDTO {
    @NotBlank(message = "La modalidad es requerida para la reserva")
    @Pattern(regexp = "(?i)presencial|virtual", message = "La modalidad debe ser presencial o virtual")
    private String modalidad;

    @NotNull(message = "El ID del cliente es requerido")
    @JsonProperty("clienteId")
    private UUID clienteId;

    @NotNull(message = "El ID del entrenador es requerido")
    @JsonProperty("entrenadorId")
    private UUID entrenadorId;

    @NotNull(message = "La fecha de reserva es requerida")
    private Instant fechaReserva;

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public UUID getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(UUID entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public Instant getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Instant fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}
