package org.vb.enums;

public enum ModalidadReserva {
    PRESENCIAL,
    VIRTUAL;

    public static ModalidadReserva fromString(String value) {
        return ModalidadReserva.valueOf(value.toUpperCase());
    }
}
