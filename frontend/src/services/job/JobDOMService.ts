import type {Job} from "../../models/Job.ts";
import {calculateAffinityLevel} from "../candidate/CandidateMetric.ts";

export function readJobForm(): HTMLInputElement[] {
    const name = document.querySelector("#job-name") as HTMLInputElement;
    const description = document.querySelector("#description") as HTMLInputElement;
    const publicationDate = document.querySelector("[name='data-publicacao']") as HTMLInputElement;

    return [name, description, publicationDate];
}

export function assignValuesToInputs(inputs: HTMLInputElement[], newValues: string[]): void {
    for (let i = 0; i < inputs.length; i++) inputs[i].value = newValues[i];
}

export function renderJobsToCandidates(job: Job): string {
    const companySkills = job.company?.skills
    if (!companySkills) return "";
    const percentual : number | null = calculateAffinityLevel(companySkills)
    return `
            <div class="card shadow-sm p-3 mb-3 bg-body rounded" style="width: 19rem;">
              <div class="card-body">
                <h5 class="card-title">${job.name}</h5>
                <p class="card-text">${job.description}</p>
              </div>
              <div class="card-body">
                <h6>Competências requiridas:</h6>
                <p class="competencies-required">${job.skills.join(", ")}</p>
              </div>
               <div class="card-body">
               <h6>Nível de afinidade com a empresa: </h6>
                  <div class="progress" role="progressbar" aria-valuenow="${percentual}" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar" style="width: ${percentual}%">${percentual}%</div>
                   </div>
               </div>
              <div class="card-body" style="display: none;">
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
              </div>
            </div>
            `;
}

export function renderJobsToCompany(job: Job): string {
    return `
                <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 19rem;">
                  <div class="card-body">
                    <h5 class="card-title">${job.name}</h5>
                    <p class="card-text">${job.description}</p>
                  </div>
                  <div class="card-body">
                    <h6>Competências requiridas:</h6>
                    <p class="competencies-required">${job.skills}</p>
                  </div>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary editar-vaga" data-id="${job.id}">
                        <i class="fa fa-pencil"></i> Editar
                    </button>
                    <button class="btn btn-sm btn-outline-danger deletar-vaga" data-id="${job.id}">
                        <i class="fa fa-trash"></i> Deletar
                     </button>
                 </div>
                </div>
                `;
}