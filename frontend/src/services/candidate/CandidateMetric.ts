import type {quantifiedSkill, Skill} from "../../models/Skill.ts";
import type {Candidate} from "../../models/Candidate.ts";
import {getObjects} from "../StorageService.ts";

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
