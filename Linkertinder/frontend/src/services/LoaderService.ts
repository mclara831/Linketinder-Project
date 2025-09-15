import { Competencia } from "../models/Competencia";
import { Vaga } from "../models/Vaga";
import { obterObjetos } from "./ArmazenamentoService";

export function carregarVagas() {
  var card_list = document.querySelector(".jobs") as HTMLDivElement;
  if (card_list) {
    card_list.innerHTML = "";
  }

  var vagas: Vaga[] = obterObjetos<Vaga>("vagas");

  vagas.forEach((vaga) => {
    var li: HTMLElement = document.createElement("li");
    li.innerHTML = `
            <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 20rem;">
              <div class="card-body">
                <h5 class="card-title">${vaga.nome}</h5>
                <p class="card-text">${vaga.descricao}</p>
              </div>
              <div class="card-body">
                <h6>Competências requiridas:</h6>
                <p class="competencies-required">${vaga.competencias}</p>
              </div>
              <div class="card-body" style="display: none;">
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
              </div>
            </div>
            `;
    card_list?.appendChild(li);
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
