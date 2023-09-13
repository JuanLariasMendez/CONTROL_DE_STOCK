package com.alura.jdbc.controller;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {
    private CategoriaDAO categoriaDAO;

    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion()); //estamos inicializando el DAO enviando una conexión del pool de conexiones
    }

    //Listado unicamente para categoria
	public List<Categoria> listar() {
		return categoriaDAO.lista();
	}

    public List<?> cargaReporte() {
        // TODO
        return new ArrayList<>();
    }

}
