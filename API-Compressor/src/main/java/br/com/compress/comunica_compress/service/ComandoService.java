package br.com.compress.comunica_compress.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compress.comunica_compress.dto.ComandoRequestDTO;
import br.com.compress.comunica_compress.model.Comando;
import br.com.compress.comunica_compress.model.Compressor;
import br.com.compress.comunica_compress.repository.ComandoRepository;
import br.com.compress.comunica_compress.repository.CompressorRepository;

@Service
public class ComandoService {

    @Autowired
    private CompressorRepository compressorRepository;

    @Autowired
    private ComandoRepository comandoRepository;

    public Comando toEntity(ComandoRequestDTO comandoRequestDTO) {
        Compressor compressor = compressorRepository.findById(comandoRequestDTO.compressorId())
                .orElseThrow(() -> new NoSuchElementException("Compressor não encontrado"));

        Comando comando = new Comando();
        comando.setCompressor(compressor);
        comando.setDataHora(LocalDateTime.now().minusHours(3));
        comando.setComando(comandoRequestDTO.comando());

        return comando;
    }

    @SuppressWarnings("null")
    public Comando salvar(ComandoRequestDTO comandoRequestDTO) {
        Comando comando = toEntity(comandoRequestDTO);
        return comandoRepository.save(comando);
    }
}
