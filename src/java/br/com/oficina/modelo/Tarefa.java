/*
tarefa_numero int not null constraint tarefa_pk primary key,
tarefa_os_numero int not null references ordem_servico(os_numero) on update cascade on delete cascade,
tarefa_tipo_servico_codigo int references tipo_servico(tipo_servico_codigo) on update cascade on delete set null,
tarefa_data_abertura date not null,
tarefa_data_encerramento date,
tarefa_descricao_abertura text not null,
tarefa_descricao_encerramento text,
tarefa_estado varchar(15),
constraint tarefa_estado_chk check(tarefa_estado in ('Aberto', 'Em Andamento', 'Cancelado', 'Concluído')),
tarefa_ativo boolean
 */
package br.com.oficina.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Tarefa extends EventoDescritivelDatavel{
    private int numero;
    private OrdemServico os;
    private TipoServico tipoServico;
    private List<Mecanico> equipe;
    private List<ItemMaterial> material;
    private double preco;

    public Tarefa(int numero, OrdemServico os, TipoServico tipoServico, GregorianCalendar dataAbertura, String descricaoAbertura) throws InsercaoException, EntidadeNulaException {
        super(dataAbertura, descricaoAbertura);
        this.setNumero(numero);
        this.setOs(os);
        this.setTipoServico(tipoServico);
        this.equipe = new ArrayList<Mecanico>();
        this.material = new ArrayList<ItemMaterial>();
    }
    
    private void atualizarPrecoTarefa() throws InsercaoException{
        double precoMaterial = 0;
        for (ItemMaterial item : this.material){
            precoMaterial += item.getItemEstoque().getPreco();
        }
        this.setPreco(precoMaterial + this.getTipoServico().getValor());
        this.getOs().atualizarPrecoTotal();
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the os
     */
    public OrdemServico getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(OrdemServico os) {
        this.os = os;
    }
    
    public TipoServico getTipoServico() {
        return tipoServico;
    }
    
    public void setTipoServico(TipoServico tipoServico) throws InsercaoException {
        this.tipoServico = tipoServico;
        this.atualizarPrecoTarefa();
    }
    
    public void inserirMecanico(Mecanico mecanico) throws InsercaoException {
        boolean jaInserido = false;
        for (Mecanico mecanicoEquipe : this.getEquipe()){
            if(mecanicoEquipe.getId() == mecanico.getId()){
                jaInserido = true;
                break;
            }
        }                
        if (!jaInserido){
            this.getEquipe().add(mecanico);
        }else{
            throw new InsercaoException("ERRO: Mecânico já alocado na tarefa");
        }
    }
    
    public void removerMecanico(int idMecanico){
        for (Mecanico mecanico : this.getEquipe()){
            if (mecanico.getId() == idMecanico){
                this.getEquipe().remove(mecanico);
                break;
            }
        }
    }
    
    public Mecanico getMecanico(int idMecanico){
        Mecanico mecanicoRetorno = null;
        for (Mecanico mecanico : this.getEquipe()){
            if (mecanico.getId() == idMecanico){
                mecanicoRetorno = mecanico;
                break;
            }
        }        
        return mecanicoRetorno;
    }
    
    public List<Mecanico> getEquipe() {
        return equipe;
    }
    
    public void setEquipe(List<Mecanico> equipe) {
        this.equipe = equipe;
    }
    
    public void inserirItemMaterial(ItemMaterial item) throws InsercaoException {
        boolean jaInserido = false;
        for (ItemMaterial itemPresente : this.getMaterial()){
            if(itemPresente.getItemEstoque().getCodigo() == item.getItemEstoque().getCodigo()){
                jaInserido = true;
                break;
            }
        }                
        if (!jaInserido){
            this.getMaterial().add(item);
        }else{
            throw new InsercaoException("ERRO: Item já presente na lista de materiais");
        }
        this.atualizarPrecoTarefa();
    }
    
    public void removerItemMaterial(int codigoEstoque) throws InsercaoException{
        for (ItemMaterial itemPresente : this.getMaterial()){
            if (itemPresente.getItemEstoque().getCodigo() == codigoEstoque){
                this.getMaterial().remove(itemPresente);
                break;
            }
        }
        this.atualizarPrecoTarefa();
    }
    
    public List<ItemMaterial> getMaterial() {
        return material;
    }
    
    public void setMaterial(List<ItemMaterial> material) throws InsercaoException {
        this.material = material;
        
        this.atualizarPrecoTarefa();
    }

    /**
     * @return the preco
     */
    public double getPreco() {
        return preco;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
