package org.acelerazg.controllers

import org.acelerazg.models.Competencia
import org.acelerazg.services.CompetenciaService

class CompetenciaController {

    CompetenciaService service
    Scanner sc

    CompetenciaController() {
        this.service = new CompetenciaService()
        this.sc = new Scanner(System.in)
    }

    void listarTodasCompetencias() {
        List<Competencia> competencias = service.listarTodasCompetencias()
        competencias.forEach {it -> println(it.toString())}
    }

    void cadastrarCompetencia() {
        this.sc.nextLine()
        print("Digite o nome da nova competência: ")
        String comp = this.sc.nextLine()
        service.cadastrarNovaCompetencia(comp)
    }

    void cadastrarListaCompetencias() {
        print "Digite as novas competências (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()
        service.cadastrarListaCompetencias(competencias)
    }

    void atualizarCompetencia() {
        this.sc.nextLine()
        print("Digite o nome da competência que deseja atualizar: ")
        String compAntiga = this.sc.nextLine()

        if (!service.existeCompetencia(compAntiga)) {
            println("[AVISO]: Esta competência não existe em nossa base de dados!")
            return
        }

        print("Digite o novo nome da competência: ")
        String nova = this.sc.nextLine()

        service.atualizarCompetencia(compAntiga, nova)
    }

    void deletarCompetencia() {
        this.sc.nextLine()
        print("Digite o nome da competência que deseja deletar: ")
        String comp = this.sc.nextLine()

        if (!service.existeCompetencia(comp)) {
            println("[AVISO]: Esta competência não existe em nossa base de dados!")
            return
        }

        service.deletarCompetencia(comp)
    }
}
