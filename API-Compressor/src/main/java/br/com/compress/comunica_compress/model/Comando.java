package br.com.compress.comunica_compress.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "comandos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comando {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compressor_id", nullable = false)
    private Compressor compressor;

    @Column(name = "comando")
    private Boolean comando;

    @Column(name = "data_hora")
    private LocalDateTime dataHora = LocalDateTime.now().minusHours(3);

    public Comando(Compressor compressor, Boolean comando) {
        this.compressor = compressor;
        this.comando = comando;
    }
}
