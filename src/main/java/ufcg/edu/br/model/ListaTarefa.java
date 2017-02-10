package ufcg.edu.br.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ListaTarefa {

    @Id @GeneratedValue
    private Integer ID;
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas;

    //adicionar o array de tarefas

    public ListaTarefa() {
        this.tarefas = new ArrayList<Tarefa>();
    }

    public boolean adicionaTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        return true;
    }

    public boolean deletaTarefaID(int tarefaID) {
        Tarefa tarefa = buscaTarefaID(tarefaID);
        if(tarefa == null) {
            return false;
        }
        this.tarefas.remove(tarefa);
        return true;
    }

    public boolean deletaTarefaObj(Tarefa tarefa) {
        if(buscaTarefaObj(tarefa) == null) {
            return false;
        }
        tarefas.remove(tarefa);
        return true;
    }

    public Tarefa buscaTarefaObj(Tarefa tarefa) {
        int tarefaID = tarefas.indexOf(tarefa);
        if(tarefaID == -1) {
            return null;
        }
        return tarefas.get(tarefaID);
    }

    public Tarefa buscaTarefaID(int tarefaID) {
        for(Tarefa tarefa: tarefas) {
            if(tarefa.getID().equals(tarefaID)) {
                return tarefa;
            }
        }
        return null;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ID == null) ? 0 : ID.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((tarefas == null) ? 0 : tarefas.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListaTarefa other = (ListaTarefa) obj;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (tarefas == null) {
            if (other.tarefas != null)
                return false;
        } else if (!tarefas.equals(other.tarefas))
            return false;
        return true;
    }



}
