package br.com.kingsdevs.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetClienteDTO {

	//DADOS DO CLIENTE
	private Integer idCliente;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;

	//DADOS DO ENDERECO
	private Integer idEndereco;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	
}
