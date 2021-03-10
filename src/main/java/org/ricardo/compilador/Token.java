package org.ricardo.compilador;

import java.util.*;

public class Token {

    int lineCount = 1;
    int colunCount = 1;

    List<String> line = new ArrayList<>();
    List<Tokens> ListofTokens = new ArrayList<>();
    public final static Map<String, String> palavrasChave = new HashMap<>();

    static {
        palavrasChave.put("start!", "init_program");
        palavrasChave.put("end!", "close_program");
        palavrasChave.put("if!", "if");
        palavrasChave.put("else!", "else");
        palavrasChave.put("endif!", "endif");
        palavrasChave.put("loop!", "loop");
        palavrasChave.put("endloop!", "endloop");
        palavrasChave.put("print!", "write");
        palavrasChave.put("scan!", "read");
        palavrasChave.put("var!", "var");
    }


    public static boolean isKeyword(String s) {
        switch (s) {
            case "start!":
            case "end!":
            case "var!":
            case "if!":
            case "endif!":
            case "else!":
            case "loop!":
            case "endloop!":
            case "print!":
            case "scan!":
                return true;
        }

        return false;
    }

    public static boolean isSymbol(String s) {
        switch (s) {
            case ";":
            case "(":
            case ")":
            case "=":
            case "+":
            case "-":
            case "*":
            case "/":
            case "<":
            case ">":
                return true;
        }

        return false;
    }

    public static boolean isId(String s) {
        if (!Character.isLetter(s.charAt(0))) {
            return false;
        }
        return s.chars().allMatch(Character::isLetterOrDigit);
    }

    public static boolean isNumber(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    public List<Tokens> tk(List<String> c) {
        String ex = "[+*\\-<>/=\\s)(\";]"; //criterios de divisao

        String reg = "((?<=" + ex + ")|" + "(?=" + ex + "))";


        for (String s : c) {
            if (s.isEmpty()) {
                lineCount++;
                continue;
            }
            line = Arrays.asList(s.split(reg));
            //percorre a lista de linha quebrada pelo regex
            for (int i = 0; i < line.size(); i++) {
                String lexeme = line.get(i);

                if (lexeme.isBlank()) {
                    if (lexeme.equals("\t")) {
                        colunCount += 4;
                    } else
                        colunCount++;
                    continue;
                }


                if (isKeyword(lexeme)) {
                    var terminal = palavrasChave.get(lexeme);
                    ListofTokens.add(new Tokens(terminal, lexeme, lineCount, colunCount));
                    colunCount += lexeme.length();
                } else if (isSymbol(lexeme)) {
                    ListofTokens.add(new Tokens(lexeme, lexeme, lineCount, colunCount));
                    colunCount += lexeme.length();
                }
                // add caso for um id
                else if (isId(lexeme)) {
                    ListofTokens.add(new Tokens("variavel", lexeme, lineCount, colunCount));
                    colunCount += lexeme.length();
                }
                // add caso for um numeral
                else if (isNumber(lexeme)) {
                    ListofTokens.add(new Tokens("number", lexeme, lineCount, colunCount));
                    colunCount += lexeme.length();
                }
                //add caso for inicio de uma string literal
                else if (lexeme.equals("\"")) {
                    StringBuilder string = new StringBuilder();

                    do {
                        string.append(line.get(i));

                        if (i + 1 == line.size()) {
                            System.err.println("LEXICAL ERROR at [" + lineCount + ", " + colunCount + "] " +
                                    "Faltou aspas.");
                            System.exit(-1);
                        }
                    } while (!line.get(++i).equals("\""));

                    string.append('"');
                    ListofTokens.add(new Tokens("string_literal", string.toString(), lineCount, colunCount));
                    colunCount += string.length();
                } else {
                    System.err.println("LEXICAL ERROR at [" + lineCount + ", " + colunCount + "] " +
                            lexeme + " não foi reconhecido.");
                    System.exit(-1);
                }
            }
            colunCount = 1;
            lineCount++;
        }
        ListofTokens.add(new Tokens("$", "$", lineCount, colunCount));
        if (Sinalizador.TOKEM.getStatus() || Sinalizador.TODOS.getStatus()) {
            for (Tokens listofTokens : ListofTokens) {
                System.out.println(listofTokens.toString());
            }
        }
        return ListofTokens;
    }
}
