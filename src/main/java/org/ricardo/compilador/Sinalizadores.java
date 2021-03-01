package org.ricardo.compilador;

public class Sinalizadores {

    public void verificaTk(String[] cmd) {

        for (String s : cmd) {

            switch (s) {
                case "--tk" -> Sinalizador.TOKEM.setStatus(true);
                case "--sin" -> Sinalizador.SINTATICO.setStatus(true);
            }
        }
    }

    public String validatePath(String[] cmd) {
        for (String arg : cmd) {
            if (arg.contains(".txt")) return arg;
        }
        throw new IllegalArgumentException("org.ricardo.compilador.Arquivo nao reconhecido");
    }
}
