package br.com.oficina.modelo;

import java.util.GregorianCalendar;

public class RegistroAtendimento extends ModeloRaOs{
    private Atendente atendente;
    private Cliente cliente;
    private Carro carro;
    
    public RegistroAtendimento (int numero, GregorianCalendar dataAbertura,
            String descricaoAbertura, Atendente atendente, Cliente cliente,
            Carro carro, boolean ativo) throws InsercaoException, EntidadeNulaException{  
            
        super(numero, dataAbertura, descricaoAbertura);
        this.setAtendente(atendente);
        this.setCliente(cliente);
        this.setCarro(carro);        
    }
    
    public RegistroAtendimento (int numero, GregorianCalendar dataAbertura,
            String descricaoAbertura, String estado, Atendente atendente, Cliente cliente,
            Carro carro) throws InsercaoException, EntidadeNulaException{  
            
        super(numero, dataAbertura, descricaoAbertura, estado);
        this.setAtendente(atendente);
        this.setCliente(cliente);
        this.setCarro(carro);
        this.setEstado(estado);
    }
    
    public RegistroAtendimento (int numero, GregorianCalendar dataAbertura,
            String descricaoAbertura, GregorianCalendar dataEncerramento, 
            String descricaoEncerramento, String estado, Atendente atendente, Cliente cliente,
            Carro carro, boolean ativo) throws InsercaoException, EntidadeNulaException{  
            
        super(numero, dataAbertura, descricaoAbertura);
        this.setDataEncerramento(dataEncerramento);
        this.setDescricaoEncerramento(descricaoEncerramento);
        this.setEstado(estado);
        this.setAtendente(atendente);
        this.setCliente(cliente);
        this.setCarro(carro);
        this.setAtivo(ativo);
        
    }
    
    public Atendente getAtendente() {
        return atendente;
    }
    
    public void setAtendente(Atendente atendente) throws EntidadeNulaException {
        if (atendente == null){
            throw new EntidadeNulaException("Atendente inválido: objeto nulo passado como referência");
        } else {
            this.atendente = atendente;
        }
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) throws EntidadeNulaException {
        if (cliente == null){
            throw new EntidadeNulaException("Cliente inválido: objeto nulo passado como referência");
        } else {
            this.cliente = cliente;
        }
    }
    
    public Carro getCarro() {
        return carro;
    }
    
    public void setCarro(Carro carro) throws EntidadeNulaException {
        if (carro == null){
            throw new EntidadeNulaException("Carro inválido: objeto nulo passado como referência");
        } else {
            this.carro = carro;
        }
    }
    
    @Override
    public String toString(){
        String descricao = super.toString() + "\nATENDENTE: " + this.getAtendente().getNome() + "\nCLIENTE: " + this.getCliente().getNome() + "\nPLACA CARRO: " + this.getCarro().getPlaca() + "\n===========================\n";
        return descricao;
    }
}
