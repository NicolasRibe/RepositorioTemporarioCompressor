package br.com.compress.comunica_compress.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.compress.comunica_compress.dto.AgendamentoRequestDTO;
import br.com.compress.comunica_compress.dto.AgendamentoResponseDTO;
import br.com.compress.comunica_compress.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordemRemota/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar agendamento de comando", description = "Cria um agendamento UNICO, SEMANAL ou MENSAL para ligar/desligar o compressor.")
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.criar(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        AgendamentoResponseDTO response = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}
