package org.acelerazg.services.skill

import org.acelerazg.repositories.SkillRepository

class CompanySkillService extends BaseSkillService {

    CompanySkillService(SkillRepository skillRepository) {
        super(skillRepository)
    }

    @Override
    List<String> findSkills(String companyId) {
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
