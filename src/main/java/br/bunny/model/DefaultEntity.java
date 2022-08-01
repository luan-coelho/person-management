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
    private LocalDateTime creationDate;
    private LocalDateTime changeDate;
    private boolean active;

    @PrePersist
    private void generateDataRegistration() {
        this.creationDate = LocalDateTime.now();
    }

    @PreUpdate
    private void generateDataChange() {
        this.changeDate = LocalDateTime.now();
    }
}
