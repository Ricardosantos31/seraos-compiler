package org.ricardo.compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public List<String> lerArquivo(String url) {
        List<String> cod = new ArrayList<>();
        try (var arquivo = new BufferedReader(new BufferedReader(new FileReader(url)))) {
            while (arquivo.ready()) {
                cod.add(arquivo.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cod;
    }

}
