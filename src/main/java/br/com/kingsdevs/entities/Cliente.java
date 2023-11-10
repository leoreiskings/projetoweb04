package br.com.kingsdevs.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cliente")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcliente")
	private Integer idCliente;
	
	@Column(name = "nome", length = 150, nullable = false)
	private String nome;
	
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "cpf", length = 14, nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "telefone", length = 14, nullable = false, unique = true)
	private String telefone;

	// chave estrangeira (foreign key)
	//um cliente TEM UM endereco
	@OneToOne	(mappedBy ="cliente")	
	private Endereco endereco;

	//1 cliente TEM MUITOS Atendimentos	
	//1 cliente para muitos Atendimentos 
	@OneToMany(mappedBy = "cliente")
	private List<Atendimento> atendimentos;
	
	
	
	
	
}
