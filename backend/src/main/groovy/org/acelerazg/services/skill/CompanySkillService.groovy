package org.acelerazg.services.skill

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository

class CompanySkillService extends BaseSkillService {

    CompanySkillService(SkillRepository skillRepository) {
        super(skillRepository)
    }

    @Override
    List<Skill> findSkills(String companyId) {
        return skillRepository.findSkillsByCompany(companyId)
    }

    @Override
    void addSkillsToEntity(String companyId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToCompany(companyId, skill.id)
        }
    }

    @Override
    void removeSkillsFromEntity(String companyId) {
        skillRepository.removeSkillsFromCompany(companyId)
    }
}
