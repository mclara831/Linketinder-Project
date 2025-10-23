import type {Skill} from "../../models/Skill.ts";
import {getLoggedEntity} from "../StorageService.ts";
import {Company} from "../../models/Company.ts";

export function calculateAffinityLevel(skills: Skill[]): number | null {
    let loggedCompany: Company | null = getLoggedEntity<Company>("empresaLogada")
    if (!loggedCompany) return 0

    let numberOfEquivalences : number = 0

    loggedCompany.skills.forEach(skill => {
        const found = skills.find((sk) => sk === skill)
        if (found) numberOfEquivalences++
    })

    const numberOfSkills: number = loggedCompany.skills.length
    const percentual =  (numberOfEquivalences / numberOfSkills * 100).toFixed(2)
    return parseFloat(percentual)
}