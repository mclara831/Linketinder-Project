package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidatoController
import org.acelerazg.controllers.EmpresaController
import org.acelerazg.services.PessoaService

static void main(String[] args) {

    CandidatoController candidatoController = new CandidatoController()
    EmpresaController empresaController = new EmpresaController()
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