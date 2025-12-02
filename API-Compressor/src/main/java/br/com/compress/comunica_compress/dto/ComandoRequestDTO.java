package br.com.compress.comunica_compress.dto;

import org.springframework.lang.NonNull;

public record ComandoRequestDTO(
        @NonNull Integer compressorId,
        Boolean comando) {
}
