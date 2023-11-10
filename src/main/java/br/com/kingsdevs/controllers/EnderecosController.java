package br.com.kingsdevs.controllers;

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

import br.com.kingsdevs.dtos.GetEnderecoDTO;
import br.com.kingsdevs.dtos.PostEnderecoDTO;
import br.com.kingsdevs.dtos.PutEnderecoDTO;
import br.com.kingsdevs.entities.Cliente;
import br.com.kingsdevs.entities.Endereco;
import br.com.kingsdevs.repositories.IClienteRepository;
import br.com.kingsdevs.repositories.IEnderecoRepository;

@Transactional
@Controller
public class EnderecosController {

	@Autowired
	private IEnderecoRepository enderecoRepository;

	@Autowired
	private IClienteRepository clienteRepository;

	@RequestMapping(value = "/api/enderecos", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PostEnderecoDTO dto) {

		try {

			// buscar o cliente no banco de dados através do id
			//O objeto "Optional" retorna ou nao 1 cliente. por isso é ideal na consulta que retorna um registro somente
			Optional<Cliente> optional = clienteRepository.findById(dto.getIdCliente());

			// verificar se o cliente não foi encontrado
			if (optional.isEmpty())
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado.");

			Cliente cliente = optional.get();

			Endereco endereco = new Endereco();

			endereco.setLogradouro(dto.getLogradouro());
			endereco.setNumero(dto.getNumero());
			endereco.setComplemento(dto.getComplemento());
			endereco.setBairro(dto.getBairro());
			endereco.setCidade(dto.getCidade());
			endereco.setEstado(dto.getEstado());
			endereco.setCep(dto.getCep());

			endereco.setCliente(cliente);
			

			// cadastrando no banco de dados
			enderecoRepository.save(endereco);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Endereço cadastrado com sucesso.");

		}

		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

	@RequestMapping(value = "/api/enderecos", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody PutEnderecoDTO dto) {

		try {

			// buscar o cliente no banco de dados através do id
			Optional<Cliente> optional = clienteRepository.findById(dto.getIdCliente());

			// verificar se o cliente não foi encontrado
			if (optional.isEmpty())

				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado.");

			Cliente cliente = optional.get(); //armazenando em cliente os dados do cliente retornado pela pesquisa do findById()

			Endereco endereco = enderecoRepository.findById(dto.getIdEndereco()).get();

			//populando os dados do endereco do cliente a ser registrado 
			endereco.setLogradouro(dto.getLogradouro());
			endereco.setNumero(dto.getNumero());
			endereco.setComplemento(dto.getComplemento());
			endereco.setBairro(dto.getBairro());
			endereco.setCidade(dto.getCidade());
			endereco.setEstado(dto.getEstado());
			endereco.setCep(dto.getCep());
			endereco.setCliente(cliente); // gravando os dados do cliente junto com os dados do obj endereco

			// gravando os dados no banco de dados (dados do cliente e o endereco do cliente)
			enderecoRepository.save(endereco);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Endereço atualizado com sucesso.");

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);

		}

	}

	@RequestMapping(value = "/api/enderecos/{idEndereco}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idEndereco") Integer idEndereco) {

		try {

			Endereco endereco = enderecoRepository.findById(idEndereco).get();

			enderecoRepository.delete(endereco);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Endereço excluído com sucesso.");

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

	@RequestMapping(value = "/api/enderecos", method = RequestMethod.GET)
	public ResponseEntity<List<GetEnderecoDTO>> getAll() {

		try {

			List<Endereco> enderecos = (List<Endereco>) enderecoRepository.findAll();
			
			List<GetEnderecoDTO> lista = new ArrayList<GetEnderecoDTO>();
			
			for (Endereco endereco : enderecos) {
				
				GetEnderecoDTO dto = new GetEnderecoDTO();
				
				dto.setIdEndereco(endereco.getIdEndereco());
				dto.setLogradouro(endereco.getLogradouro());
				dto.setNumero(endereco.getNumero());
				dto.setComplemento(endereco.getComplemento());
				dto.setBairro(endereco.getBairro());
				dto.setCidade(endereco.getCidade());
				dto.setEstado(endereco.getEstado());
				dto.setCep(endereco.getCep());
				dto.setIdCliente(endereco.getCliente().getIdCliente());
				dto.setNome(endereco.getCliente().getNome());
				dto.setEmail(endereco.getCliente().getEmail());
				dto.setCpf(endereco.getCliente().getCpf());
				dto.setTelefone(endereco.getCliente().getTelefone());
				
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

	@RequestMapping(value = "/api/enderecos/{idEndereco}", method = RequestMethod.GET)
	public ResponseEntity<GetEnderecoDTO> getById(@PathVariable("idEndereco") Integer idEndereco) {
		try {

			Endereco endereco = enderecoRepository.findById(idEndereco).get();

			GetEnderecoDTO dto = new GetEnderecoDTO();
			
			//DADOS DO ENDERECO DO CLIENTE PESQUISADO NO BANCO
			dto.setIdEndereco(endereco.getIdEndereco());
			dto.setLogradouro(endereco.getLogradouro());
			dto.setNumero(endereco.getNumero());
			dto.setComplemento(endereco.getComplemento());
			dto.setBairro(endereco.getBairro());
			dto.setCidade(endereco.getCidade());
			dto.setEstado(endereco.getEstado());
			dto.setCep(endereco.getCep());
			
			//DADOS DO CLIENTE VINCULADO AO ENDERECO 
			dto.setIdCliente(endereco.getCliente().getIdCliente());
			dto.setNome(endereco.getCliente().getNome());
			dto.setEmail(endereco.getCliente().getEmail());
			dto.setCpf(endereco.getCliente().getCpf());
			dto.setTelefone(endereco.getCliente().getTelefone());
			
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
