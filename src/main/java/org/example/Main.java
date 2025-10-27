package org.example;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

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

        var juego5 = dao.findById(5);
        if (juego5.isPresent()){
            (juego5.get()).setNombre("Sonic 3");
            dao.update(juego5.get());
        }


        dao.findById(77).ifPresentOrElse(
            (j)-> {
                j.setNombre("Fornite - Cesur");
                dao.update(j).ifPresent(System.out::println);
            },
            () -> {
                System.out.println("No se encontro el videojuego");
            }
        );

    }


    /* Métodos tipicos de repositorio - Capa de negocio */

    Optional<VideoJuego> saveOrUpdate(VideoJuego videojuego){
        return Optional.empty();
    }

    Boolean exists(VideoJuego videojuego){
        return false;
    }
}