package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufcg.edu.br.database.BancoDados;
import ufcg.edu.br.model.ListaTarefa;
import ufcg.edu.br.model.Tarefa;

import java.util.List;

@RestController
public class ListaTarefaController {

    @Autowired
    private BancoDados bancoDados;

    public ListaTarefaController() {
    }

    @RequestMapping(method=RequestMethod.GET, value="/listas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListaTarefa>> buscarTodasAsListas() {
        List<ListaTarefa> listas = bancoDados.getListasTarefas();
        return new ResponseEntity<>(listas, HttpStatus.OK);
    }

    /**
     *
     * @return

    @RequestMapping(method=RequestMethod.GET, value="/listas/prioridades", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefa.Prioridade[]> buscarListasPrioridades() {
        Tarefa.Prioridade[] prioridades = Tarefa.Prioridade.values();
        return new ResponseEntity<>(prioridades, HttpStatus.OK);
    }
     */

    @RequestMapping(method=RequestMethod.POST, value="/listas", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListaTarefa> adicionarListaTarefa(@RequestBody ListaTarefa listaDeTarefa) {
        ListaTarefa listaSalva = bancoDados.salvaListaTarefa(listaDeTarefa);
        return new ResponseEntity<>(listaSalva, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/listas/{listaID}/{novoNome}")
    public ResponseEntity<ListaTarefa> alterarNomeListaTarefa(@PathVariable Integer listaID, @PathVariable String novoNome) {
        ListaTarefa listaComNovoNome = bancoDados.alteraNomeListaTarefa(listaID, novoNome);
        if (listaComNovoNome != null) {
            return new ResponseEntity<>(listaComNovoNome, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.GET, value="/listas/{listaID}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tarefa>> getTarefasLista(@PathVariable Integer listaID) {
        List<Tarefa> listas = bancoDados.getTarefasLista(listaID);
        return new ResponseEntity<>(listas, HttpStatus.OK);
    }



    @RequestMapping(method=RequestMethod.DELETE, value="/listas/tarefas/{listaID}")
    public ResponseEntity<ListaTarefa> deletarTodasAsTarefas(@PathVariable Integer listaID) {
        if (bancoDados.deletaTarefas(listaID)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @RequestMapping(method=RequestMethod.DELETE, value="/listas/{listaID}")
    public ResponseEntity<ListaTarefa> deletarListaTarefa(@PathVariable Integer listaID) {
        if (bancoDados.deletaListaTarefa(listaID)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/listas")
    public ResponseEntity<ListaTarefa> deletarTodasAsListas() {
        bancoDados.deletarListas();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}