package br.com.compress.comunica_compress.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.CompressorDados;

public interface CompressorDadosRepository extends JpaRepository<CompressorDados, Integer> {

    Optional<CompressorDados> findTopByCompressorIdOrderByDataHoraDesc(Integer idCompressor);

    List<CompressorDados> findTop5ByCompressorIdOrderByDataHoraDesc(Integer idCompressor);

    Page<CompressorDados> findByCompressorIdAndFalhaIdNot(
            Integer idCompressor,
            String idFalha,
            Pageable pageable);

    Page<CompressorDados> findByCompressorIdAndAlertaIdNot(
            Integer idCompressor,
            String idAlerta,
            Pageable pageable);
}