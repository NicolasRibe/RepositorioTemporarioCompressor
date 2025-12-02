package br.com.compress.comunica_compress.dto;

import org.springframework.lang.NonNull;

public record CompressorDadosRequestDTO(
                Boolean ligado,
                String estado,
                Float temperaturaArComprimido,
                Float temperaturaAmbiente,
                Float temperaturaOleo,
                Float temperaturaOrvalho,
                Float pressaoArComprimido,
                Float horaCarga,
                Float horaTotal,
                Float pressaoAlivio,
                Float pressaoCarga,
                @NonNull Integer compressorId,
                @NonNull String falhaId,
                @NonNull String alertaId) {
}
