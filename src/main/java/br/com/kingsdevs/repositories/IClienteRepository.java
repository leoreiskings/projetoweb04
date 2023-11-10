package br.com.kingsdevs.repositories;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.kingsdevs.entities.Cliente;

//CrudRepository<Cliente, Integer> 
//CrudRepository<nome -da-entidade, tipo-da-primary-key>
public interface IClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("select c from Cliente c left join c.endereco e")
	List<Cliente> findAll();

	@Query("select c from Cliente c left join c.endereco e 	where c.idCliente = :param")
	Optional<Cliente> findById(@Param("param") Integer idCliente); 

}
