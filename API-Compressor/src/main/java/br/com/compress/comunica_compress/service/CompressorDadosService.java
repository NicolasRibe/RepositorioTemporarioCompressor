package br.com.compress.comunica_compress.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.compress.comunica_compress.dto.CompressorDadosRequestDTO;
import br.com.compress.comunica_compress.dto.CompressorDadosResponseDTO;
import br.com.compress.comunica_compress.model.Compressor;
import br.com.compress.comunica_compress.model.CompressorDados;
import br.com.compress.comunica_compress.model.Falha;
import br.com.compress.comunica_compress.repository.AlertaRepository;
import br.com.compress.comunica_compress.repository.CompressorDadosRepository;
import br.com.compress.comunica_compress.repository.CompressorRepository;
import br.com.compress.comunica_compress.repository.FalhaRepository;
import jakarta.transaction.Transactional;

@Service
public class CompressorDadosService {

    @Autowired
    CompressorRepository compressorRepository;

    @Autowired
    CompressorDadosRepository compressorDadosRepository;

    @Autowired
    FalhaRepository falhaRepository;

    @Autowired 
    AlertaRepository alertaRepository;

    // DTO → Entity
    public CompressorDados toEntity(CompressorDadosRequestDTO dto, Compressor compressor, Falha falha) {
        CompressorDados entity = new CompressorDados();
        entity.setLigado(dto.ligado());
        entity.setEstado(dto.estado());
        entity.setTemperaturaArComprimido(dto.temperaturaArComprimido());
        entity.setTemperaturaAmbiente(dto.temperaturaAmbiente());
        entity.setTemperaturaOleo(dto.temperaturaOleo());
        entity.setTemperaturaOrvalho(dto.temperaturaOrvalho());
        entity.setPressaoArComprimido(dto.pressaoArComprimido());
        entity.setHoraCarga(dto.horaCarga());
        entity.setHoraTotal(dto.horaTotal());
        entity.setPressaoCarga(dto.pressaoCarga());
        entity.setPressaoAlivio(dto.pressaoAlivio());
        entity.setCompressor(compressor);
        entity.setFalha(falha);
        return entity;
    }

    // Entity → DTO
    public CompressorDadosResponseDTO toResponse(CompressorDados entity) {
        String falhaId = entity.getFalha() != null ? entity.getFalha().getId() : null;
        String falhaDescricao = entity.getFalha() != null ? entity.getFalha().getDescricao() : null;
        String alertaId = entity.getAlerta() != null ? entity.getAlerta().getId() : null;
        String alertaDescricao = entity.getAlerta() != null ? entity.getAlerta().getDescricao() : null;

        return new CompressorDadosResponseDTO(
                entity.getDataHora(),
                entity.getLigado(),
                entity.getEstado(),
                entity.getTemperaturaArComprimido(),
                entity.getTemperaturaAmbiente(),
                entity.getTemperaturaOleo(),
                entity.getTemperaturaOrvalho(),
                entity.getPressaoArComprimido(),
                entity.getHoraCarga(),
                entity.getHoraTotal(),
                entity.getPressaoCarga(),
                entity.getPressaoAlivio(),
                falhaId,
                falhaDescricao,
                alertaId,
                alertaDescricao);
    }

    @Transactional
    public CompressorDadosResponseDTO salvar(CompressorDadosRequestDTO dto) {
        Compressor compressor = compressorRepository.findById(dto.compressorId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Compressor de id: " + dto.compressorId() + "não existe!"));

        Falha falha = falhaRepository.findById(dto.falhaId())
                .orElseThrow(() -> new IllegalArgumentException("Falha " + dto.falhaId() + "não existe!"));

        CompressorDados compressorDadosEntity = toEntity(dto, compressor, falha);

        if (compressorDadosEntity == null) {
            throw new IllegalStateException("Falha ao converter dados para entidade. Verifique os valores de entrada.");
        }

        Optional<CompressorDados> ultimosDados = compressorDadosRepository
                .findTopByCompressorIdOrderByDataHoraDesc(dto.compressorId());

        boolean dadosSemAlteracao = Objects.equals(compressorDadosEntity.getEstado(), ultimosDados.get().getEstado()) &&
                Objects.equals(compressorDadosEntity.getFalha().getId(), ultimosDados.get().getFalha().getId()) &&
                Double.compare(compressorDadosEntity.getPressaoAlivio(), ultimosDados.get().getPressaoAlivio()) == 0 &&
                Double.compare(compressorDadosEntity.getPressaoArComprimido(),
                        ultimosDados.get().getPressaoArComprimido()) == 0
                &&
                Double.compare(compressorDadosEntity.getPressaoCarga(), ultimosDados.get().getPressaoCarga()) == 0 &&
                Double.compare(compressorDadosEntity.getTemperaturaAmbiente(),
                        ultimosDados.get().getTemperaturaAmbiente()) == 0
                &&
                Double.compare(compressorDadosEntity.getTemperaturaArComprimido(),
                        ultimosDados.get().getTemperaturaArComprimido()) == 0
                &&
                Double.compare(compressorDadosEntity.getTemperaturaOleo(), ultimosDados.get().getTemperaturaOleo()) == 0
                &&
                Double.compare(compressorDadosEntity.getTemperaturaOrvalho(),
                        ultimosDados.get().getTemperaturaOrvalho()) == 0
                &&
                Objects.equals(compressorDadosEntity.getLigado(), ultimosDados.get().getLigado());

        if (dadosSemAlteracao) {
            return null;
        }

        CompressorDados saved = compressorDadosRepository.save(compressorDadosEntity);

        return toResponse(saved);
    }

    @Transactional
    public Optional<CompressorDados> buscarUltimaLeitura(Integer idCompressor) {
        return compressorDadosRepository.findTopByCompressorIdOrderByDataHoraDesc(idCompressor);
    }

    @Transactional
    public List<CompressorDados> buscarDadosDashboard(Integer idCompressor) {
        return compressorDadosRepository.findTop5ByCompressorIdOrderByDataHoraDesc(idCompressor);
    }

    @Transactional
    public Page<CompressorDados> buscarFalhas(Integer idCompressor, Pageable pageable) {
        return compressorDadosRepository.findByCompressorIdAndFalhaIdNot(idCompressor, "0x00", pageable);
    }
}
