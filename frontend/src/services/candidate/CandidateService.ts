import {Candidate} from "../../models/Candidate.ts";
import {getObjects, setLoggedEntity, setObject} from "../StorageService.ts";
import {readCandidateForm} from "../form/FormReader.ts";
import {clearLoginInput, clearForm} from "../form/FormCleaner.ts";
import {fillCandidateProfile, renderCandidatesList} from "./CandidateDOMService.ts";
import {isCPFValid, isInvalid, isValid} from "../form/ValidationService.ts";

export function initializeCandidateModule() {
    setupAddCandidatesBtn()
    clearCandidateFormButton()
    setupCandidateLogin()
}

export function renderCandidates(): void {
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
    setLoggedEntity<Candidate>("candidatoLogado", candidate)
    fillCandidateProfile(candidate)
}

