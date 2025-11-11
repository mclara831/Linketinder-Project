package org.acelerazg.services.skill

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository

class CandidateSkillService extends BaseSkillService {

    CandidateSkillService(SkillRepository skillRepository) {
        super(skillRepository)
    }

    @Override
    List<Skill> findSkills(String candidateId) {
        return skillRepository.findSkillsByCandidate(candidateId)
    }

    @Override
    void addSkillsToEntity(String candidateId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToCandidate(candidateId, skill.id)
        }
    }

    @Override
    void removeSkillsFromEntity(String candidateId) {
        skillRepository.removeSkillsFromCandidate(candidateId)
    }
}
