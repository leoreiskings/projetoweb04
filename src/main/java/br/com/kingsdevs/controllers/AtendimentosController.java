package br.com.kingsdevs.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.kingsdevs.dtos.GetAtendimentoDTO;
import br.com.kingsdevs.dtos.GetClienteDTO;
import br.com.kingsdevs.dtos.GetServicoDTO;
import br.com.kingsdevs.dtos.PostAtendimentoDTO;
import br.com.kingsdevs.dtos.PutAtendimentoDTO;
import br.com.kingsdevs.entities.Atendimento;
import br.com.kingsdevs.entities.Cliente;
import br.com.kingsdevs.entities.Servico;
import br.com.kingsdevs.repositories.IAtendimentoRepository;
import br.com.kingsdevs.repositories.IClienteRepository;
import br.com.kingsdevs.repositories.IServicoRepository;

@Transactional
@Controller
public class AtendimentosController {

	//fazendo a injeção de dependencia para utilizar os metodos do repositorio de atendimentos / clientes / servicos
	@Autowired
	private IAtendimentoRepository atendimentoRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IServicoRepository servicoRepository;
 
	@RequestMapping(value = "/api/atendimentos", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PostAtendimentoDTO dto) {

		try {
			
			Optional<Cliente> optCliente = clienteRepository.findById(dto.getIdCliente());

			if (optCliente.isEmpty())
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado.");

				Cliente cliente = optCliente.get();

				List<Servico> servicos = new ArrayList<Servico>();

			for (Integer idServico : dto.getIdsServicos()) {
				
				Optional<Servico> optServico = servicoRepository.findById(idServico);

				if (optServico.isEmpty())					
					
					return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body("Serviço não encontrado.");

					servicos.add(optServico.get());
				
			}

			Atendimento atendimento = new Atendimento();

			atendimento.setData(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getData()));
			atendimento.setHora(dto.getHora());
			atendimento.setObservacoes(dto.getObservacoes());
			atendimento.setCliente(cliente);
			atendimento.setServicos(servicos);

			atendimentoRepository.save(atendimento);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Atendimento cadastrado com sucesso.");

		} catch (Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao cadastrar atendimento: " + e.getMessage());
			
		}
	}

	@RequestMapping(value = "/api/atendimentos", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody PutAtendimentoDTO dto) {

		try {
			
			Optional<Cliente> optCliente = clienteRepository.findById(dto.getIdCliente());

			if (optCliente.isEmpty())

				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado.");

			Cliente cliente = optCliente.get();

			List<Servico> servicos = new ArrayList<Servico>();

			for (Integer idServico : dto.getIdsServicos()) {

				Optional<Servico> optServico = servicoRepository.findById(idServico);

				if (optServico.isEmpty())

					return ResponseEntity
							.status(HttpStatus.BAD_REQUEST)
							.body("Serviço não encontrado.");
				
					servicos.add(optServico.get());

			}

			Atendimento atendimento = atendimentoRepository.findById(dto.getIdAtendimento()).get();

			atendimento.setData(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getData()));
			atendimento.setHora(dto.getHora());
			atendimento.setObservacoes(dto.getObservacoes());
			atendimento.setCliente(cliente);
			atendimento.setServicos(servicos);
			
			atendimentoRepository.save(atendimento);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Atendimento atualizado com sucesso.");
		}

		catch (Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao cadastrar atendimento: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/api/atendimentos/{idAtendimento}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idAtendimento") Integer idAtendimento) {

		try {

			Atendimento atendimento = atendimentoRepository.findById(idAtendimento).get();

			atendimentoRepository.delete(atendimento);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Atendimento excluído com sucesso.");

		} catch (Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao cadastrar atendimento: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/api/atendimentos", method = RequestMethod.GET)
	public ResponseEntity<List<GetAtendimentoDTO>> getAll() {

		try {
			
			List<Atendimento> atendimentos = atendimentoRepository.findAll();
			
			List<GetAtendimentoDTO> lista	= new ArrayList<GetAtendimentoDTO>();
			
			for(Atendimento atendimento : atendimentos) {
				
				 // CRIANDO UM OBJETO PARA SER POPULADO COM OS DADOS RETORNADOS DO ATENDIMENTO
				GetAtendimentoDTO dto = new GetAtendimentoDTO();
				
				dto.setCliente(new GetClienteDTO()); //CRIANDO ESPACO EM MEMORIA NO OBJ DTO PARA POPULAR OS DADOS DO CLIENTE VINCULADO AO ATENDIMENTO				
				dto.setServicos(new ArrayList<GetServicoDTO>());  //CRIANDO ESPACO EM MEMORIA NO OBJ DTO PARA POPULAR OS DADOS DO SERVICO VINCULADO AO ATENDIMENTO
				
				//DADOS DO ATENDIMENTO
				dto.setIdAtendimento(atendimento.getIdAtendimento());
				dto.setData(atendimento.getData());
				dto.setHora(atendimento.getHora());
				dto.setObservacoes(atendimento.getObservacoes());
				
				//DADOS DO CLIENTE
				dto.getCliente().setIdCliente(atendimento.getCliente().getIdCliente());
				dto.getCliente().setNome(atendimento.getCliente().getNome());
				dto.getCliente().setEmail(atendimento.getCliente().getEmail());
				dto.getCliente().setCpf(atendimento.getCliente().getCpf());
				dto.getCliente().setTelefone(atendimento.getCliente().getTelefone());
				
				if (atendimento.getCliente().getEndereco() != null) {
					
					//DADOS DO ENDERECO DO CLIENTE DAQUELE ATENDIMENTO
					dto.getCliente().setIdEndereco(atendimento.getCliente().getEndereco().getIdEndereco());
					dto.getCliente().setLogradouro(atendimento.getCliente()	.getEndereco().getLogradouro());
					dto.getCliente().setNumero(atendimento.getCliente().getEndereco().getNumero());
					dto.getCliente().setComplemento(atendimento.getCliente().getEndereco().getComplemento());
					dto.getCliente().setBairro(atendimento.getCliente().getEndereco().getBairro());
					dto.getCliente().setCidade(atendimento.getCliente().getEndereco().getCidade());
					dto.getCliente().setEstado(atendimento.getCliente().getEndereco().getEstado());
					dto.getCliente().setCep(atendimento.getCliente().getEndereco().getCep());
				}
					for(Servico servico : atendimento.getServicos()) {
						
						GetServicoDTO servicoDto	= new GetServicoDTO();
						
						servicoDto.setIdServico(servico.getIdServico());
						servicoDto.setNome(servico.getNome());
						servicoDto.setPreco(Double.parseDouble(servico.getPreco()));
						
						dto.getServicos().add(servicoDto);
					}					
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

	@RequestMapping(value = "/api/atendimentos/{idAtendimento}", method = RequestMethod.GET)
	public ResponseEntity<GetAtendimentoDTO> getById(@PathVariable("idAtendimento") Integer idAtendimento) {
		try {

			Atendimento atendimento = atendimentoRepository.findById(idAtendimento).get();

			GetAtendimentoDTO dto = new GetAtendimentoDTO();
			
			dto.setCliente(new GetClienteDTO());
			dto.setServicos(new ArrayList<GetServicoDTO>());
			
			dto.setIdAtendimento(atendimento.getIdAtendimento());
			dto.setData(atendimento.getData());
			dto.setHora(atendimento.getHora());
			dto.setObservacoes(atendimento.getObservacoes());
			dto.getCliente().setIdCliente(atendimento.getCliente().getIdCliente());
			dto.getCliente().setNome(atendimento.getCliente().getNome());
			dto.getCliente().setEmail(atendimento.getCliente().getEmail());
			dto.getCliente().setCpf(atendimento.getCliente().getCpf());			
			dto.getCliente().setTelefone(atendimento.getCliente().getTelefone());
			
			if (atendimento.getCliente().getEndereco() != null) {
					
					//DADOS DO ENDERECO DO CLIENTE DAQUELE ATENDIMENTO
					dto.getCliente().setIdEndereco(atendimento.getCliente().getEndereco().getIdEndereco());
					dto.getCliente().setLogradouro(atendimento.getCliente()	.getEndereco().getLogradouro());
					dto.getCliente().setNumero(atendimento.getCliente().getEndereco().getNumero());
					dto.getCliente().setComplemento(atendimento.getCliente().getEndereco().getComplemento());
					dto.getCliente().setBairro(atendimento.getCliente().getEndereco().getBairro());
					dto.getCliente().setCidade(atendimento.getCliente().getEndereco().getCidade());
					dto.getCliente().setEstado(atendimento.getCliente().getEndereco().getEstado());
					dto.getCliente().setCep(atendimento.getCliente().getEndereco().getCep());
					
				}
			
			for(Servico servico : atendimento.getServicos()) {
				
				GetServicoDTO servicoDto = new GetServicoDTO();
				
				servicoDto.setIdServico(servico.getIdServico());
				servicoDto.setNome(servico.getNome());
				servicoDto.setPreco(Double.parseDouble(servico.getPreco()));
				
				dto.getServicos().add(servicoDto);				
			}
			
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
