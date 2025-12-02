package br.com.compress.comunica_compress.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compress.comunica_compress.dto.ComandoRequestDTO;
import br.com.compress.comunica_compress.model.Agendamento;
import br.com.compress.comunica_compress.model.Agendamento.Recorrencia;
import br.com.compress.comunica_compress.repository.AgendamentoRepository;

@Service
public class AgendamentoExecutor {

    private final AgendamentoRepository agendamentoRepository;
    private final ComandoService comandoService;

    public AgendamentoExecutor(AgendamentoRepository agendamentoRepository,
                               ComandoService comandoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.comandoService = comandoService;
    }

    @Scheduled(fixedDelay = 60000) // a cada 60 segundos
    @Transactional
    public void executarAgendamentos() {
        LocalDateTime agora = LocalDateTime.now();
        LocalTime horaAgora = agora.toLocalTime().withSecond(0).withNano(0);
        DayOfWeek diaSemanaAgora = agora.getDayOfWeek();
        int diaMesAgora = agora.getDayOfMonth();

        executarUnicos(agora);
        executarSemanais(diaSemanaAgora, horaAgora);
        executarMensais(diaMesAgora, horaAgora);
    }

    private void executarUnicos(LocalDateTime agora) {
        List<Agendamento> pendentes = agendamentoRepository
                .findByRecorrenciaAndExecutadoFalseAndDataHoraExecucaoLessThanEqual(
                        Recorrencia.UNICO, agora);

        for (Agendamento ag : pendentes) {
            dispararComando(ag);
            ag.setExecutado(true);
            ag.setDataHoraExecucaoReal(LocalDateTime.now());
            agendamentoRepository.save(ag);
        }
    }

    private void executarSemanais(DayOfWeek diaSemanaAgora, LocalTime horaAgora) {
        LocalTime inicioJanela = horaAgora.minusMinutes(1);
        LocalTime fimJanela = horaAgora.plusMinutes(1);

        List<Agendamento> semanais = agendamentoRepository
                .findByRecorrenciaAndDiaSemana(Recorrencia.SEMANAL, diaSemanaAgora);

        for (Agendamento ag : semanais) {
            if (ag.getHoraExecucao() == null) continue;

            boolean bateHorario =
                    !ag.getHoraExecucao().isBefore(inicioJanela) &&
                    !ag.getHoraExecucao().isAfter(fimJanela);

            boolean jaExecutouHoje =
                    ag.getDataHoraExecucaoReal() != null &&
                    ag.getDataHoraExecucaoReal().toLocalDate().equals(LocalDate.now());

            if (bateHorario && !jaExecutouHoje) {
                dispararComando(ag);
                ag.setDataHoraExecucaoReal(LocalDateTime.now());
                agendamentoRepository.save(ag);
            }
        }
    }

    private void executarMensais(int diaMesAgora, LocalTime horaAgora) {
        LocalTime inicioJanela = horaAgora.minusMinutes(1);
        LocalTime fimJanela = horaAgora.plusMinutes(1);

        List<Agendamento> mensais = agendamentoRepository
                .findByRecorrencia(Recorrencia.MENSAL);

        for (Agendamento ag : mensais) {
            if (ag.getDiaMes() == null || ag.getHoraExecucao() == null) continue;

            if (!ag.getDiaMes().equals(diaMesAgora)) continue;

            boolean bateHorario =
                    !ag.getHoraExecucao().isBefore(inicioJanela) &&
                    !ag.getHoraExecucao().isAfter(fimJanela);

            boolean jaExecutouEsteMes =
                    ag.getDataHoraExecucaoReal() != null &&
                    ag.getDataHoraExecucaoReal().getYear() == LocalDate.now().getYear() &&
                    ag.getDataHoraExecucaoReal().getMonth() == LocalDate.now().getMonth();

            if (bateHorario && !jaExecutouEsteMes) {
                dispararComando(ag);
                ag.setDataHoraExecucaoReal(LocalDateTime.now());
                agendamentoRepository.save(ag);
            }
        }
    }

    @SuppressWarnings("null")
    private void dispararComando(Agendamento ag) {
        ComandoRequestDTO dto = new ComandoRequestDTO(
                ag.getCompressor().getId(),
                ag.getComando()
        );
        comandoService.salvar(dto);
    }
}
