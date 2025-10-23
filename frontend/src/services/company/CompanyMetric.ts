import type {Skill} from "../../models/Skill.ts";
import {getLoggedEntity} from "../StorageService.ts";
import {Company} from "../../models/Company.ts";
import {calculateAffinity} from "../AffinityCalculatorService.ts";

export function calculateAffinityLevel(skills: Skill[]): number | null {
    let loggedCompany: Company | null = getLoggedEntity<Company>("empresaLogada")
    if (!loggedCompany) return 0

    return calculateAffinity(loggedCompany.skills, skills);
}