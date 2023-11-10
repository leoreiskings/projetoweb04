package br.com.kingsdevs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.kingsdevs.entities.Endereco;

public interface IEnderecoRepository extends CrudRepository<Endereco, Integer> {

	@Query("select e from Endereco e join e.cliente c")
	List<Endereco> findAll();

	@Query("select e from Endereco e join e.cliente c 	where e.idEndereco = :param")
	Optional<Endereco> findById(@Param("param") Integer idEndereco);

}
