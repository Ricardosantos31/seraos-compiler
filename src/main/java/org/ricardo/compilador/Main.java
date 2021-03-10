package org.ricardo.compilador;

import org.ricardo.semantico.Semantico;
import org.ricardo.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) {
        var sinalizador = new Sinalizadores();
        sinalizador.verificaTk(args);
        var path = sinalizador.validatePath(args);
        var token = new Token();
        var arquivo = new Arquivo();
        var conteudoDoArquivo = arquivo.lerArquivo(path);
        var listaDeTokens = token.tk(conteudoDoArquivo);
        System.out.println("Analise léxica concluida");
        new Sintatico(listaDeTokens).analiseSintatico();
        System.out.println("Analise sinattico concluida");
        new Semantico(listaDeTokens).analisador();
    }
}


