package br.com.compress.comunica_compress.dto;

import java.time.LocalDateTime;

public record FalhasDTO(
        String id,
        String descricao,
        LocalDateTime horario) {
}
