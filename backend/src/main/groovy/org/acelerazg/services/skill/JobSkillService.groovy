package org.acelerazg.services.skill

import org.acelerazg.repositories.SkillRepository

class JobSkillService extends BaseSkillService {

    JobSkillService(SkillRepository skillRepository) {
        super(skillRepository)
    }

    @Override
    List<String> findSkills(String jobId) {
        return skillRepository.findSkillsByJob(jobId)
    }

    @Override
    void addSkillsToEntity(String jobId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToJob(jobId, skill.id)
        }
    }

    @Override
    void removeSkillsFromEntity(String jobId) {
        skillRepository.removeSkillsFromJob(jobId)
    }
}
