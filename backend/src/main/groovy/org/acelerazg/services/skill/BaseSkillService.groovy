package org.acelerazg.services.skill

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository

abstract class BaseSkillService {

    protected SkillRepository skillRepository

    BaseSkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository
    }

    protected final void addSkills(String skillList, Closure<Void> function) {
        List<String> skills = skillList.split(", ")
        skills.forEach { name ->
            String formatted = formatSkill(name)
            Skill skill = skillRepository.findByName(formatted)

            if (!skill) {
                skill = new Skill(UUID.randomUUID().toString(), formatted)
                skillRepository.createFullSkill(skill.id, skill.name)
            }
            function.call(skill)
        }
    }

    protected final String formatSkill(String skill) {
        skill = skill.trim()
        return skill[0].toUpperCase() + skill.substring(1).toLowerCase()
    }

    abstract List<String> findSkills(String entityId)
    abstract void addSkillsToEntity(String entityId, String skills)
    abstract void removeSkillsFromEntity(String entityId)
}
