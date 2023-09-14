package com.alura.jdbc.modelo;/** * Este objeto del tipo producto es el que representa nuestra tabla de * producto en la base de datos, pero aquí en el proyecto de Java. */public class Producto {    //Atributos de la tabla producto    private Integer id;    private String nombre;    private String descripcion;    private Integer cantidad;    private Integer categoriaId;    /**     * Primera sobreescritura del costructor, en el que inicamente utilizamos nombre, descripcion y cantidad     * Este es utilizado en el ControlDeStockFrame para poder convertir las entradas en el formulario     * Y asi poder utilizarlas en nuestras querys, esto transformando a un objeto tipo Producto     * @param nombre     * @param descripcion     * @param cantidad     */    public Producto(String nombre, String descripcion, Integer cantidad) {        this.nombre=nombre;        this.descripcion=descripcion;        this.cantidad=cantidad;    }    /**     * Este costructor en principio es utilizado para listar los productos en productoController.listar()     * @param id     * @param nombre     * @param descripcion     * @param cantidad     */    public Producto(int id, String nombre, String descripcion, int cantidad) {        this.id=id;        this.nombre=nombre;        this.descripcion=descripcion;        this.cantidad=cantidad;    }    public Producto(int id, String nombre, int cantidad) {        this.id = id;        this.nombre=nombre;        this.cantidad=cantidad;    }    //Getters y Setters    public String getNombre() {        return this.nombre;    }    public void setNombre(String nombre) {        this.nombre = nombre;    }    public String getDescripcion() {        return this.descripcion;    }    public void setDescripcion(String descripcion) {        this.descripcion = descripcion;    }    public Integer getCantidad() {        return this.cantidad;    }    public void setCantidad(Integer cantidad) {        this.cantidad = cantidad;    }    public void setId(int id) {        this.id = id;    }    public Integer getId() {return this.id;}    public void setId(Integer id) {this.id = id;}    public int getCategoriaId() {        return this.categoriaId;    }    public void setCategoriaId(Integer categoriaId) {        this.categoriaId = categoriaId;    }    /**     * Sobreescritura del método String para que no nos retorne el valor guardado en mémoria a la hora de llamar a los valores ingresados     * @return     */    @Override    public String toString() {        return String.format(                "{id: %d, nombre: %s, descripcion: %s, cantidad: %d}",                this.id,                this.nombre,                this.descripcion,                this.cantidad        );    }}