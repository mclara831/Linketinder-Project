import {Competencia} from "../models/Competencia";

export function setText(selector: string, text?: string): void {
    const el = document.querySelector(selector);
    if (el) el.textContent = text ?? "";
}

export function loadSkillsFromObject(elementClass: string, skillsList: Competencia[]) {
    const skillsElement = document.querySelector(elementClass);
    if (!skillsElement) return

    skillsElement.innerHTML = "";
    skillsList.forEach((skill) => {
        const li = document.createElement("li");
        const div = document.createElement("div");
        div.classList.add("skill-container");
        div.textContent = skill;
        li.appendChild(div);
        skillsElement.appendChild(li);
    });
}

export function obterCompetenciasSelecionadas(): Competencia[] {
    const inputs =
        document.querySelectorAll<HTMLInputElement>(".btn-check:checked");

    return Array.from(inputs).map((input) => input.value as Competencia);
}

export function selecionarCompetencias(competencias: Competencia[]): void {
    Object.values(Competencia).forEach((competencia, index) => {

        if (competencias.includes(competencia)) {
            let input = document.querySelector(`#competencia-${index}`) as HTMLInputElement
            input.checked = true

        }
    });
}

export function carregarCompetencias() {
    const containers = document.querySelectorAll<HTMLDivElement>(".form-check");

    containers.forEach((container) => {
        Object.values(Competencia).forEach((competencia, index) => {
            const input = document.createElement("input");
            input.type = "checkbox";
            input.className = "btn-check";
            input.id = `competencia-${index}`;
            input.value = competencia;
            input.autocomplete = "off";

            const label = document.createElement("label");
            label.className = "btn btn-outline-primary m-1";
            label.htmlFor = input.id;
            label.textContent = competencia;

            container.appendChild(input);
            container.appendChild(label);
        });
    });
}
