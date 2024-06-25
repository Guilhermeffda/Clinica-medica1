package RA3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static <T> void salvarObjeto(List<T> objetos, String caminho) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(objetos);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarObjeto(String caminho) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (List<T>) ois.readObject();
        } catch (ClassCastException e) {
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro de conversão
        }
    }
}
