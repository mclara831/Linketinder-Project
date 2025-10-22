package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.controllers.CandidateController
import org.acelerazg.controllers.SkillController
import org.acelerazg.controllers.CompanyController
import org.acelerazg.controllers.JobController
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.DatabaseConnection
import org.acelerazg.repositories.db.PostgresConnection
import org.acelerazg.services.address.AddressService
import org.acelerazg.services.candidate.CandidateService
import org.acelerazg.services.skill.CandidateSkillService
import org.acelerazg.services.company.CompanyService
import org.acelerazg.services.skill.CompanySkillService
import org.acelerazg.services.job.JobService
import org.acelerazg.services.skill.JobSkillService
import org.acelerazg.services.skill.SkillService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.candidate.ICandidateService
import org.acelerazg.services.company.ICompanyService
import org.acelerazg.services.job.IJobService
import org.acelerazg.services.mappers.CandidateMapper
import org.acelerazg.services.mappers.CompanyMapper
import org.acelerazg.services.mappers.JobMapper

static void main(String[] args) {

    DatabaseConnection database = new PostgresConnection()

    AddressRepository addressRepository = new AddressRepository(database)
    CandidateRepository candidateRepository = new CandidateRepository(database)
    CompanyRepository companyRepository = new CompanyRepository(database)
    JobRepository jobRepository = new JobRepository(database)
    SkillRepository skillRepository = new SkillRepository(database)

    CandidateSkillService candidateSkillService = new CandidateSkillService(skillRepository)
    CompanySkillService companySkillService = new CompanySkillService(skillRepository)
    JobSkillService jobSkillService = new JobSkillService(skillRepository)

    CandidateMapper candidateMapper = new CandidateMapper()
    CompanyMapper companyMapper = new CompanyMapper()
    JobMapper jobMapper = new JobMapper()

    IAddressService addressService = new AddressService(addressRepository)
    SkillService skillService = new SkillService(skillRepository)
    ICandidateService candidateService = new CandidateService(candidateRepository, addressService, candidateSkillService, candidateMapper)
    ICompanyService companyService = new CompanyService(companyRepository, addressService, companySkillService, companyMapper)
    IJobService jobService = new JobService(jobRepository, addressService, jobSkillService, companyService, jobMapper)

    CandidateController candidateController = new CandidateController(candidateService)
    CompanyController companyController = new CompanyController(companyService)
    JobController jobController = new JobController(jobService)
    SkillController skillController = new SkillController(skillService)

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