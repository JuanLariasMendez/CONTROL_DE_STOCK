package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;

import java.util.List;

/**
 * Capa que hace la conexión de la vista con la capa de datos
 * Contiene las lógicas de negocio para manipular los datos antes de
 * guardar en la base de datos o para devolver a la pantalla.
 */

public class ProductoController {
	/**
	 * Instancia de la clase ProductoDAO
	 */
	private ProductoDAO productoDAO;

	/**
	 * Constructor en el que hacemos la instancia directa a la conexión
	 */
	public ProductoController(){
		var factory = new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperaConexion());
	}

	/**
	 * Método para eliminar, en el que toda la lógica esta en ProductoDAO
	 * Para eliminar un producto, hacemos uso de su PK id
	 * @param id
	 * @return
	 */
	public int eliminar(Integer id)  {
		return productoDAO.eliminar(id);
	}

	/**
	 * Método para listar todos  los productos, en el que toda la lógica esta en ProductoDAO
	 * @return
	 */
	public List<Producto> listar(){
		//Instancia de un productoDAO que nos devolvera el listado de productos
		return productoDAO.listar();
	}

	/**
	 * Método para modificar, en el que toda la lógica esta en ProductoDAO
	 * podemos modificar cada uno de los parametros del producto, basandonos en el id
	 * @param nombre
	 * @param descripcion
	 * @param cantidad
	 * @param id
	 * @return
	 */
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return productoDAO.modificar(nombre, descripcion, cantidad, id);
	}

	/**
	 * Método para guardar, en el que toda la lógica esta en la clase ProductoDAO
	 * Hacemos uso de un objeto tipo producto para poder guardarlo
	 * Esto debido a que utilizamos la Clase del modelo Producto, para poder convertir dichos parametros
	 * y por insertarlos en la base de datos
	 * @param producto
	 */
	public void guardar(Producto producto, Integer categoriaId) {
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}

	//Sobreescritura del Método listar, que busca todos los productos pero por categoria
	public List<Producto> listar (Categoria categoria){
		return productoDAO.listar(categoria); //busqueda de los productos por su categoria
	}
}

