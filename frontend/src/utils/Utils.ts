import {Skill} from "../models/Skill.ts";

export function setText(selector: string, text?: string): void {
    const el = document.querySelector(selector);
    if (el) el.textContent = text ?? "";
}

export function loadSkillsFromObject(elementClass: string, skillsList: Skill[]) {
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

export function getSelectedSkills(): Skill[] {
    const inputs =
        document.querySelectorAll<HTMLInputElement>(".btn-check:checked");

    return Array.from(inputs).map((input) => input.value as Skill);
}

export function setSelectedSkills(skills: Skill[]): void {
    Object.values(Skill).forEach((skill, index) => {

        if (skills.includes(skill)) {
            let input = document.querySelector(`#competencia-${index}`) as HTMLInputElement
            input.checked = true

        }
    });
}

export function loadSkills() {
    const containers = document.querySelectorAll<HTMLDivElement>(".form-check");

    containers.forEach((container) => {
        Object.values(Skill).forEach((skill, index) => {
            const input = document.createElement("input");
            input.type = "checkbox";
            input.className = "btn-check";
            input.id = `competencia-${index}`;
            input.value = skill;
            input.autocomplete = "off";

            const label = document.createElement("label");
            label.className = "btn btn-outline-primary m-1";
            label.htmlFor = input.id;
            label.textContent = skill;

            container.appendChild(input);
            container.appendChild(label);
        });
    });
}
