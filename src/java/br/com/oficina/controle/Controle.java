/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.controle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saintclair
 */
public abstract class Controle extends HttpServlet{
    
    public static final int LISTAR = 6;
    public static final int DETALHAR = 7;
    public static final int ATUALIZAR = 8;
    public static final int INSERIR = 9;
    public static final int BUSCAR = 10;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{}
        
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
