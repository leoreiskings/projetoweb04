package br.com.kingsdevs.dtos;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAtendimentoDTO {

	private Integer idAtendimento;
	private Date data;
	private String hora;
	private String observacoes;
	private GetClienteDTO cliente;
	private List<GetServicoDTO> servicos;
	
}
