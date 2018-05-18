package br.com.oficina.modelo;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdemServico extends ModeloRaOs{
    private double precoServico;
    private final Set<String> LISTA_PRIORIDADE = new HashSet<String>(Arrays.asList(new String[] {"Urgente", "Alta", "Mediana", "Baixa", "Prorrogavel"}));
    private String prioridade;
    private SetorOficina setorAtual;
    private RegistroAtendimento registroAtendimento;
    private Tecnico tecnicoEncerramento;
    private List<Tarefa> listaTarefas;
    
    public OrdemServico(int numero, GregorianCalendar dataAbertura,String descricaoAbertura,
            SetorOficina setorAtual, RegistroAtendimento registroAtendimento) throws InsercaoException, EntidadeNulaException{
        
        super(numero, dataAbertura, descricaoAbertura);
        
        this.setPrecoServico(precoServico);
        this.setSetorAtual(setorAtual);
        this.setRegistroAtendimento(registroAtendimento);
        
    }
    
    public void atualizarPrecoTotal() throws InsercaoException{
        double total = 0;
        for (Tarefa tarefa : this.getListaTarefas()){
            total += tarefa.getPreco();
        }
        
        this.setPrecoServico(total);
    }
    
    public void inserirTarefa(Tarefa tarefa) throws InsercaoException {
        boolean jaInserido = false;
        for (Tarefa tarefaPresente : this.getListaTarefas()){
            if(tarefaPresente.getTipoServico().getCodigo() == tarefaPresente.getTipoServico().getCodigo()){
                jaInserido = true;
                break;
            }
        }                
        if (!jaInserido){
            this.getListaTarefas().add(tarefa);
        }else{
            throw new InsercaoException("ERRO: Tarefa já presente na Ordem de Servico");
        }
        
        this.atualizarPrecoTotal();
    }
    
    public void removerTarefa(int codTipoServico){
        for (Tarefa tarefa : this.getListaTarefas()){
            if (tarefa.getTipoServico().getCodigo() == codTipoServico){
                this.getListaTarefas().remove(tarefa);
                break;
            }
        }
    }
    
    public double getPrecoServico() {
        return precoServico;
    }
    
    public void setPrecoServico(double precoServico) throws InsercaoException {
        if (precoServico < 0){
            throw new InsercaoException("Preco de Servico inválido: valor menor que zero.");
        } else {
            this.precoServico = precoServico;
        }
    }
    
    public String getPrioridade() {
        return prioridade;
    }
    
    public void setPrioridade(String prioridade) throws InsercaoException {
        if (! LISTA_PRIORIDADE.contains(prioridade)){
            throw new InsercaoException();
        } else {
            this.prioridade = prioridade;
        }
    }
    
    public SetorOficina getSetorAtual() {
        return setorAtual;
    }

    public void setSetorAtual(SetorOficina setorAtual) throws EntidadeNulaException {
        if (setorAtual == null){
            throw new EntidadeNulaException("Setor atual inválido: referência a objeto nulo");
        } else {
            this.setorAtual = setorAtual;
        }
    }
    
    public RegistroAtendimento getRegistroAtendimento() {
        return registroAtendimento;
    }
    
    public void setRegistroAtendimento(RegistroAtendimento registroAtendimento) throws EntidadeNulaException {
        if (registroAtendimento == null){
            throw new EntidadeNulaException("Registro de Atendimento inválido: referência a objeto nulo");
        } else {
            this.registroAtendimento = registroAtendimento;
        }
    }
    
    public Tecnico getTecnicoEncerramento() {
        return tecnicoEncerramento;
    }
    
    public void setTecnicoEncerramento(Tecnico tecnicoEncerramento) throws EntidadeNulaException {
        if (tecnicoEncerramento == null){
            throw new EntidadeNulaException("Técnico inválido: referência a objeto nulo");
        } else {
            this.tecnicoEncerramento = tecnicoEncerramento;
        }
    }
    
    @Override
    public String toString(){
        Tecnico tecnico = this.getTecnicoEncerramento();
        String nomeTecnico;
        if (tecnico == null){
            nomeTecnico = "";
        } else {
            nomeTecnico = tecnico.getNome();
        }
        String descricao = super.toString() + "\nPREÇO: " + Double.toString(precoServico) + "\nSETOR: " + this.getSetorAtual().getNome() + "\nNUMERO RA: " + this.getRegistroAtendimento().getNumero() + "\nTECNICO DO ENCERRAMENTO: " + nomeTecnico;
        return descricao;
    }

    /**
     * @return the listaTarefas
     */
    public List<Tarefa> getListaTarefas() {
        return listaTarefas;
    }

    /**
     * @param listaTarefas the listaTarefas to set
     */
    public void setListaTarefas(List<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }
}
