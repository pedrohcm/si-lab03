package ufcg.edu.br.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tarefa {

    @GeneratedValue @Id
    private Integer ID;
    private String nome, descricao, categoria;
    private boolean terminada;
    private String prioridade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    private List<SubTarefa> subTarefas;

    public Tarefa() {
        this.subTarefas = new ArrayList<SubTarefa>();
    }

    public boolean adicionaSubTarefa(SubTarefa subTarefa) {
        subTarefas.add(subTarefa);
        return true;
    }

    public boolean deletaSubTarefaID(int subTarefaID) {
        SubTarefa subTarefa = buscaSubTarefaID(subTarefaID);
        if(subTarefa == null) {
            return false;
        }
        this.subTarefas.remove(subTarefa);
        return true;
    }

    public boolean deletaSubTarefaObj(SubTarefa subTarefa) {
        if(buscaSubTarefaObj(subTarefa) == null) {
            return false;
        }
        subTarefas.remove(subTarefa);
        return true;
    }

    public SubTarefa buscaSubTarefaObj(SubTarefa subTarefa) {
        int subTarefaID = subTarefas.indexOf(subTarefa);
        if(subTarefaID == -1) {
            return null;
        }
        return subTarefas.get(subTarefaID);
    }

    public SubTarefa buscaSubTarefaID(int subTarefaID) {
        for(SubTarefa subTarefa: subTarefas) {
            if(subTarefa.getID().equals(subTarefaID)) {
                return subTarefa;
            }
        }
        return null;
    }

    public SubTarefa atualizaSubtarefa(SubTarefa subtarefa) {
        SubTarefa subtarefaASerAtualizada = buscaSubTarefaID(subtarefa.getID());
        subtarefaASerAtualizada.setNome(subtarefa.getNome());
        subtarefaASerAtualizada.setTerminada(subtarefa.terminou());
        return subtarefaASerAtualizada;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean terminou() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

    public List<SubTarefa> getSubTarefas() {
        return subTarefas;
    }

    public void setSubTarefas(ArrayList<SubTarefa> subTarefas) {
        this.subTarefas = subTarefas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
        Tarefa other = (Tarefa) obj;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        return true;
    }
}
