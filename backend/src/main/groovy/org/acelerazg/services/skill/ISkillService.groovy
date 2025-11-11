package org.acelerazg.services.skill

import org.acelerazg.models.DTO.skill.SkillRequestDTO
import org.acelerazg.models.Skill

interface ISkillService {
    List<Skill> findAll()
    Skill create(SkillRequestDTO dto)
    void registerAList(String skillsList)
    boolean existsSkill(String skill)
    Skill update(String existingSkill, SkillRequestDTO dto)
    void delete(String skill)
    String formatSkill(String skill)
}
