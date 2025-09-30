package org.acelerazg.models

class Empresa extends Pessoa {

    String cnpj
    String pais

    @Override
    public String toString() {
        return "\n\nEmpresa: " + super.toString() + "\nCNPF: " + cnpj;
    }
}
