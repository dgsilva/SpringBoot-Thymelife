package br.com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.entity.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<Telefone, Long>  {
    @Query("select t from Telefone t where t.pessoa.id = ?1") 
	public List<Telefone>getTelefones(Long pessoaid);
}
