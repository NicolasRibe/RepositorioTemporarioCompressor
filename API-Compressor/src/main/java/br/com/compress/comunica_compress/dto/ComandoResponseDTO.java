package br.com.compress.comunica_compress.dto;

import java.time.LocalDateTime;

public record ComandoResponseDTO(Integer compressorId,
        Boolean comando,
        LocalDateTime dataHora) {
}
