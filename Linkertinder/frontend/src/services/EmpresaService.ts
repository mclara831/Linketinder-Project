import type { Empresa } from "../models/Empresa";
import {
  definirEmpresaLogada,
  salvarObjeto,
} from "./ArmazenamentoService";
import {
  lerInfoFomularioEmpresa,
  limparFormularioEmpresa,
  loginEmpresa,
} from "./FormService";

export function adicionaEventosAManipulacaoEmpresas() {
  adicionarEmpresa();
  limparFormularioEmpresaBtn();
  loginEmpresa();
}

function adicionarEmpresa() {
  document
    .querySelector("#cadastrar-empresa")
    ?.addEventListener("click", function (event) {
      event.preventDefault();
      var empresa = lerInfoFomularioEmpresa();
      if (empresa == null) {
        return;
      }
      salvarObjeto<Empresa>("empresas", empresa);
    });
}

function limparFormularioEmpresaBtn() {
  document
    .querySelector("#clear-form-company")
    ?.addEventListener("click", function (event) {
      event.preventDefault();
      limparFormularioEmpresa();
    });
}

export function logarEmpresa(empresa: Empresa): void {
  const nameSpan = document.querySelector<HTMLSpanElement>(".company-name");
  const emailSpan = document.querySelector<HTMLSpanElement>(".company-email");
  const cnpjSpan = document.querySelector<HTMLSpanElement>(".company-cnpj");
  const stateSpan = document.querySelector<HTMLSpanElement>(".company-state");
  const cepSpan = document.querySelector<HTMLSpanElement>(".company-cep");
  const countrySpan =
    document.querySelector<HTMLSpanElement>(".company-country");
  const descriptionSpan = document.querySelector<HTMLSpanElement>(
    ".company-description"
  );

  const skillsList = document.querySelector<HTMLUListElement>(".skills-list");

  definirEmpresaLogada(empresa);
  console.log("empresa foi logada");

  if (nameSpan) nameSpan.textContent = empresa.nome;
  if (emailSpan) emailSpan.textContent = empresa.email;
  if (cnpjSpan) cnpjSpan.textContent = empresa.cnpj;
  if (stateSpan) stateSpan.textContent = empresa.estado;
  if (cepSpan) cepSpan.textContent = empresa.cep;
  if (countrySpan) countrySpan.textContent = empresa.pais;
  if (descriptionSpan) descriptionSpan.textContent = empresa.descricao;

  if (skillsList) {
    skillsList.innerHTML = "";
    empresa.competencias.forEach((skill) => {
      const li = document.createElement("li");
      const div = document.createElement("div");
      div.classList.add("skill-container");
      div.textContent = skill;
      li.appendChild(div);
      skillsList.appendChild(li);
    });
  }
}
