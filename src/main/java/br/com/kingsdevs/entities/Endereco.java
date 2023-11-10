package br.com.kingsdevs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "endereco")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idendereco")
	private Integer idEndereco;
	
	@Column(name = "logradouro", length = 150, nullable = false)
	private String logradouro;
	
	@Column(name = "numero", length = 10, nullable = false)
	private String numero;
	
	@Column(name = "complemento", length = 100, nullable = false)
	private String complemento;
	
	@Column(name = "bairro", length = 100, nullable = false)
	private String bairro;
	
	@Column(name = "cidade", length = 100, nullable = false)
	private String cidade;
	
	@Column(name = "estado", length = 25, nullable = false)
	private String estado;
	
	@Column(name = "cep", length = 10, nullable = false)
	private String cep;
	
	//A chave estrangeira (foreign key) idcliente da tabela Cliente referencia a chave primária idcliente da tabela Cliente, 
	//estabelecendo assim um relacionamento OneToOne entre as duas tabelas.
	@OneToOne
	@JoinColumn(name = "idcliente", unique = true)
	private Cliente cliente;
	//um ENDERECO PERTENCE A UM CLIENTE
	
}
