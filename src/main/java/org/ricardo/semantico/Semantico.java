package org.ricardo.semantico;

import org.ricardo.compilador.Sinalizador;
import org.ricardo.compilador.Token;
import org.ricardo.compilador.Tokens;
import org.ricardo.sintatico.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Semantico {
    private List<Tokens> listaTokens;
    private Map<String, Boolean> listaDeclarados;

    public Semantico(List<Tokens> listaTokens) {
        this.listaTokens = new ArrayList<>(listaTokens);
        this.listaDeclarados = new HashMap<>();
    }

    public void analisador() {
        int i = 0;
        while (i < listaTokens.size()) {
            Tokens tokenAtual = listaTokens.get(i);
            if (tokenAtual.getToken().equals("var")) {
                Tokens tokenProximo = listaTokens.get(i + 1);
                if (listaDeclarados.containsKey(tokenProximo.getLexeme())) {
                    erro("a variavel \"" + tokenProximo.getLexeme() + "\" ja foi declarada! ", tokenProximo.getLine(), tokenProximo.getColum());
                }
                listaDeclarados.put(tokenProximo.getLexeme(), false);
                print("A variavel \"" + tokenProximo.getLexeme() + "\" foi declarada");
            } else if (tokenAtual.getToken().equals("variavel")) {
                if (!listaDeclarados.containsKey(tokenAtual.getLexeme())) {
                    erro("a variavel \"" + tokenAtual.getLexeme() + "\" nao foi declarada! ",
                            listaTokens.get(i).getLine(), listaTokens.get(i).getColum());
                }
                StringBuffer buffer = new StringBuffer();
                if (listaTokens.get(++i).getLexeme().equals("=")) {
                    i++;
                    while (!listaTokens.get(i).getToken().equals(";")) {
                        String valor = listaTokens.get(i).getLexeme();
                        if (Token.isId(valor)) {
                            if (!listaDeclarados.containsKey(tokenAtual.getLexeme())) {
                                erro("a variavel \"" + tokenAtual.getLexeme() + "\" nao foi declarada! ",
                                        listaTokens.get(i).getLine(), listaTokens.get(i).getColum());
                            }
                            if (listaDeclarados.get(valor)) {
                                buffer.append(valor);
                            } else {
                                buffer.append("0");
                            }
                        } else {
                            buffer.append(valor);
                        }
                        i++;
                    }
                    if (buffer.toString().equals("0")) {
                        listaDeclarados.put(tokenAtual.getLexeme(), false);
                    } else if (buffer.toString().contains("/0")) {
                        erro("encontrado divisao por 0! ", tokenAtual.getLine(), tokenAtual.getColum());
                    } else {
                        listaDeclarados.put(tokenAtual.getLexeme(), true);
                    }
                    print("A variavel \"" + tokenAtual.getLexeme() + "\" recebeu valor: " + buffer.toString());
                }
            }
            i++;
        }
    }

    public Map<String, Boolean> getListaDeclarados() {
        return listaDeclarados;
    }

    private void erro(String erro, int linha, int coluna) {
        throw new SemanticoException("ERRO SEMANTICO, " + erro + linha + ", " + coluna);
    }

    public void print(String msg) {
        if (Sinalizador.SEMANTICO.getStatus() || Sinalizador.TODOS.getStatus()) {
            System.out.println(msg);
        }
    }
}
