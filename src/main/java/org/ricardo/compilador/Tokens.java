package org.ricardo.compilador;

public class Tokens {

    private String token;
    private String lexeme;
    private int line;
    private int colum;

    public Tokens(String token,String lexeme, int line, int colum)
    {
        this.token = token;
        this.lexeme = lexeme;
        this.colum = colum;
        this.line = line;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColum() {
        return colum;
    }

    public void setColum(int colum) {
        this.colum = colum;
    }

    @Override
    public String toString() {
        return "[" + line + ", " +
                colum + "] " +
                "token='" + token +
                "', lexeme='"+ lexeme+"'";
    }
}
