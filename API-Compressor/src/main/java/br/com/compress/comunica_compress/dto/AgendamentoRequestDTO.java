package br.com.compress.comunica_compress.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.compress.comunica_compress.model.Agendamento;

public record AgendamentoRequestDTO(
        Integer compressorId,
        Boolean comando,
        Agendamento.Recorrencia recorrencia,
        LocalDateTime dataHoraExecucao, // UNICO
        DayOfWeek diaSemana, // SEMANAL
        LocalTime horaExecucao, // SEMANAL || MENSAL
        Integer diaMes, // MENSAL
        String descricao) {

}
