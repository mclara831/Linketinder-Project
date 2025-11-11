package services

import org.acelerazg.models.DTO.skill.SkillRequestDTO
import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.services.skill.SkillService
import spock.lang.Specification

class SkillServiceTest extends Specification {

    SkillRepository skillRepository = Mock(SkillRepository)
    SkillService skillService = new SkillService(skillRepository)

    void "list all skills"() {
        given:
        Skill[] skills = [
                new Skill("Git"),
                new Skill("Java"),
                new Skill("SQL")
        ]
        skillRepository.findAll() >> skills

        when:
        List<Skill> result = skillService.findAll()

        then:
        result.size() == 3
        result[0].name == "Git"
    }

    void  "insert new skill"() {
        SkillRequestDTO skill = new SkillRequestDTO("Git")
        Skill expectedResult = new Skill("Git")

        skillRepository.findByName(_ as String) >> null
        skillRepository.createByName(_ as String) >> expectedResult

        when:
        Skill result = skillService.create(skill)

        then:
        result.name == expectedResult.name
    }

    def "update a skill"() {
        Skill existing = new Skill("Git")
        SkillRequestDTO updated = new SkillRequestDTO("Java")
        Skill expedtedResult = new Skill("Java")

        skillRepository.findByName(existing.name) >> existing
        skillRepository.findByName(updated.skills) >> null
        skillRepository.updateById(_ as Skill) >> expedtedResult

        when:
        Skill result = skillService.update(existing.name, updated)

        then:
        result.name == expedtedResult.name
    }

    def "delete a skill"() {
        Skill skill = new Skill("Git")

        skillRepository.findByName(_ as String) >> skill

        when:
        skillService.delete(skill.name)

        then:
        1 * skillRepository.deleteById(skill)
    }
}
