package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidatoController
import org.acelerazg.controllers.CompetenciaController
import org.acelerazg.controllers.CompanyController
import org.acelerazg.controllers.VagaController

static void main(String[] args) {

    CandidatoController candidatoController = new CandidatoController()
    CompanyController empresaController = new CompanyController()
    VagaController vagaController = new VagaController()
    CompetenciaController competenciaController = new CompetenciaController()

    Scanner sc = new Scanner(System.in)
    boolean continuar = true
    int opcao


    while (continuar) {
        UI.menu()
        opcao = sc.nextInt()

        switch (opcao) {
            case 1:
                empresaController.findAll()
                break
            case 2:
                empresaController.create()
                break
            case 3:
                empresaController.update()
                break
            case 4:
                empresaController.delete()
                break
            case 5:
                candidatoController.findAll()
                break
            case 6:
                candidatoController.create()
                break
            case 7:
                candidatoController.update()
                break
            case 8:
                candidatoController.delete()
                break
            case 9:
                vagaController.listarTodasVagas()
                break
            case 10:
                vagaController.buscarVagasPorEmpresa()
                break
            case 11:
                vagaController.cadastrarVaga()
                break
            case 12:
                vagaController.atualizarVaga()
                break
            case 13:
                vagaController.deletarVaga()
                break
            case 14:
                competenciaController.listarTodasCompetencias()
                break
            case 15:
                competenciaController.cadastrarCompetencia()
                break
            case 16:
                competenciaController.cadastrarListaCompetencias()
                break
            case 17:
                competenciaController.atualizarCompetencia()
                break
            case 18:
                competenciaController.deletarCompetencia()
                break
            case 0:
                println("Obrigado por utilizar o Linketinder!")
                continuar = false
                break
            default:
                println("Opção inválida!")
                break
        }
    }

}