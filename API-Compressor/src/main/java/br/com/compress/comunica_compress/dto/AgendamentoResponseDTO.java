package br.com.compress.comunica_compress.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.compress.comunica_compress.model.Agendamento.Recorrencia;

public record AgendamentoResponseDTO(
        Integer id,
        Integer compressorId,
        Boolean comando,
        Recorrencia recorrencia,
        LocalDateTime dataHoraExecucao,
        Integer diaMes,
        DayOfWeek diaSemana,
        LocalTime horaExecucao,
        Boolean executado,
        LocalDateTime dataHoraExecucaoReal,
        String descricao) {

}
