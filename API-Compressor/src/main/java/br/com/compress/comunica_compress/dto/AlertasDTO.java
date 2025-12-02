package br.com.compress.comunica_compress.dto;

import java.time.LocalDateTime;

public record AlertasDTO(
        String id,
        String descricao,
        LocalDateTime horario
) {


}
