import { Candidato } from "../models/Candidato";
import type {
  Competencia,
  CompetenciaQuantificada,
} from "../models/Competencia";
import { obterObjetos, salvarObjeto } from "./ArmazenamentoService";
import { lerInfoFomularioCandidato, limparFormularioCandidato, loginCandidato } from "./FormService";

export function adicionaEventosAManipulacaoCandidatos() {
  carregarCandidatos()
  adicionarCandidato()
  limparFormularioCandidatoBtn()
  loginCandidato()
}

function adicionarCandidato(): void {
  
  document
    .querySelector("#cadastrar-candidato")
    ?.addEventListener("click", function (event) {
      event.preventDefault();
      var candidato = lerInfoFomularioCandidato();
      if (candidato === null) {
        return;
      }
  
      salvarObjeto<Candidato>("candidatos", candidato);
      limparFormularioCandidato();
      carregarCandidatos();
    });
}

function carregarCandidatos(): void {
  let lista = obterObjetos<Candidato>("candidatos");

  var card_list = document.querySelector(".candidates");
  if (card_list) {
    card_list.innerHTML = "";
  }

  lista.forEach((candidato) => {
    var li: HTMLElement = document.createElement("li");
    li.innerHTML = `
            <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 19rem;">
              <div class="card-body">
                <h5 class="card-title">Anônimo</h5>
                <p class="card-text">${candidato.descricao}</p>
              </div>
              <div class="card-body">
                <h6>Competências requiridas:</h6>
                <p class="competencies-required">${candidato.competencias}</p>
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

function limparFormularioCandidatoBtn() {
  document
  .querySelector("#clear-form-candidate")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    limparFormularioCandidato();
  });
}

export function carregarCompetencias(): CompetenciaQuantificada[] {
  let lista = obterObjetos<Candidato>("candidatos");
  const contador: Record<Competencia, number> = {} as Record<
    Competencia,
    number
  >;

  lista.forEach((candidato) => {
    candidato.competencias.forEach((comp) => {
      contador[comp] = (contador[comp] || 0) + 1;
    });
  });

  return Object.entries(contador).map(([competencia, quantidade]) => ({
    competencia: competencia as Competencia,
    quantidade,
  }));
}

export function logarCandidato(candidato: Candidato): void {
  const nameSpan = document.querySelector<HTMLSpanElement>(".candidate-name");
  const emailSpan = document.querySelector<HTMLSpanElement>(".candidate-email");
  const ageSpan = document.querySelector<HTMLSpanElement>(".candidate-age");
  const cpfSpan = document.querySelector<HTMLSpanElement>(".candidate-cnpj");
  const stateSpan = document.querySelector<HTMLSpanElement>(".candidate-state");
  const cepSpan = document.querySelector<HTMLSpanElement>(".candidate-cep");
  const descriptionSpan = document.querySelector<HTMLSpanElement>(".candidate-description");

  const skillsList = document.querySelector<HTMLUListElement>(".candidate-skills");

  if (nameSpan) nameSpan.textContent = candidato.nome;
  if (emailSpan) emailSpan.textContent = candidato.email;
  if (ageSpan) ageSpan.textContent = String(candidato.idade);
  if (cpfSpan) cpfSpan.textContent = candidato.cpf;
  if (stateSpan) stateSpan.textContent = candidato.estado;
  if (cepSpan) cepSpan.textContent = candidato.cep;
  if (descriptionSpan) descriptionSpan.textContent = candidato.descricao;

  if (skillsList) {
    skillsList.innerHTML = ""; 
    candidato.competencias.forEach(skill => {
      const li = document.createElement("li");
      const div = document.createElement("div");
      div.classList.add("skill-container");
      div.textContent = skill;
      li.appendChild(div);
      skillsList.appendChild(li);
    });
  }
}

