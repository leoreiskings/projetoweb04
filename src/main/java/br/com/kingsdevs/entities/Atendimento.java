package br.com.kingsdevs.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "atendimento") //definindo o nome da tabela a ser criada pelo Hibernate
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Atendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idatendimento")
	private Integer idAtendimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	private Date data;
	
	@Column(name = "hora", length = 10, nullable = false)
	private String hora;
	
	@Column(name = "observacoes", length = 250, nullable = false)	
	private String observacoes;
	
	@ManyToOne // Muitos Atendimentos para 1 Cliente
	@JoinColumn(name = "idcliente")
	// Chave estrangeira (Foreign Key)
	private Cliente cliente;
	
	@ManyToMany // Muitos atendimentos para muitos serviços
	@JoinTable( // mapeando a tabela associativa
		name = "atendimento_servico", 	// nome da tabela associativa que será criada
		joinColumns = @JoinColumn(name = "idatendimento"), 	// Chave estrangeira
		inverseJoinColumns = @JoinColumn(name = "idservico")	// Chave estrangeira
	)
	
	private List<Servico> servicos;
	
	
}
