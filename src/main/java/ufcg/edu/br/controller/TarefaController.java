package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufcg.edu.br.database.BancoDados;
import ufcg.edu.br.model.SubTarefa;
import ufcg.edu.br.model.Tarefa;

@RestController
public class TarefaController {
    @Autowired
    private BancoDados bancoDados;

    public TarefaController() {

    }

    @RequestMapping(method=RequestMethod.POST, value="/listas/{listaID}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefa> adicionaTarefa(@PathVariable Integer listaID, @RequestBody Tarefa tarefa) {
        Tarefa tarefaAdicionada = bancoDados.adicionaTarefa(listaID, tarefa);
        if (tarefaAdicionada != null) {
            return new ResponseEntity<>(tarefaAdicionada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/listas/tarefa", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefa> salvaTarefa(@RequestBody Tarefa tarefa) {
        Tarefa tarefaSalva = bancoDados.salvaTarefa(tarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/listas/{listaID}/{tarefaID}")
    public ResponseEntity<Tarefa> deletaTarefa(@PathVariable Integer listaID, @PathVariable Integer tarefaID) {
        if (bancoDados.deletaTarefa(listaID, tarefaID)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.POST, value="/listas/tarefa/{tarefaID}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubTarefa> adicionaSubTarefa(@PathVariable Integer tarefaID, @RequestBody SubTarefa subTarefa) {
        SubTarefa subTarefaSalva = bancoDados.adicionaSubTarefa(tarefaID, subTarefa);
        if (subTarefaSalva != null) {
            return new ResponseEntity<>(subTarefaSalva, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/listas/{tarefaID}/subTarefa", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubTarefa> salvaSubTarefa(@PathVariable Integer tarefaID, @RequestBody SubTarefa subTarefa) {
        SubTarefa subTarefaAlterada = bancoDados.salvaSubTarefa(tarefaID, subTarefa);
        if (subTarefaAlterada != null)
            return new ResponseEntity<>(subTarefaAlterada, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/listas/tarefa/{tarefaID}/{subTarefaID}")
    public ResponseEntity<SubTarefa> deletarSubTarefa(@PathVariable Integer tarefaID, @PathVariable Integer subTarefaID) {
        if (bancoDados.deletaSubTarefa(tarefaID, subTarefaID)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
