import type {Skill} from "../../models/Skill.ts";
import {Job} from "../../models/Job.ts";
import {getSelectedSkills, setSelectedSkills,} from "../../utils/Utils.ts";
import {
    getJobInEdition, getLoggedCompany, getObjects, setJobInEdition, setObject, setObjects,
} from "../StorageService.ts";
import {clearForm} from "../form/FormCleaner.ts";
import {isBiggerTextValid, isInvalid, isValid} from "../form/ValidationService.ts";
import {
    assignValuesToInputs, readJobForm, renderJobsToCandidates, renderJobsToCompany
} from "./JobDOMService.ts";

export function initializeJobModule() {
    setupAddJobBtn();
    renderJobsByCompany();
}

export function renderJobs() {
    let jobContainer = document.querySelector(".jobs") as HTMLDivElement;
    if (!jobContainer) return

    jobContainer.innerHTML = "";

    let jobs: Job[] = getObjects<Job>("vagas");
    console.log(jobs);

    jobs.forEach((job) => {
        let li: HTMLElement = document.createElement("li");
        li.innerHTML = renderJobsToCandidates(job)
        jobContainer.appendChild(li);
    });
}

function setupAddJobBtn(): void {

    const registerButton = document.querySelector("#cadastrar-vaga") as HTMLButtonElement;
    console.log(registerButton);

    registerButton.onclick = () => {
        const loggedCompany = getLoggedCompany();

        let inputs = readJobForm()
        if (inputs == null) {
        }
        const skills: Skill[] = getSelectedSkills();

        let valid: boolean = validateJobInputs(inputs);
        if (!valid) return

        const job: Job = new Job(inputs[0].value, inputs[1].value, loggedCompany, new Date(inputs[2].value), skills);
        setObject<Job>("vagas", job);
        clearForm("#job-register")
        renderJobsByCompany();
    };
}

function setupUpdateEvent(): void {
    const updateButtons = document.querySelectorAll<HTMLButtonElement>(".editar-vaga");

    const jobs = getObjects<Job>("vagas");

    updateButtons.forEach((job) => {
        job.addEventListener("click", function (event) {
            event.preventDefault();
            const j: Job | undefined = jobs.find((j) => j.id == job.dataset.id);
            if (j) {
                setJobInEdition(j);
            }
            updateJob();
        });
    });
}

function updateJob(): void {
    const jobInEdition = getJobInEdition();

    const collapse = document.querySelector<HTMLDivElement>("#collapseForm")!;
    let inputs = readJobForm()
    let submitBtn = document.querySelector("#cadastrar-vaga") as HTMLButtonElement;
    const btnsContainer = document.querySelector(".btns") as HTMLDivElement;

    collapse.className = "collapse show";
    let date = jobInEdition.publication_date ? new Date(jobInEdition.publication_date).toISOString().split("T")[0] : "";

    assignValuesToInputs(inputs, [jobInEdition.name, jobInEdition.description, date])
    setSelectedSkills(jobInEdition.skills)

    submitBtn.textContent = "Salvar alterações";

    const backBtn = document.createElement("button");
    if (!document.querySelector("#btn-voltar")) {
        backBtn.id = "btn-voltar";
        backBtn.className = "btn btn-secondary ms-2";
        backBtn.textContent = "Voltar";

        backBtn.addEventListener("click", () => {
            assignValuesToInputs(inputs, ["", "", ""])

            submitBtn.textContent = "Cadastrar";
            submitBtn.onclick = setupAddJobBtn;
            backBtn.remove();
            clearForm("#job-register")
        });

        btnsContainer.appendChild(backBtn);
    }

    submitBtn.onclick = () => {

        const jobs: Job[] = getObjects("vagas");
        let valid: boolean = validateJobInputs(inputs)

        if (!valid) return;

        const jobIndex = jobs.findIndex((v) => v.id === jobInEdition.id);
        if (jobIndex === -1) return;

        const updatedJob = {
            ...jobs[jobIndex],
            name: inputs[0].value,
            description: inputs[1].value,
            publication_date: new Date(inputs[2].value),
            skills: getSelectedSkills(),
        };

        jobs[jobIndex] = updatedJob;
        setObjects<Job>("vagas", jobs);
        resetJobForm(submitBtn, document.querySelector("#btn-voltar")!);
        renderJobsByCompany();
        clearForm("#job-register")
    };
}

function resetJobForm(submitBtn: HTMLButtonElement, backBtn: HTMLButtonElement) {
    submitBtn.textContent = "Cadastrar";
    submitBtn.onclick = setupAddJobBtn;
    backBtn.remove();
    clearForm("#job-register")
}

function setupDeleteEvent() {
    const deleteBtns = document.querySelectorAll<HTMLButtonElement>(".deletar-vaga");

    const jobs = getObjects<Job>("vagas");

    deleteBtns.forEach((btn) => {
        btn.addEventListener("click", function (event) {
            event.preventDefault();
            const j: Job | undefined = jobs.find((j) => j.id == btn.dataset.id);
            if (j) {
                setJobInEdition(j);
            }
            deleteJob();
        });
    });
}


function deleteJob(): void {
    const job = getJobInEdition();

    let jobs: Job[] = getObjects("vagas");
    jobs = jobs.filter((j) => j.id !== job.id);
    setObjects<Job>("vagas", jobs);

    renderJobsByCompany();
}

export function renderJobsByCompany(): void {
    const loggedCompany = getLoggedCompany();
    var card_list = document.querySelector(".jobs-company") as HTMLDivElement;
    if (card_list) {
        card_list.innerHTML = "";
    }

    var jobs: Job[] = getObjects<Job>("vagas");

    jobs.forEach((job) => {
        if (job.company?.cnpj == loggedCompany?.cnpj) {
            var li: HTMLElement = document.createElement("li");
            li.innerHTML = renderJobsToCompany(job)
            card_list?.appendChild(li);
        }
    });
    setupDeleteEvent();
    setupUpdateEvent();
}

function validateJobInputs(inputs: HTMLInputElement[]): boolean {
    let valid: boolean = true;

    for (let i = 0; i < inputs.length - 1; i++) {
        if (!isBiggerTextValid(inputs[i].value)) {
            isInvalid(inputs[i]);
            valid = false;
        } else {
            isValid(inputs[i]);
        }
    }

    if (!inputs[2].value) {
        isInvalid(inputs[2]);
        valid = false;
    } else {
        isValid(inputs[2]);
    }

    return valid;
}