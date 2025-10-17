package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidateController
import org.acelerazg.controllers.SkillController
import org.acelerazg.controllers.CompanyController
import org.acelerazg.controllers.JobController

static void main(String[] args) {

    CandidateController candidateController = new CandidateController()
    CompanyController companyController = new CompanyController()
    JobController jobController = new JobController()
    SkillController skillController = new SkillController()

    Scanner sc = new Scanner(System.in)
    boolean keepGoing = true
    int option


    while (keepGoing) {
        UI.menu()
        option = sc.nextInt()

        switch (option) {
            case 1:
                companyController.findAll()
                break
            case 2:
                companyController.create()
                break
            case 3:
                companyController.update()
                break
            case 4:
                companyController.delete()
                break
            case 5:
                candidateController.findAll()
                break
            case 6:
                candidateController.create()
                break
            case 7:
                candidateController.update()
                break
            case 8:
                candidateController.delete()
                break
            case 9:
                jobController.findAll()
                break
            case 10:
                jobController.findJobFromACompany()
                break
            case 11:
                jobController.create()
                break
            case 12:
                jobController.update()
                break
            case 13:
                jobController.delete()
                break
            case 14:
                skillController.findAll()
                break
            case 15:
                skillController.create()
                break
            case 16:
                skillController.registerAList()
                break
            case 17:
                skillController.update()
                break
            case 18:
                skillController.delete()
                break
            case 0:
                println("Obrigado por utilizar o Linketinder!")
                keepGoing = false
                break
            default:
                println("Opção inválida!")
                break
        }
    }

}