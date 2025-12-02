package br.com.compress.comunica_compress.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "comando_agendado")
public class Agendamento {

    public enum Recorrencia {
        UNICO,
        SEMANAL,
        MENSAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compressor_id", nullable = false)
    private Compressor compressor;

    @Column(nullable = false)
    private Boolean comando;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recorrencia recorrencia;

    // hora da execucao caso for UNICO
    private LocalDateTime dataHoraExecucao;

    private Integer diaMes;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    // hora da execucao caso for SEMANAL ou MENSAL
    private LocalTime horaExecucao;

    @Column(nullable = false)
    private Boolean executado = false;

    private LocalDateTime dataHoraExecucaoReal;

    private String descricao;
}
