package ufcg.edu.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufcg.edu.br.model.ListaTarefa;

@Repository
public interface ListaTarefaRepository extends JpaRepository<ListaTarefa, Integer> {
}
