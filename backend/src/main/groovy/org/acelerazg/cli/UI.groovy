package org.acelerazg.cli

import org.acelerazg.models.Address
import org.acelerazg.models.Candidate
import org.acelerazg.models.Company
import org.acelerazg.models.Job

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class UI {
    static void menu() {
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

    static LocalDate readDate() {
        Scanner scanner = new Scanner(System.in)
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        boolean keepGoing = true
        LocalDate formattedDate = null
        while (keepGoing) {
            try {
                String date = scanner.next()
                formattedDate = LocalDate.parse(date, dateTimeFormatter)
                keepGoing = false
            } catch (DateTimeParseException e) {
                System.out.println("Data está em formato inválido! Digite novamente: ")
            }
        }
        return formattedDate
    }

    static Candidate readCandidateInfo(String cpf = null) {
        Scanner scanner = new Scanner(System.in)
        print "Digite o nome: "
        String name = scanner.nextLine()

        print "Digite o sobrenome: "
        String lastname = scanner.nextLine()

        print "Digite o e-mail: "
        String email = scanner.nextLine()

        print "Digite o LinkedIn: "
        String linkedin = scanner.nextLine()

        if (cpf == null)
            cpf = requestCpf()

        print "Digite a data de nascimento (dd/MM/yyyy): "
        LocalDate dateOfBirth = readDate()

        print "Digite uma descrição: "
        String description = scanner.nextLine()

        print "Digite uma senha: "
        String senha = scanner.nextLine()

        return new Candidate(name, lastname, email, linkedin, cpf, dateOfBirth, description, senha)
    }

    static Address readAdress() {
        Scanner scanner = new Scanner(System.in)
        print "Digite o país: "
        String country = scanner.nextLine()

        print "Digite o estado: "
        String region = scanner.nextLine()

        print "Digite o CEP: "
        String cep = scanner.nextLine()

        return new Address(country, region, cep)
    }

    static String readSkills() {
        Scanner scanner = new Scanner(System.in)
        print "Digite suas competências (ex: Java, Angular, SQL): "
        return scanner.nextLine()
    }

    static String requestCpf() {
        Scanner scanner = new Scanner(System.in)
        print "Digite o CPF: "
        return scanner.nextLine()
    }

    static Company readCompanyInfo(String cnpj = null) {
        Scanner scanner = new Scanner(System.in)

        print "Digite o nome: "
        String name = scanner.nextLine()

        print "Digite o e-mail: "
        String email = scanner.nextLine()

        print "Digite o LinkedIn: "
        String linkedin = scanner.nextLine()

        if (cnpj == null) {
            cnpj = requestCnpj()
        }

        print "Digite uma descrição: "
        String description = scanner.nextLine()

        print "Digite uma senha: "
        String password = scanner.nextLine()

        return new Company(name, email, linkedin, description, password, cnpj)
    }

    static String requestCnpj() {
        Scanner scanner = new Scanner(System.in)
        print "Digite o CNPJ: "
        return scanner.nextLine().trim()
    }

    static String readLine(String message) {
        Scanner scanner = new Scanner(System.in)
        print(message)
        return scanner.nextLine().trim()
    }

    static int readInt() {
        Scanner scanner = new Scanner(System.in)
        print("Escolha o número da vaga que deseja: ")
        return scanner.nextInt()
    }

    static Job readJobInfo() {
        Scanner scanner = new Scanner(System.in)
        print "Digite o nome: "
        String name = scanner.nextLine()

        print "Digite uma descrição: "
        String description = scanner.nextLine()
        return new Job(name, description)
    }
}
