package org.example;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoJuego implements Serializable {
    private Integer id;
    private String nombre;
    private String desarrollador;
    private Integer año;
    private String genero;
    private String plataforma;
}
