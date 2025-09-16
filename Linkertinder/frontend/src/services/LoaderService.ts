import { Competencia } from "../models/Competencia";

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
