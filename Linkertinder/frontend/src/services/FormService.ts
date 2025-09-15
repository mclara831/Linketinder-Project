import { Candidato } from "../models/Candidato";
import type { Competencia } from "../models/Competencia";
import { Empresa } from "../models/Empresa";
import { obterCompetenciasSelecionadas } from "../utils/Utils";
import { obterObjetos } from "./ArmazenamentoService";
import { logarCandidato } from "./CandidatoService";
import { logarEmpresa } from "./EmpresaService";

export function loginEmpresa(): void {
  const login_btn = document.querySelector(
    "#login-btn-empresa"
  ) as HTMLButtonElement;
  const main_form = document.querySelector(".login-empresa") as HTMLDivElement;
  const main_profile = document.querySelector(
    "#perfil-empresa"
  ) as HTMLDivElement;
  const cnpf = document.querySelector("#inputCnpj") as HTMLInputElement;

  login_btn?.addEventListener("click", function (e) {
    e.preventDefault();
    var empresa: Empresa[] = obterObjetos<Empresa>("empresas");
    var encontrado: boolean = false;

    empresa.forEach((empresa) => {
      if (empresa.cnpj === cnpf.value) {
        main_profile.style.display = "initial";
        main_form.style.display = "none";
        encontrado = true;
        logarEmpresa(empresa)
      }
    });

    if (!encontrado) {
      alert("O CNPJ informado não está cadastrado em nossa base de dados!");
    }
    limparInputLogin("#inputCnpj");
  });
}

export function loginCandidato(): void {
  const login_btn = document.querySelector(
    "#login-btn-candidato"
  ) as HTMLButtonElement;
  const main_form = document.querySelector(
    "#login-candidato"
  ) as HTMLDivElement;
  const main_profile = document.querySelector(
    "#perfil-candidato"
  ) as HTMLDivElement;
  const cpf = document.querySelector("#inputCpf") as HTMLInputElement;

  login_btn?.addEventListener("click", function (e) {
    e.preventDefault();
    var candidatos: Candidato[] = obterObjetos<Candidato>("candidatos");
    var encontrado: boolean = false;

    candidatos.forEach((candidato) => {
      if (candidato.cpf === cpf.value) {
        main_profile.style.display = "initial";
        main_form.style.display = "none";
        encontrado = true
        logarCandidato(candidato)
      }
    });

    if (!encontrado) {
      alert("O CNPJ informado não está cadastrado em nossa base de dados!");
    }
    limparInputLogin("#inputCpf");
  });
}
 
export function lerInfoFomularioCandidato(): Candidato | null {
  const form = document.querySelector("#candidate-form") as HTMLInputElement;

  let nome = form.querySelector('[name="name"]') as HTMLInputElement;
  let email = form.querySelector('[name="email"]') as HTMLInputElement;
  let cpf = form.querySelector('[name="cpf"]') as HTMLInputElement;
  let idade = form.querySelector('[name="idade"]') as HTMLInputElement;
  let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
  let descricao = form.querySelector(
    '[name="description"]'
  ) as HTMLInputElement;
  let competencias = obterCompetenciasSelecionadas();
  let estado = form.querySelector('[name="estado"]') as HTMLInputElement;

  if (
    !nome.value ||
    !email.value ||
    !cpf.value ||
    !idade.value ||
    !cep.value ||
    !descricao.value ||
    !estado.value
  ) {
    alert("Os campos obrigatórios não foram preenchidos!");
    return null;
  }

  return new Candidato(
    nome.value,
    email.value,
    Number.parseInt(idade.value),
    cpf.value,
    estado.value,
    cep.value,
    descricao.value,
    competencias
  );
}

export function lerInfoFomularioEmpresa(): Empresa | null {
  const form = document.querySelector("#company-form") as HTMLInputElement;

  let nome = form.querySelector('[name="name"]') as HTMLInputElement;
  let email = form.querySelector('[name="email"]') as HTMLInputElement;
  let cnpj = form.querySelector('[name="cnpj"]') as HTMLInputElement;
  let estado = form.querySelector('[name="estado"]') as HTMLInputElement;
  let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
  let pais = form.querySelector('[name="pais"]') as HTMLInputElement;
  let descricao = form.querySelector(
    '[name="description"]'
  ) as HTMLInputElement;
  let competencias = form.querySelector('[name="skills"]') as HTMLInputElement;

  if (
    !nome.value ||
    !email.value ||
    !cnpj.value ||
    !pais.value ||
    !cep.value ||
    !descricao.value ||
    !estado.value
  ) {
    alert("Os campos obrigatórios não foram preenchidos!");
    return null;
  }

  return new Empresa(
    nome.value,
    email.value,
    cnpj.value,
    estado.value,
    cep.value,
    pais.value,
    descricao.value,
    [competencias.value as Competencia]
  );
}

export function limparFormularioEmpresa() {
  const form = document.querySelector("#company-form") as HTMLInputElement;

  (form.querySelector('[name="name"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="email"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cnpj"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="estado"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cep"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="pais"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="description"]') as HTMLTextAreaElement).value =
    "";
  document
    .querySelectorAll<HTMLInputElement>(".btn-check")
    .forEach((e) => (e.checked = false));
}

export function limparFormularioCandidato() {
  const form = document.querySelector("#candidate-form") as HTMLInputElement;

  (form.querySelector('[name="name"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="email"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cpf"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="estado"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cep"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="description"]') as HTMLTextAreaElement).value =
    "";
  document
    .querySelectorAll<HTMLInputElement>(".btn-check")
    .forEach((e) => (e.checked = false));
}

function limparInputLogin(id: string) {
  (document.querySelector(id) as HTMLInputElement).value = "";
}
