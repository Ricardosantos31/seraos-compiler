package org.ricardo.compilador;

public enum Sinalizador {


    TOKEM("--tk", false),
    SINTATICO("--sin",false);


    private String value;
    private Boolean status;

    Sinalizador(String value, boolean status) {
        this.value = value;
        this.status = status;
    }

    public Boolean getStatus() {

        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
