/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.dao;

import br.com.oficina.controle.InterfaceControle;
import br.com.oficina.modelo.Carro;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;

/**
 *
 * @author saintclair
 */
public class TesteDAO implements InterfaceControle {

    public static void main(String[] args) throws EntidadeNulaException, InsercaoException {
        CarroDAO cd = new CarroDAO();
        Carro c = cd.buscar(usuario, senha, endereco, 7);
        System.out.println(c.getCliente().getId());
    }
}
