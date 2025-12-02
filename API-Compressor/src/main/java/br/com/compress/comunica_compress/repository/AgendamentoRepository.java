package br.com.compress.comunica_compress.repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.Agendamento;
import br.com.compress.comunica_compress.model.Agendamento.Recorrencia;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    
    // UNICO vencido e ainda não executado
    List<Agendamento> findByRecorrenciaAndExecutadoFalseAndDataHoraExecucaoLessThanEqual(
            Recorrencia recorrencia, LocalDateTime dataHoraLimite);

    // SEMANAL
    List<Agendamento> findByRecorrenciaAndDiaSemana(
            Recorrencia recorrencia, DayOfWeek diaSemana);

    // MENSAL
    List<Agendamento> findByRecorrencia(Recorrencia recorrencia);
}
