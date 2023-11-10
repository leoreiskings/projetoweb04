package br.com.kingsdevs.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutClienteDTO {

	private Integer idCliente;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	
	
}
