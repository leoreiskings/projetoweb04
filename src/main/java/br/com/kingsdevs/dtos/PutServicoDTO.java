package br.com.kingsdevs.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutServicoDTO {

	private Integer idServico;
	private String nome;
	private String preco;
	
}
