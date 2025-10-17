package org.acelerazg.controllers


import org.acelerazg.models.Vaga
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.CompanyService
import org.acelerazg.services.EnderecoService
import org.acelerazg.services.VagaService

class VagaController {

    Scanner sc
    VagaService service
    EnderecoService enderecoService
    CompanyService empresaService
    CompetenciaService competenciaService

    VagaController() {
        this.sc = new Scanner(System.in)
        this.service = new VagaService()
        this.enderecoService = new EnderecoService()
        this.empresaService = new CompanyService()
        this.competenciaService = new CompetenciaService()
    }

    void listarTodasVagas() {
        List<Vaga> vagas = service.listarTodasVagas()
        vagas.forEach { it ->
            {
                println(it.toString())
                print(enderecoService.encontrarEnderecoPorID(it.enderecoId).toString())
                println("\n\tCompetencias: " + competenciaService.buscaCompetenciasDaVaga(it.id).join(", "))
            }
        }
    }

    void cadastrarVaga() {
        this.sc.nextLine()
        print "Digite o cnpj da empresa: "
        String cnpj = this.sc.nextLine()

        if (!empresaService.cnpjValid(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        print "Digite o nome: "
        String nome = this.sc.nextLine()

        print "Digite uma descrição: "
        String descricao = this.sc.nextLine()

        print "Digite o pais: "
        String pais = this.sc.nextLine()

        print "Digite o estado: "
        String estado = this.sc.nextLine()

        print "Digite o cep: "
        String cep = this.sc.nextLine()

        print "Digite suas competencias (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()

        service.inserirNovaVaga(nome, descricao, pais, estado, cep, competencias, cnpj)
    }

    void atualizarVaga() {
        List<Vaga> vagas = service.listarTodasVagas()

        for (i in 0..<vagas.size()) {
            println("[" + (i + 1) + "] " + vagas[i].toString() + "\n")
        }

        print "Escolha o número da vaga que deseja atualizar: "
        int opcao = this.sc.nextInt()

        if (opcao <= 0 || opcao > vagas.size()) {
            println "[AVISO]: Esta vaga não é válida!"
            return
        }

        sc.nextLine()
        print "Digite o nome: "
        String nome = this.sc.nextLine()

        print "Digite uma descrição: "
        String descricao = this.sc.nextLine()

        print "Digite o pais: "
        String pais = this.sc.nextLine()

        print "Digite o estado: "
        String estado = this.sc.nextLine()

        print "Digite o cep: "
        String cep = this.sc.nextLine()

        print "Digite suas competencias (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()

        service.atualizarVagaPorId(vagas[opcao - 1], nome, descricao, pais, estado, cep, competencias)
    }

    void deletarVaga() {
        List<Vaga> vagas = service.listarTodasVagas()

        for (i in 0..<vagas.size()) {
            println("[" + (i + 1) + "] " + vagas[i].toString())
        }

        print "Escolha o número da vaga que deseja deletar: "
        int opcao = this.sc.nextInt()

        if (opcao <= 0 || opcao > vagas.size()) {
            println "[AVISO]: Esta vaga não é válida!"
            return
        }
        service.deletarVagaPorId(vagas[opcao - 1].id)
    }

    void buscarVagasPorEmpresa() {
        this.sc.nextLine()
        print "Digite o cnpj da empresa: "
        String cnpj = this.sc.nextLine()

        if (!empresaService.cnpjValid(cnpj)) {
            println "[AVISO]: Este CNPJ não está cadastrado em nossa base de dados!"
            return
        }

        List<Vaga> vagas = service.buscarVagasDeEmpresa(cnpj)
        if (vagas.isEmpty()) {
            println("[AVISO]: Não foram encontradas vagas cadastradas nesse CNPJ!")
            return
        }

        vagas.forEach { it ->
            {
                println(it.toString())
                println("\tEmpresa = " + empresaService.findById(it.empresaId).nome)
                print(enderecoService.encontrarEnderecoPorID(it.enderecoId).toString())
                println("\n\tCompetencias: " + competenciaService.buscaCompetenciasDaVaga(it.id).join(", "))
            }
        }
    }


}