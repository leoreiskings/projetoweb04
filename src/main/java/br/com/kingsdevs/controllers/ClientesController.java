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

import br.com.kingsdevs.dtos.GetClienteDTO;
import br.com.kingsdevs.dtos.PostClienteDTO;
import br.com.kingsdevs.dtos.PutClienteDTO;
import br.com.kingsdevs.entities.Cliente;
import br.com.kingsdevs.repositories.IClienteRepository;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class ClientesController {

	// fazendo a injecao de dependencia com o @Autowired
	// isso me permite instanciar um objeto deste repositorio e utilizar seus
	// metodos
	@Autowired
	private IClienteRepository clienteRepository;

	@ApiOperation("Serviço para cadastro de clientes.")
	@RequestMapping(value = "/api/clientes", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PostClienteDTO dto) {

		try {

			Cliente cliente = new Cliente();

			// populando o obj com os dados vindos do DTO de Post do Cliente
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());
			cliente.setTelefone(dto.getTelefone());

			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente cadastrado com Sucesso!");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}

	}

	@ApiOperation("Serviço para atualização de dados de clientes.")
	@RequestMapping(value = "/api/clientes", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody PutClienteDTO dto) {
		try {

			// buscando o cliente no banco de
			// dados através do id..
			Cliente cliente = clienteRepository.findById(dto.getIdCliente()).get();

			// alterando os dados do cliente
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());
			cliente.setTelefone(dto.getTelefone());

			// atualizando no banco de dados
			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente atualizado com sucesso.");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}

	@ApiOperation("Serviço para exclusão de clientes.")
	@RequestMapping(value = "/api/clientes/{idCliente}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idCliente") Integer idCliente) {

		try {
			
			// consultar o cliente no banco de dados através do ID antes de excluir
			Cliente cliente = clienteRepository.findById(idCliente).get();
			
			// excluindo o cliente recuperado do banco de dados
			clienteRepository.delete(cliente);
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Cliente excluído com sucesso.");
			
		} catch (Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
			
		}
	}

	@ApiOperation("Serviço para consulta de clientes.")
	@RequestMapping(value = "/api/clientes", method = RequestMethod.GET)
	public ResponseEntity<List<GetClienteDTO>> getAll() { //visibilidade do metodo -> "PUBLIC"																									  
																									  //retorno do método -> "List<GetClienteDTO>"
																									  //nome do metodo -> "getAll()"

		try {
			// consultando todos os clientes cadastrados na base de dados
			List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();

			List<GetClienteDTO> lista = new ArrayList<GetClienteDTO>(); // criando uma lista vazia de objetos do tipo "GetClienteDTO" 

			for (Cliente cliente : clientes) { //varrendo todos os clientes que ele trouxe do banco apos consulta com o metodo  "clienteRepository.findAll()"

				GetClienteDTO dto = new GetClienteDTO();

				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setEmail(cliente.getEmail());
				dto.setCpf(cliente.getCpf());
				dto.setTelefone(cliente.getTelefone());

					if (cliente.getEndereco() != null) {						
						dto.setIdEndereco(cliente.getEndereco().getIdEndereco());
						dto.setLogradouro(cliente.getEndereco().getLogradouro());
						dto.setNumero(cliente.getEndereco().getNumero());
						dto.setComplemento(cliente.getEndereco().getComplemento());
						dto.setBairro(cliente.getEndereco().getBairro());
						dto.setCidade(cliente.getEndereco().getCidade());
						dto.setEstado(cliente.getEndereco().getEstado());
						dto.setCep(cliente.getEndereco().getCep());						
					}
				
					lista.add(dto);
				
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(lista);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Serviço para consulta de 1 cliente baseado no ID.")
	@RequestMapping(value = "/api/clientes/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<GetClienteDTO> getById(@PathVariable("idCliente") Integer idCliente) {
		
		try {

			// consultando 1 cliente na base
			// de dados de acordo com o ID
			Cliente cliente = clienteRepository.findById(idCliente).get();

			GetClienteDTO dto = new GetClienteDTO();

			//setando campo a campo os dados retornados da consulta no objeto dto
			dto.setIdCliente(cliente.getIdCliente());
			dto.setNome(cliente.getNome());
			dto.setEmail(cliente.getEmail());
			dto.setCpf(cliente.getCpf());
			dto.setTelefone(cliente.getTelefone());
			
			if (cliente.getEndereco() != null) {
			
				dto.setIdEndereco(cliente.getEndereco().getIdEndereco());
				dto.setLogradouro(cliente.getEndereco().getLogradouro());
				dto.setNumero(cliente.getEndereco().getNumero());
				dto.setComplemento(cliente.getEndereco().getComplemento());
				dto.setBairro(cliente.getEndereco().getBairro());
				dto.setCidade(cliente.getEndereco().getCidade());
				dto.setEstado(cliente.getEndereco().getEstado());
				dto.setCep(cliente.getEndereco().getCep());
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
