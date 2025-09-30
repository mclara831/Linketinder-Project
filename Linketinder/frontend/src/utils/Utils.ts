import { Competencia } from "../models/Competencia";

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
