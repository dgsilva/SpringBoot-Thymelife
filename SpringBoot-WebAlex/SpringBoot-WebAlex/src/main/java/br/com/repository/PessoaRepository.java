package br.com.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.entity.Pessoa;

@Repository                               //Qualquer coisa mudar para CrudRepository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {
    @Query("select p from Pessoa p where p.nome like %?1%")
	List<Pessoa> findPessoaByName(String nome);
}
