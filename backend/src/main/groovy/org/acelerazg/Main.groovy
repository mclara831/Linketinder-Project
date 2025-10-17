package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidateController
import org.acelerazg.controllers.SkillController
import org.acelerazg.controllers.CompanyController
import org.acelerazg.controllers.JobController

static void main(String[] args) {

    CandidateController candidatoController = new CandidateController()
    CompanyController empresaController = new CompanyController()
    JobController vagaController = new JobController()
    SkillController competenciaController = new SkillController()

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
                vagaController.findAll()
                break
            case 10:
                vagaController.findJobFromACompany()
                break
            case 11:
                vagaController.create()
                break
            case 12:
                vagaController.update()
                break
            case 13:
                vagaController.delete()
                break
            case 14:
                competenciaController.findAll()
                break
            case 15:
                competenciaController.create()
                break
            case 16:
                competenciaController.registerAList()
                break
            case 17:
                competenciaController.update()
                break
            case 18:
                competenciaController.delete()
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