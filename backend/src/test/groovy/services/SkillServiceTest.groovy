package services

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.services.SkillService
import spock.lang.Specification

class SkillServiceTest extends Specification {

    def skillRepository = Mock(SkillRepository)
    def skillService = new SkillService(skillRepository)

    def "list all skills"() {
        given:
        def skills = [
                new Skill("Git"),
                new Skill("Java"),
                new Skill("SQL")
        ]
        skillRepository.findAll() >> skills

        when:
        def result = skillService.findAll()

        then:
        result.size() == 3
        result[0].name == "Git"
    }

    def "insert new skill"() {
        Skill skill = new Skill("Git")

        skillRepository.findByName(_ as String) >> null
        skillRepository.createByName(_ as String) >> skill

        when:
        Skill result = skillService.create(skill.name)

        then:
        result.name == skill.name
    }

    def "update a skill"() {
        Skill existing = new Skill("Git")
        Skill updated = new Skill("Java")

        skillRepository.findByName(existing.name) >> existing
        skillRepository.findByName(updated.name) >> null
        skillRepository.updateCompetenciaById(_ as Skill) >> updated

        when:
        Skill result = skillService.update(existing.name, updated.name)

        then:
        result.name == updated.name
    }

    def "delete a skill"() {
        Skill skill = new Skill("Git")

        skillRepository.findByName(_ as String) >> skill

        when:
        skillService.delete(skill.name)

        then:
        1 * skillRepository.deleteCompetenciaById(skill)
    }
}
