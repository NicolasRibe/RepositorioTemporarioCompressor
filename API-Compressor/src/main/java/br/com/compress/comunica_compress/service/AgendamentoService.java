package br.com.compress.comunica_compress.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compress.comunica_compress.dto.AgendamentoRequestDTO;
import br.com.compress.comunica_compress.dto.AgendamentoResponseDTO;
import br.com.compress.comunica_compress.model.Agendamento;
import br.com.compress.comunica_compress.model.Compressor;
import br.com.compress.comunica_compress.repository.AgendamentoRepository;
import br.com.compress.comunica_compress.repository.CompressorRepository;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final CompressorRepository compressorRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              CompressorRepository compressorRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.compressorRepository = compressorRepository;
    }

    @SuppressWarnings("null")
    @Transactional
    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        Compressor compressor = compressorRepository.findById(dto.compressorId())
                .orElseThrow(() -> new IllegalArgumentException("Compressor não encontrado"));

        Agendamento ag = new Agendamento();
        ag.setCompressor(compressor);
        ag.setComando(dto.comando());
        ag.setRecorrencia(dto.recorrencia());
        ag.setDescricao(dto.descricao());
        ag.setExecutado(false);
        ag.setDataHoraExecucaoReal(null);

        // valida e preenche conforme recorrência
        switch (dto.recorrencia()) {
            case UNICO -> configurarUnico(dto, ag);
            case SEMANAL -> configurarSemanal(dto, ag);
            case MENSAL -> configurarMensal(dto, ag);
        }

        agendamentoRepository.save(ag);

        return toResponse(ag);
    }

    private void configurarUnico(AgendamentoRequestDTO dto, Agendamento ag) {
        if (dto.dataHoraExecucao() == null) {
            throw new IllegalArgumentException("dataHoraExecucao é obrigatória para recorrencia UNICO");
        }

        LocalDateTime agora = LocalDateTime.now().minusSeconds(5);
        if (dto.dataHoraExecucao().isBefore(agora)) {
            throw new IllegalArgumentException("Data/hora de execução deve ser no presente ou futuro.");
        }

        ag.setDataHoraExecucao(dto.dataHoraExecucao());
        ag.setDiaMes(null);
        ag.setDiaSemana(null);
        ag.setHoraExecucao(null);
    }

    private void configurarSemanal(AgendamentoRequestDTO dto, Agendamento ag) {
        if (dto.diaSemana() == null || dto.horaExecucao() == null) {
            throw new IllegalArgumentException("diaSemana e horaExecucao são obrigatórios para recorrencia SEMANAL");
        }

        ag.setDiaSemana(dto.diaSemana());
        ag.setHoraExecucao(dto.horaExecucao());
        ag.setDiaMes(null);
        ag.setDataHoraExecucao(null);
    }

    private void configurarMensal(AgendamentoRequestDTO dto, Agendamento ag) {
        if (dto.diaMes() == null || dto.horaExecucao() == null) {
            throw new IllegalArgumentException("diaMes e horaExecucao são obrigatórios para recorrencia MENSAL");
        }

        if (dto.diaMes() < 1 || dto.diaMes() > 31) {
            throw new IllegalArgumentException("diaMes deve estar entre 1 e 31");
        }

        ag.setDiaMes(dto.diaMes());
        ag.setHoraExecucao(dto.horaExecucao());
        ag.setDiaSemana(null);
        ag.setDataHoraExecucao(null);
    }

    @SuppressWarnings("null")
    public AgendamentoResponseDTO buscarPorId(Integer id) {
        Agendamento ag = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        return toResponse(ag);
    }

    private AgendamentoResponseDTO toResponse(Agendamento ag) {
        return new AgendamentoResponseDTO(
                ag.getId(),
                ag.getCompressor().getId(),
                ag.getComando(),
                ag.getRecorrencia(),
                ag.getDataHoraExecucao(),
                ag.getDiaMes(),
                ag.getDiaSemana(),
                ag.getHoraExecucao(),
                ag.getExecutado(),
                ag.getDataHoraExecucaoReal(),
                ag.getDescricao()
        );
    }
}
