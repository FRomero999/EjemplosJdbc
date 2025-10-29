package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class UsoLambda {

    public static void saludo(String nombre, Function<String,String> dialecto, Consumer<String> canal, Consumer<String> listener){
        canal.accept( dialecto.apply(nombre) );
        listener.accept( nombre );
    }

    public static void escribirArchivo(String s){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("salida.txt"))){
            bw.write(s + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        saludo("Francisco",(s) -> "Hola "+s ,System.out::println,(_)->{});
        saludo("Francisco",(s) -> "Hello "+s,System.out::println,(_)->{});
        saludo("Francisco",(s) -> "Ni Hao "+s,UsoLambda::escribirArchivo,(_)->{
            System.out.println("Archivo escrito");
        });


    }
}
