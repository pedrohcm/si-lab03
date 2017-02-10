package ufcg.edu.br.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufcg.edu.br.model.ListaTarefa;
import ufcg.edu.br.model.SubTarefa;
import ufcg.edu.br.model.Tarefa;
import ufcg.edu.br.repository.ListaTarefaRepository;
import ufcg.edu.br.repository.TarefaRepository;

import java.util.List;

@Component
public class BancoDados {

    private static BancoDados banco;

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    private BancoDados() {

    }

    public static BancoDados getInstance() {
        if (banco == null) {
            banco = new BancoDados();
        }

        return banco;
    }

    public ListaTarefa salvaListaTarefa(ListaTarefa lista) {
        return listaTarefaRepository.save(lista);
    }

    public ListaTarefa buscaListaTarefaID(Integer listaTarefaID) {
        return listaTarefaRepository.findOne(listaTarefaID);
    }

    public Tarefa buscaTarefaID(Integer tarefaID) {
        return tarefaRepository.findOne(tarefaID);
    }

    public List<ListaTarefa> getListasTarefas() {
        return listaTarefaRepository.findAll();
    }

    public Tarefa adicionaTarefa(Integer idLista, Tarefa tarefa) {

        ListaTarefa listaEncontrada = buscaListaTarefaID(idLista);

        if(listaEncontrada != null) {
            listaEncontrada.adicionaTarefa(tarefa);

            ListaTarefa listaSalva = salvaListaTarefa(listaEncontrada);

            Tarefa tarefaSalva = listaSalva.buscaTarefaObj(tarefa);

            return tarefaSalva;
        } else {

            return null;

        }
    }

    public Tarefa salvaTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public SubTarefa adicionaSubTarefa(Integer tarefaID, SubTarefa subtarefa) {
        Tarefa tarefa = buscaTarefaID(tarefaID);
        if(tarefa != null) {
            tarefa.adicionaSubTarefa(subtarefa);
            Tarefa tarefaSalva = salvaTarefa(tarefa);
            return tarefaSalva.buscaSubTarefaObj(subtarefa);
        }
        return null;
    }

    public boolean deletaListaTarefa(Integer listaTarefaID) {
        ListaTarefa listaTarefa = buscaListaTarefaID(listaTarefaID);
        if(listaTarefa != null) {
            listaTarefaRepository.delete(listaTarefaID);
            return true;
        }
        return false;
    }

    public List<Tarefa> getTarefasLista(Integer listaTarefaID) {
        ListaTarefa listaTarefa = buscaListaTarefaID(listaTarefaID);
        if(listaTarefa != null) {
            return listaTarefaRepository.findOne(listaTarefaID).getTarefas();
        }
        return null;
    }

    public boolean deletaTarefa(Integer listaTarefaID, Integer tarefaID) {
        ListaTarefa listaTarefa = buscaListaTarefaID(listaTarefaID);
        if(listaTarefa != null) {
            if(listaTarefa.deletaTarefaID(tarefaID)) {
                salvaListaTarefa(listaTarefa);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean deletaSubTarefa(Integer tarefaID, Integer subTarefaID) {
        Tarefa tarefa = buscaTarefaID(tarefaID);
        if(tarefa != null) {
            if(tarefa.deletaSubTarefaID(subTarefaID)) {
                salvaTarefa(tarefa);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean deletarListas() {
        listaTarefaRepository.deleteAll();
        return true;
    }

    public boolean deletaTarefas(Integer listaTarefaID) {
        ListaTarefa listaTarefa = buscaListaTarefaID(listaTarefaID);
        if(listaTarefa != null) {
            listaTarefa.getTarefas().clear();
            listaTarefaRepository.save(listaTarefa);
            return true;
        }
        return false;
    }

    public ListaTarefa alteraNomeListaTarefa(Integer listaTarefaID, String novoNome) {
        ListaTarefa listaTarefa = buscaListaTarefaID(listaTarefaID);
        if(listaTarefa != null) {
            listaTarefa.setNome(novoNome);
            return salvaListaTarefa(listaTarefa);
        }
        return null;
    }

    public Tarefa alteraNomeTarefa(Integer tarefaID, String novoNome) {
        Tarefa tarefa = buscaTarefaID(tarefaID);
        if(tarefa != null) {
            tarefa.setNome(novoNome);
            return salvaTarefa(tarefa);
        }
        return null;
    }

    public SubTarefa salvaSubTarefa(Integer tarefaID, SubTarefa subtarefa) {
        Tarefa tarefa = buscaTarefaID(tarefaID);
        if(tarefa != null) {
            return tarefa.atualizaSubtarefa(subtarefa);
        }
        return null;
    }









}
