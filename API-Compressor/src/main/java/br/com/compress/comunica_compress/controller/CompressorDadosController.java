package br.com.compress.comunica_compress.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.compress.comunica_compress.dto.CompressorDadosRequestDTO;
import br.com.compress.comunica_compress.dto.CompressorDadosResponseDTO;
import br.com.compress.comunica_compress.dto.FalhasDTO;
import br.com.compress.comunica_compress.model.CompressorDados;
import br.com.compress.comunica_compress.service.CompressorDadosService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/compressor")
public class CompressorDadosController {

        @Autowired
        private CompressorDadosService compressorDadosService;

        @Operation(summary = "Enviar/inserir dados dos sensores do compressor no banco")
        @PostMapping("/dados")
        public ResponseEntity<CompressorDadosResponseDTO> enviarDadosSensores(
                        @RequestBody CompressorDadosRequestDTO request) {
                CompressorDadosResponseDTO response = compressorDadosService.salvar(request);

                if (response == null) {
                        return ResponseEntity.noContent().build();
                }

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "GET/recebe os dados dos sensores do compressor no banco")
        @GetMapping("/dados")
        public ResponseEntity<CompressorDadosResponseDTO> ultimosDadosSensores(@RequestParam Integer idCompressor) {

                Optional<CompressorDados> dadosRecentes = compressorDadosService.buscarUltimaLeitura(idCompressor);

                return dadosRecentes
                                .map(dado -> ResponseEntity.ok(compressorDadosService.toResponse(dado)))
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Lista os ultimos 5 dados do compressor para o dashboard")
        @GetMapping("/dados-dashboard")
        public ResponseEntity<List<CompressorDadosResponseDTO>> cincoUltimosDadosSensores(
                        @RequestParam Integer idCompressor) {

                List<CompressorDados> dadosDashboard = compressorDadosService.buscarDadosDashboard(idCompressor);

                if (dadosDashboard.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }

                List<CompressorDadosResponseDTO> resposta = dadosDashboard.stream()
                                .map(compressorDadosService::toResponse)
                                .toList();

                return ResponseEntity.ok(resposta);
        }

        @Operation(summary = "Lista todas as falhas do compressor")
        @GetMapping("/falhas")
        public ResponseEntity<Page<FalhasDTO>> listarFalhas(
                        @RequestParam Integer idCompressor,
                        Pageable pageable) {

                Page<CompressorDados> page = compressorDadosService.buscarFalhas(idCompressor, pageable);

                if (page.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }

                Page<FalhasDTO> dtoPage = page.map(reg -> new FalhasDTO(
                                reg.getFalha().getId().toString(),
                                reg.getFalha().getDescricao(),
                                reg.getDataHora()));

                return ResponseEntity.ok(dtoPage);
        }

}
