package br.com.kingsdevs.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutAtendimentoDTO {
	
	private Integer idAtendimento;
	private String data;
	private String hora;
	private String observacoes;
	private Integer idCliente;
	private List<Integer> idsServicos;	
	
}
