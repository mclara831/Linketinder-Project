package org.acelerazg.views

import org.acelerazg.cli.UI
import org.acelerazg.models.DTO.skill.SkillRequestDTO
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
        try {
            String skill = UI.readLine("Digite o nome da nova competência: ")

            Skill created = skillService.create(new SkillRequestDTO(skill))
            println("[CREATED]: skill ${created}")
        } catch (Exception e) {
            println(e.getMessage())
        }
    }

    void registerAList() {
        String skills = UI.readSkills()
        skillService.registerAList(skills)
    }

    void update() {
        try {
            String existingSkill = UI.readLine("Digite o nome da competência que deseja atualizar: ")

            if (!skillService.existsSkill(existingSkill)) {
                println("[AVISO]: Esta competência não existe em nossa base de dados!")
                return
            }

            String updatedSkill = UI.readLine("Digite o novo nome da competência: ")
            updatedSkill = skillService.update(existingSkill, new SkillRequestDTO(updatedSkill))
            println("[UPDATED]: skill ${updatedSkill}")
        } catch (Exception e) {
            println(e.getMessage())
        }
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
