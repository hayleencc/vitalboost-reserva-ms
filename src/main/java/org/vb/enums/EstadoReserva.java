package org.vb.enums;

public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    TERMINADA;

    public static EstadoReserva fromString(String value) {
        return EstadoReserva.valueOf(value.toUpperCase());
    }
}
