package org.ricardo.sintatico;

import org.ricardo.compilador.Sinalizador;
import org.ricardo.compilador.Tokens;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static org.ricardo.sintatico.Util.naoTerminal;
import static org.ricardo.sintatico.Util.terminal;
import static org.ricardo.sintatico.Util.getTabelaSintatica;
import static org.ricardo.sintatico.Util.getRegrasProducao;

public class Sintatico {

    private LinkedList<Tokens> filaTk;
    private Stack<String> pilha;

    public Sintatico(List<Tokens> listOfTokens) {
        this.filaTk = new LinkedList<>(listOfTokens); //inicia a fila
        this.pilha = new Stack<>();// inicia a pilha
        pilha.push("$"); // add $ e SERAOS para no final mostrar que o processo deu certo
        pilha.push("SERAOS");
    }

    public void analiseSintatico() { // verifica o que esta no topo

        while (!pilha.isEmpty()) {

            var tk = filaTk.getFirst().getToken();
            var line = filaTk.getFirst().getLine();
            var column = filaTk.getFirst().getColum();
            var topo = pilha.peek();

            if (isTerminal(topo)) {
                terminalNoTopo(tk, line, column);
            } else {
                naoTerminalNoTopo(tk, line, column);
            }
        }
    }

    private void naoTerminalNoTopo(String tk, int line, int column) {
        var topo = pilha.peek();
        var naoTerminalId = naoTerminal.get(topo);
        var tokenId = terminal.get(tk);
        var indice = getTabelaSintatica().get(naoTerminalId).get(tokenId); // checa a tabela sintatica

        if (indice < 0) { // erro sintatico
            erro(topo, tk, line, column);
        }
        var regraDeProducao = new LinkedList<>(getRegrasProducao().get(indice));
        log((!isTerminal(topo) ? "nao terminal \"" : "token \"") + topo + "\" foi removido na pilha");
        pilha.pop();
        while (!regraDeProducao.isEmpty()) {
            var regra = regraDeProducao.removeLast();
            log((!isTerminal(regra) ? "nao terminal \"" : "token \"") + regra + "\" foi colocada na pilha");
            pilha.push(regra);
        }
    }


    private void terminalNoTopo(String tk, int line, int column) {
        var tkId = terminal.get(tk);
        var topo = pilha.peek();
        var topoId = terminal.get(topo);

        if (tkId.equals(topoId)) {
            filaTk.removeFirst();
            pilha.pop();
            log("Token \"" + tk + "\" foi removido da lista de tokens");
            log("Token \"" + tk + "\" foi removido da pilha de tokens");
        } else {
            erro(topo, tk, line, column);
        }

    }

    private boolean isTerminal(String s) {
        return terminal.containsKey(s);
    }

    private void log(String msg) {
        if (Sinalizador.SINTATICO.getStatus() || Sinalizador.TODOS.getStatus()) {
            System.out.println(msg);
        }
    }

    private void erro(String topo, String tk, Integer line, Integer column) {
        throw new SintaticException("[" + line + ", " + column + "]" +
                " foi encontrado o token: \"" + tk + "\", porem era esperado: \"" + topo + "\"");
    }

}

