package org.vb.dto.request;

import jakarta.validation.constraints.NotNull;
import org.vb.enums.EstadoReserva;
import org.vb.enums.validators.SubsetEnum;

public class UpdateReservaDTO {
    @NotNull
    @SubsetEnum(anyOf = { EstadoReserva.TERMINADA, EstadoReserva.CANCELADA })
    private EstadoReserva estado;

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
