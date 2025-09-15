import type { Empresa } from "../models/Empresa";

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
