package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.Skill
import org.acelerazg.services.skill.SkillService

class SkillView {

    SkillService skillService

    SkillView(SkillService skillService) {
        this.skillService = skillService
    }

    void findAll() {
        List<Skill> skills = skillService.findAll()
        skills.forEach { it -> println(it.toString()) }
    }

    void create() {
        String skill = UI.readLine("Digite o nome da nova competência: ")

        Skill created = skillService.create(skill)
        println("[CREATED]: skill ${created}")
    }

    void registerAList() {
        String skills = UI.readSkills()
        skillService.registerAList(skills)
    }

    void update() {
        String existingSkill = UI.readLine("Digite o nome da competência que deseja atualizar: ")

        if (!skillService.existsSkill(existingSkill)) {
            println("[AVISO]: Esta competência não existe em nossa base de dados!")
            return
        }

        String updatedSkill = UI.readLine("Digite o novo nome da competência: ")
        updatedSkill = skillService.update(existingSkill, updatedSkill)
        println("[UPDATED]: skill ${updatedSkill}")
    }

    void delete() {
        String skill = UI.readLine("Digite o nome da competência que deseja deletar: ")

        if (!skillService.existsSkill(skill)) {
            println("[AVISO]: Esta competência não existe em nossa base de dados!")
            return
        }
        skillService.delete(skill)
    }
}
