import type {quantifiedSkill, Skill} from "../../models/Skill.ts";
import type {Candidate} from "../../models/Candidate.ts";
import {getLoggedEntity, getObjects} from "../StorageService.ts";
import {calculateAffinity} from "../AffinityCalculatorService.ts";

export function computeSkillsStats(): quantifiedSkill[] {
    let candidates: Candidate[] = getObjects<Candidate>("candidatos");
    const count: Record<Skill, number> = {} as Record<Skill, number>;

    candidates.forEach((candidates) => {
        candidates.skills.forEach((comp) => {
            count[comp] = (count[comp] || 0) + 1;
        });
    });

    return Object.entries(count).map(([skills, quantity]) => ({
        skill: skills as Skill, quantity: quantity,
    }));
}

export function calculateAffinityLevel(skills: Skill[]): number | null {
    let loggedCandidate: Candidate | null = getLoggedEntity<Candidate>("candidatoLogado");
    if (!loggedCandidate) return 0

    return calculateAffinity(loggedCandidate.skills, skills);
}