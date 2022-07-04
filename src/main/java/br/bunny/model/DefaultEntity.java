package br.bunny.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@MappedSuperclass
public class DefaultEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
