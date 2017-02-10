package ufcg.edu.br.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufcg.edu.br.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer>{
}
