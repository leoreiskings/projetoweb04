package br.com.kingsdevs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.kingsdevs.dtos.GetServicoDTO;
import br.com.kingsdevs.dtos.PostServicoDTO;
import br.com.kingsdevs.dtos.PutServicoDTO;
import br.com.kingsdevs.entities.Servico;
import br.com.kingsdevs.repositories.IServicoRepository;

@Transactional
@Controller
public class ServicosController {

	@Autowired
	private IServicoRepository servicoRepository;	
	
	@RequestMapping(value = "/api/servicos", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PostServicoDTO dto) {

		try {

			Servico servico = new Servico();

			servico.setNome(dto.getNome());
			servico.setPreco(dto.getPreco());

			servicoRepository.save(servico);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Serviço Cadastrado com Sucesso!");

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value = "/api/servicos", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody PutServicoDTO dto) {

		try {

			//buscando o nome do servico pelo Id para ser alterado 
			Servico servico = servicoRepository.findById(dto.getIdServico()).get();

			//alterando o nome e o preco do servico 
			servico.setNome(dto.getNome());
			servico.setPreco(dto.getPreco());
			
			servicoRepository.save(servico);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Serviço atualizado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}

	}

	@RequestMapping(value = "/api/servicos/{idServico}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idServico") Integer idServico) {

		try {

			Servico servico = servicoRepository.findById(idServico).get();

			servicoRepository.delete(servico);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Serviço excluído com sucesso.");
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value = "/api/servicos", method = RequestMethod.GET)
	public ResponseEntity<List<GetServicoDTO>> getAll() {

		try {
			
			List<Servico> servicos = (List<Servico>) servicoRepository.findAll();
			
			List<GetServicoDTO> lista = new ArrayList<GetServicoDTO>();
			
				for (Servico servico : servicos) {
					
					GetServicoDTO dto = new GetServicoDTO();
					
					dto.setIdServico(servico.getIdServico());
					dto.setNome(servico.getNome());
					dto.setPreco(Double.parseDouble(servico.getPreco()));
					
					lista.add(dto);
				}
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(lista);
				
				} catch (Exception e) {				
					return ResponseEntity
							.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body(null);					
				}
	}

	@RequestMapping(value = "/api/servicos/{idServico}", method = RequestMethod.GET)
	public ResponseEntity<GetServicoDTO> getById(@PathVariable("idServico") Integer idServico) {

		try {

			Servico servico = servicoRepository.findById(idServico).get();

			GetServicoDTO dto = new GetServicoDTO();
			
			dto.setIdServico(servico.getIdServico());
			dto.setNome(servico.getNome());
			dto.setPreco(Double.parseDouble(servico.getPreco()));
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(dto);

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

}
