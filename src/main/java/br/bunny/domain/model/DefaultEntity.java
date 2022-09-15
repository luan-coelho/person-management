package br.bunny.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class DefaultEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime changeDate;
    private boolean active = true;

    @PrePersist
    protected void prePersist() {
        this.creationDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.changeDate = LocalDateTime.now();
    }
}
