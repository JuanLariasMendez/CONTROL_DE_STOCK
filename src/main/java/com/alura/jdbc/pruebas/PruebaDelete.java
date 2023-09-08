package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaDelete {
    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        //Creación del statemnt/query
        Statement statement = con.createStatement();

        statement.execute("DELETE FROM PRODUCTO WHERE ID = 99");

        //Para corroborar que el elemento haya sido eliminado
        //Nos devuelve cuantas filas fueron modificadas luego de hacer el query dentro del statement
        //Con retur, el programa nos da el mensaje, esto para no hacer un sout
        System.out.println("Filas modificadas: "+statement.getUpdateCount());
    }
}
