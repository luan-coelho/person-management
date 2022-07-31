package br.bunny.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class DefaultEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Version
    private Integer version;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAlteracao;
    private boolean ativo;

    @PrePersist
    private void gerarDataCadastro() {
        dataCadastro = LocalDateTime.now();
    }

    @PreUpdate
    private void gerarDataAlteracao() {
        dataAlteracao = LocalDateTime.now();
    }
}
