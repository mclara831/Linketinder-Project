package org.acelerazg.cli

import org.acelerazg.models.Candidato
import org.acelerazg.models.Empresa
import org.acelerazg.services.CandidatoService

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class UI {

    Scanner sc
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    CandidatoService candidatoService = new CandidatoService()

    UI() {
        this.sc = new Scanner(System.in)
    }

    void menu() {
        println "\n\n========================= MENU ============================="
        println "\nEmpresas "
        println "1. Listar todas empresas"
        println "2. Cadastrar nova empresa"
        println "3. Atualizar dados de empresa"
        println "4. Deletar empresa"
        println "\nCandidatos "
        println "5. Listar todos candidatos"
        println "6. Cadastrar novo candidato"
        println "7. Atualizar dados de candidato"
        println "8. Deletar candidato"
        println "\nVagas "
        println "9. Listar todas vagas"
        println "10. Encontrar vagas por CNPJ"
        println "11. Cadastrar nova vaga"
        println "12. Atualizar dados de vaga"
        println "13. Deletar vaga"
        println "\nCompetencias "
        println "14. Listar todas competências"
        println "15. Cadastrar nova competência"
        println "16. Cadastrar uma lista de competência"
        println "17. Atualizar dados de competência"
        println "18. Deletar competência"
        println "0. Sair"
        println "============================================================"
        print "Escolha uma opcao: "
    }

    static LocalDate lerData() {
        Scanner scanner = new Scanner(System.in)
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean continuar = true;
        LocalDate dataFormatada = null;
        while (continuar) {
            try {
                String data = scanner.next();
                dataFormatada = LocalDate.parse(data, dateTimeFormatter);
                continuar = false;
            } catch (DateTimeParseException e) {
                System.out.println("Data está em formato inválido! Digite novamente: ");
            }
        }
        return dataFormatada;
    }

    Candidato cadastrarCandidato() {
        print "Digite o nome: "
        String nome = this.sc.nextLine()

        print "Digite o sobrenome: "
        String sobrenome = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o email: "
        String email = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o linkedin: "
        String linkedin = this.sc.nextLine()

        print "Digite o cpf: "
        String cpf = this.sc.nextLine()

        print "Digite a data de nascimento: "
        String idade = lerData()

        print "Digite o pais: "
        String pais = this.sc.nextLine()

        print "Digite o estado: "
        String estado = this.sc.nextLine()

        print "Digite o cep: "
        String cep = this.sc.nextLine()

        print "Digite uma descrição: "
        String descricao = this.sc.nextLine()

        print "Digite uma senha: "
        String senha = this.sc.nextLine()


    }

    Empresa cadastrarEmpresa() {
        print "Digite o nome: "
        def nome = this.sc.nextLine()

        print "Digite o email: "
        def email = this.sc.nextLine()

        print "Digite o cnpj: "
        def cnpj = this.sc.nextLine()

        print "Digite o estado: "
        def estado = this.sc.nextLine()

        print "Digite o pais: "
        def pais = this.sc.nextLine()

        print "Digite o cep: "
        def cep = this.sc.nextLine()

        print "Digite uma descrição: "
        def descricao = this.sc.nextLine()

        print "Digite as competencias (competencia1, contencia2,...): "
        def competencias = this.sc.nextLine()

        return new Empresa(nome: nome, email: email, cnpj: cnpj, cep: cep, estado: estado, pais: pais, descricao: descricao, competencias: competencias.split(","))
    }
}
