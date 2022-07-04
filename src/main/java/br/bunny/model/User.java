package br.bunny.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User extends DefaultEntity {

    private String name;
    private String email;
}
