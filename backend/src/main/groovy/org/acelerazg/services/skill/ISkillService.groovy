package org.acelerazg.services.skill

import org.acelerazg.models.Skill

interface ISkillService {
    List<Skill> findAll()
    Skill create(String skill)
    void registerAList(String skillsList)
    boolean existsSkill(String skill)
    Skill update(String existingSkill, String updatedSkill)
    void delete(String skill)
    String formatSkill(String skill)
}
