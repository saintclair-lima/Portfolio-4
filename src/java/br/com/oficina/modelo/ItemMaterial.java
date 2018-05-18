/*
item_material_numero int not null,
item_material_tarefa_numero int not null references tarefa(tarefa_numero) on update cascade on delete cascade,
item_material_quantidade numeric not null,
item_material_cobrado boolean,
constraint item_material_pk primary key(item_material_numero, item_material_tarefa_numero),
item_material_ativo boolean
 */
package br.com.oficina.modelo;

public class ItemMaterial {
    private int numero;
    private ItemEstoque itemEstoque;
    private double quantidade;
    private boolean cobrado;
    private boolean ativo;

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the itemEstoque
     */
    public ItemEstoque getItemEstoque() {
        return itemEstoque;
    }

    /**
     * @param itemEstoque the itemEstoque to set
     */
    public void setItemEstoque(ItemEstoque itemEstoque) {
        this.itemEstoque = itemEstoque;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the cobrado
     */
    public boolean isCobrado() {
        return cobrado;
    }

    /**
     * @param cobrado the cobrado to set
     */
    public void setCobrado(boolean cobrado) {
        this.cobrado = cobrado;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
