import {Candidate} from "../models/Candidate.ts";
import type {quantifiedSkill, Skill,} from "../models/Skill.ts";
import {getObjects, setObject} from "./StorageService.ts";
import {clearForm, clearLoginInput, readCandidateForm} from "./FormService";
import {fillCandidateProfile, renderCandidatesList} from "./DOMService/CandidateDOMService.ts";
import {isCPFValid, isInvalid, isValid} from "./ValidationService.ts";

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
        let candidate: Candidate | null = readCandidateForm();
        if (candidate === null) return

        setObject<Candidate>("candidatos", candidate);
        clearForm("#candidate-form");
        renderCandidates();
    });
}

function clearCandidateFormButton() {
    document.querySelector("#clear-form-candidate")?.addEventListener("click", function (event) {
        event.preventDefault();
        clearForm("#candidate-form");
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

export function setupCandidateLogin(): void {

    const submitBtn = document.querySelector("#login-btn-candidato") as HTMLButtonElement;
    const loginContainer = document.querySelector("#login-candidato") as HTMLDivElement;
    const candidateProfile = document.querySelector("#perfil-candidato") as HTMLDivElement;
    const cpf = document.querySelector("#inputCpf") as HTMLInputElement;

    submitBtn?.addEventListener("click", function (e) {
        e.preventDefault();
        const candidates: Candidate[] = getObjects<Candidate>("candidatos");
        let wasCandidateFound: boolean = false;

        isCPFValid(cpf.value) ? isValid(cpf) : isInvalid(cpf)

        candidates.forEach((candidate) => {
            if (candidate.cpf === cpf.value) {
                candidateProfile.style.display = "initial";
                loginContainer.style.display = "none";
                wasCandidateFound = true;
                showCandidateProfile(candidate);
            }
        });

        if (!wasCandidateFound) {
            isInvalid(cpf);
        }

        clearLoginInput("#inputCpf");
    });
}

export function showCandidateProfile(candidate: Candidate): void {
    fillCandidateProfile(candidate)
}

