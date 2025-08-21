package models

abstract class Pessoa implements IPessoa {

    String nome
    String email
    String estado
    String cep
    String descricao
    List<String> competencias = new ArrayList<>();


    @Override
    public String toString() {
        return "nome=" + nome +
                "\nemail=" + email +
                "\nestado=" + estado +
                "\ncep=" + cep +
                "\ndescricao=" + descricao +
                "\ncompetencias=" + competencias
    }
}

