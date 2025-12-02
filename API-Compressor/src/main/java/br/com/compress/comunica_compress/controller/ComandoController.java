package br.com.compress.comunica_compress.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.compress.comunica_compress.dto.ComandoRequestDTO;
import br.com.compress.comunica_compress.dto.ComandoResponseDTO;
import br.com.compress.comunica_compress.model.Comando;
import br.com.compress.comunica_compress.repository.ComandoRepository;
import br.com.compress.comunica_compress.service.ComandoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/ordemRemota")
public class ComandoController {

        @Autowired
        private ComandoService comandoService;

        @Autowired
        private ComandoRepository comandoRepository;

        @Autowired
        private EntityManager entityManager;

        @SuppressWarnings("null")
        @Operation(summary = "Enviar/inserir comando de liga/desliga do compressor no banco")
        @PostMapping("/comando")
        @Transactional
        public ResponseEntity<ComandoResponseDTO> enviarComando(@RequestBody ComandoRequestDTO comandoDTO) {
                Comando comando = comandoService.toEntity(comandoDTO);
                comandoRepository.save(comando);
                entityManager.refresh(comando.getCompressor());
                ComandoResponseDTO responseDTO = new ComandoResponseDTO(
                                comando.getCompressor().getId(),
                                comando.getComando(),
                                comando.getDataHora());

                return ResponseEntity.ok(responseDTO);
        }

        @Operation(summary = "Busca o último comando recente de um compressor", description = "Retorna o último comando do compressor nos ultimos 60 minutos. "
                        + "Se não houver comandos nesse período, retorna 204.")
        @GetMapping("/comando")
        public ResponseEntity<ComandoResponseDTO> receberComando(@RequestParam Integer compressorId) {
                Optional<Comando> latestComando = comandoRepository
                                .findTopByCompressorIdOrderByDataHoraDesc(compressorId);

                if (latestComando.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }

                LocalDateTime horarioAtual = LocalDateTime.now().minusHours(3);
                LocalDateTime horarioComando = latestComando.get().getDataHora();

                Duration diff = Duration.between(horarioComando, horarioAtual);

                if (Math.abs(diff.toMinutes()) > 5) {
                        return ResponseEntity.noContent().build();
                }

                ComandoResponseDTO responseDTO = new ComandoResponseDTO(
                                latestComando.get().getCompressor().getId(),
                                latestComando.get().getComando(),
                                latestComando.get().getDataHora());

                return ResponseEntity.ok(responseDTO);
        }
}
