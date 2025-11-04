package services

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.services.skill.JobSkillService
import spock.lang.Specification

class JobSkillServiceTest extends Specification {

    def skillRepository = Mock(SkillRepository)
    def jobSkillService = new JobSkillService(skillRepository)

    def "add skills to job"() {
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
        jobSkillService.addSkillsToEntity(companyId, skillsString)

        then:
        2 * skillRepository.addSkillsToJob(companyId, _)
        1 * skillRepository.createFullSkill(_, "Java")
        1 * skillRepository.createFullSkill(_, "Groovy")
    }

    def "remove skills from job"() {
        given:
        def companyId = "456"

        when:
        jobSkillService.removeSkillsFromEntity(companyId)

        then:
        1 * skillRepository.removeSkillsFromJob(companyId)
    }
}

