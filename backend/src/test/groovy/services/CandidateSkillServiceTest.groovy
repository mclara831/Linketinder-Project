package services

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.services.skill.CandidateSkillService
import spock.lang.Specification

class CandidateSkillServiceTest extends Specification {

    def skillRepository = Mock(SkillRepository)
    def candidateSkillService = new CandidateSkillService(skillRepository)

    def "add skills to candidate"() {
        given:
        def candidateId = "123"
        def skillsString = "Java, Groovy"
        def javaSkill = new Skill(UUID.randomUUID().toString(), "Java")
        def groovySkill = new Skill(UUID.randomUUID().toString(), "Groovy")


        skillRepository.findByName("Java") >> null
        skillRepository.findByName("Groovy") >> null
        skillRepository.createFullSkill(_ as String, "Java") >> javaSkill
        skillRepository.createFullSkill(_ as String, "Groovy") >> groovySkill

        when:
        candidateSkillService.addSkillsToEntity(candidateId, skillsString)

        then:
        2 * skillRepository.addSkillsToCandidate(candidateId, _)
        1 * skillRepository.createFullSkill(_, "Java")
        1 * skillRepository.createFullSkill(_, "Groovy")
    }

    def "remove skills from candidate"() {
        given:
        def candidateId = "456"

        when:
        candidateSkillService.removeSkillsFromEntity(candidateId)

        then:
        1 * skillRepository.removeSkillsFromCandidate(candidateId)
    }
}

