package services

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.services.skill.CompanySkillService
import spock.lang.Specification

class CompanySkillServiceTest extends Specification {

    def skillRepository = Mock(SkillRepository)
    def companySkillService = new CompanySkillService(skillRepository)

    def "add skills to candidate"() {
        given:
        def companyId = "123"
        def skillsString = "Java, Groovy"
        def javaSkill = new Skill(UUID.randomUUID().toString(), "Java")
        def groovySkill = new Skill(UUID.randomUUID().toString(), "Groovy")


        skillRepository.findByName("Java") >> null
        skillRepository.findByName("Groovy") >> null
        skillRepository.createFullSkill(_ as String, "Java") >> javaSkill
        skillRepository.createFullSkill(_ as String, "Groovy") >> groovySkill

        when:
        companySkillService.addSkillsToEntity(companyId, skillsString)

        then:
        2 * skillRepository.addSkillsToCompany(companyId, _)
        1 * skillRepository.createFullSkill(_, "Java")
        1 * skillRepository.createFullSkill(_, "Groovy")
    }

    def "remove skills from candidate"() {
        given:
        def companyId = "456"

        when:
        companySkillService.removeSkillsFromEntity(companyId)

        then:
        1 * skillRepository.removeSkillsFromCompany(companyId)
    }
}

