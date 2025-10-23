import type {Candidate} from "../../models/Candidate.ts";
import {loadSkillsFromObject, setText} from "../../utils/Utils.ts";
import {calculateAffinityLevel} from "../company/CompanyMetric.ts";

export function renderCandidatesList(candidates: Candidate[]): void {
    let card_list = document.querySelector(".candidates");
    if (card_list) {
        card_list.innerHTML = "";
    }

    candidates.forEach((candidate) => {
        const percentual: number | null = calculateAffinityLevel(candidate.skills)
        let card: HTMLElement = document.createElement("li");
        card.innerHTML = `
            <div class="card shadow-sm p-3 mb-3 bg-body rounded" style="width: 19rem;">
              <div class="card-body">
                <h5 class="card-title">Anônimo</h5>
                <p class="card-text">${candidate.description}</p>
              </div>
              <div class="card-body">
                <h6>Competências requiridas:</h6>
                <p class="competencies-required">${candidate.skills.join(", ")}</p>
              </div>
              <div class="card-body">
               <h6>Nível de afinidade: </h6>
                 <div class="progress" role="progressbar" aria-valuenow="${percentual}" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar" style="width: ${percentual}%;">${percentual}%</div>
                  </div>
             </div>
              <div class="card-body" style="display: none;">
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
              </div>
            </div>
            `;
        card_list?.appendChild(card);
    });
}

export function fillCandidateProfile(candidate: Candidate): void {

    setText(".candidate-name", candidate.name);
    setText(".candidate-email", candidate.email);
    setText(".candidate-linkedin", candidate.linkedin);
    setText(".candidate-phone", candidate.phone);
    setText(".candidate-born-date", new Date(candidate.date_of_birth).toLocaleDateString());
    setText(".candidate-cnpj", candidate.cpf);
    setText(".candidate-state", candidate.region);
    setText(".candidate-cep", candidate.cep);
    setText(".candidate-description", candidate.description);

    loadSkillsFromObject(".candidate-skills", candidate.skills);
}