import type {Skill} from "../models/Skill.ts";

export function calculateAffinity(referenceSkills: Skill[], comparisonSkills: Skill[]): number {
    if (referenceSkills.length === 0 || comparisonSkills.length === 0) return 0;

    let matches = 0;
    referenceSkills.forEach(skill => {
        const found = comparisonSkills.find(sk => sk.toLowerCase() === skill.toLowerCase());
        if (found) matches++;
    });

    const percentual = (matches / referenceSkills.length) * 100;
    return parseFloat(percentual.toFixed(2));
}
