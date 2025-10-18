import {Candidate} from "../models/Candidate.ts";
import type {Competencia, CompetenciaQuantificada,} from "../models/Competencia";
import {getObjects, setObject} from "./ArmazenamentoService";
import {clearCandidateForm, lerInfoFomularioCandidato, setupCandidateLogin} from "./FormService";
import {fillCandidateProfile, renderCandidatesList} from "./CandidateDOMService.ts";

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

export function computeSkillsStats(): CompetenciaQuantificada[] {
    let candidates: Candidate[] = getObjects<Candidate>("candidatos");
    const count: Record<Competencia, number> = {} as Record<Competencia, number>;

    candidates.forEach((candidates) => {
        candidates.skills.forEach((comp) => {
            count[comp] = (count[comp] || 0) + 1;
        });
    });

    return Object.entries(count).map(([skills, quantity]) => ({
        competencia: skills as Competencia, quantidade: quantity,
    }));
}

export function showCandidateProfile(candidate: Candidate): void {
    fillCandidateProfile(candidate)
}

