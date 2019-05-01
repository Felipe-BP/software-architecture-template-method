package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class PaisDTO {
    private int id;
    private String nome;
    private String sigla;
    private int codigoTelefone;
    
}