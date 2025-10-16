package org.acelerazg.controllers

import org.acelerazg.models.Company
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.EmpresaService
import org.acelerazg.services.EnderecoService

class EmpresaController {

    EmpresaService service
    EnderecoService enderecoService
    CompetenciaService competenciaService
    Scanner sc

    EmpresaController() {
        this.service = new EmpresaService()
        this.enderecoService = new EnderecoService()
        this.competenciaService = new CompetenciaService()
        sc = new Scanner(System.in)
    }

    void listarTodasEmpresas() {
        List<Company> empresas = service.findAll()
        empresas.each { it ->
            {
                println(it.toString())
                print(enderecoService.encontrarEnderecoPorID(it.enderecoId).toString())
                println("\n\tCompetencias: " + competenciaService.buscaCompetenciasDaEmpresa(it.id).join(", "))
            }
        }
    }

    void cadastrarEmpresa() {
        print "Digite o nome: "
        String nome = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o email: "
        String email = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o linkedin: "
        String linkedin = this.sc.nextLine()

        print "Digite o cnpj: "
        String cnpj = this.sc.nextLine()

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

        print "Digite suas competencias (competencia1, competencia2,...): "
        String competencias = sc.nextLine()

        service.inserirNovaEmpresa(nome, email, linkedin, cnpj, descricao, senha, pais, estado, cep, competencias);
    }

    void atualizarEmpresa() {
        print "Digite o cnpj da empresa: "
        String cnpj = this.sc.nextLine()

        if (!service.cnpjValido(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        print "Digite o nome: "
        String nome = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o email: "
        String email = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o linkedin: "
        String linkedin = this.sc.nextLine()

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

        print "Digite suas competencias (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()

        service.atualizarEmpresaPorCnpj(cnpj, nome, email, linkedin, descricao, senha, pais, estado, cep, competencias);
    }

    void deletarEmpresa() {
        print "Digite o cnpj da empresa: "
        String cnpj = this.sc.nextLine()
        service.deletarEmpresaPorCnpj(cnpj)
    }
}
