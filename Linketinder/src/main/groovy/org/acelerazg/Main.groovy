package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidatoController
import org.acelerazg.controllers.CompetenciaController
import org.acelerazg.controllers.EmpresaController
import org.acelerazg.controllers.VagaController
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.PessoaService

static void main(String[] args) {

    CandidatoController candidatoController = new CandidatoController()
    EmpresaController empresaController = new EmpresaController()
    VagaController vagaController = new VagaController()
    CompetenciaController competenciaController = new CompetenciaController()
    UI ui = new UI()
    PessoaService service = new PessoaService()

    Scanner sc = new Scanner(System.in)
    boolean continuar = true
    int opcao


    while (continuar) {
        ui.menu()
        opcao = sc.nextInt()

        switch (opcao) {
            case 1:
                empresaController.listarTodasEmpresas()
                break
            case 2:
                empresaController.cadastrarEmpresa()
                break
            case 3:
                empresaController.atualizarEmpresa()
                break
            case 4:
                empresaController.deletarEmpresa()
                break
            case 5:
                candidatoController.listarTodosCandidatos()
                break
            case 6:
                candidatoController.cadastrarCandidato()
                break
            case 7:
                candidatoController.atualizarCandidato()
                break
            case 8:
                candidatoController.deletarCandidato()
                break
            case 9:
                vagaController.listarTodasVagas()
                break
            case 10:
                vagaController.cadastrarVaga()
                break
            case 11:
                vagaController.atualizarVaga()
                break
            case 12:
                vagaController.deletarVaga()
                break
            case 13:
                competenciaController.listarTodasCompetencias()
                break
            case 14:
                competenciaController.cadastrarCompetencia()
                break
            case 15:
                competenciaController.cadastrarListaCompetencias()
                break
            case 16:
                competenciaController.atualizarCompetencia()
                break
            case 17:
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