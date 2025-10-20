package org.example;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DataSource ds = DataProvider.getDataSource(
                "jdbc:mysql://localhost:3306/ad",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASSWORD")
        );

        var dao = new VideoJuegoDAO(ds);

        System.out.println("FindAll");
        dao.findAll().forEach(System.out::println);

        System.out.println("FindById");
        dao.findById(20).ifPresentOrElse(System.out::println, ()->{
            System.out.println("No se encontro el videojuego");
        });

        System.out.println("Save");

        VideoJuego videojuego = new VideoJuego();
        videojuego.setPlataforma("Megadrive");
        videojuego.setNombre("Sonic");
        videojuego.setAño(1991);
        videojuego.setGenero("Plataformas");
        videojuego.setDesarrollador("Sega");

        dao.save(videojuego).ifPresent(System.out::println);

        var juego = dao.findById(6);
        if (juego.isPresent()){
            System.out.println("Juego 6 encontrado");
            dao.delete(juego.get());
        }else{
            System.out.println("No se encontro el videojuego");
        }



    }
}