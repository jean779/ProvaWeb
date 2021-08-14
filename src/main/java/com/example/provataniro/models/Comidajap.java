package com.example.provataniro.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comidajap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 50, message = "Insira um valor de 2 á 50")
    private String nome;
    @Size(min = 1, max = 50, message = "Insira um valor de 1 á 50")
    private String tipo;
    private double preco;
    private String descricao;
    private int quantidade;
    private String imgUri;
    private Date deleted = null;
}
