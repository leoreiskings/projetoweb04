package br.com.kingsdevs.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostEnderecoDTO {
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private Integer idCliente;
	
}