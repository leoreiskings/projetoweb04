package br.com.kingsdevs.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetServicoDTO {
	
	private Integer idServico;
	private String nome;
	private Double preco;
	
}
