package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidatoController
import org.acelerazg.repositories.CandidatoRepository
import org.acelerazg.services.PessoaService

static void main(String[] args) {

    CandidatoController candidatoController = new CandidatoController()
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
                service.empresas.each { it -> println(it.toString()) }
                break
            case 2:
                def empresa = ui.cadastrarEmpresa()
                service.empresas.add(empresa)
                break
            case 3:
                candidatoController.listarTodosCandidatos()
                break
            case 4:
                candidatoController.cadastrarCandidato()
                break
            case 5:
                candidatoController.atualizarCandidato()
                break
            case 6:
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