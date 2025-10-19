import {Candidate} from "../models/Candidate.ts";
import type {Skill, quantifiedSkill,} from "../models/Skill.ts";
import {getObjects, setObject} from "./StorageService.ts";
import {clearCandidateForm, lerInfoFomularioCandidato, setupCandidateLogin} from "./FormService";
import {fillCandidateProfile, renderCandidatesList} from "./DOMService/CandidateDOMService.ts";

export function initializeCandidateModule() {
    renderCandidates()
    setupAddCandidatesBtn()
    clearCandidateFormButton()
    setupCandidateLogin()
}

function renderCandidates(): void {
    let candidates = getObjects<Candidate>("candidatos");
    renderCandidatesList(candidates)
}

function setupAddCandidatesBtn(): void {
    document.querySelector("#cadastrar-candidato")?.addEventListener("click", function (event) {
        event.preventDefault();
        let candidate: Candidate | null = lerInfoFomularioCandidato();
        if (candidate === null) return

        setObject<Candidate>("candidatos", candidate);
        clearCandidateForm();
        renderCandidates();
    });
}

function clearCandidateFormButton() {
    document.querySelector("#clear-form-candidate")?.addEventListener("click", function (event) {
        event.preventDefault();
        clearCandidateForm();
    });
}

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

export function showCandidateProfile(candidate: Candidate): void {
    fillCandidateProfile(candidate)
}

