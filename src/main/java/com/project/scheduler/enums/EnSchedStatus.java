package com.project.scheduler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnSchedStatus {
    PENDING("Pendente"),
    CONFIRMED("Confirmado"),
    DONE("Concluído"),
    CANCELLED("Cancelado");

    private final String label;
}

