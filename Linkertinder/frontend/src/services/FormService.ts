import { Candidato } from "../models/Candidato";
import type { Competencia } from "../models/Competencia";
import { Empresa } from "../models/Empresa";
import { obterCompetenciasSelecionadas } from "../utils/Utils";
import { obterObjetos } from "./ArmazenamentoService";
import { logarCandidato } from "./CandidatoService";
import { logarEmpresa } from "./EmpresaService";
import {
  invalido,
  validaCEP,
  validaCPF,
  validaEmail,
  validaLinkedin,
  validaTexto,
  valido
} from "./ValidacaoService";

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
        logarEmpresa(empresa);
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
        encontrado = true;
        logarCandidato(candidato);
      }
    });

    if (!encontrado) {
      alert("O CPF informado não está cadastrado em nossa base de dados!");
    }
    limparInputLogin("#inputCpf");
  });
}

export function lerInfoFomularioCandidato(): Candidato | null {
  const form = document.querySelector("#candidate-form") as HTMLInputElement;

  let nome = form.querySelector('[name="name"]') as HTMLInputElement;
  let email = form.querySelector('[name="email"]') as HTMLInputElement;
  let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
  let cpf = form.querySelector('[name="cpf"]') as HTMLInputElement;
  let data_nascimento = form.querySelector(
    '[name="data-nascimento"]'
  ) as HTMLInputElement;
  let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
  let descricao = form.querySelector(
    '[name="description"]'
  ) as HTMLInputElement;
  let competencias = obterCompetenciasSelecionadas();
  let estado = form.querySelector('[name="estado"]') as HTMLInputElement;

  var result = validaCamposCandidato(
    nome,
    email,
    linkedin,
    cpf,
    cep,
    descricao,
    estado,
    data_nascimento
  );

  if (!result) {
    console.log("aqui!!");
    return null;
  }

  return new Candidato(
    nome.value,
    email.value,
    new Date(data_nascimento.value),
    cpf.value,
    estado.value,
    cep.value,
    descricao.value,
    competencias
  );
}

function validaCamposCandidato(
  nome: HTMLInputElement,
  email: HTMLInputElement,
  linkedin: HTMLInputElement,
  cpf: HTMLInputElement,
  cep: HTMLInputElement,
  descricao: HTMLInputElement,
  estado: HTMLInputElement,
  data_nascimento: HTMLInputElement
): boolean {
  let flag: boolean = true;

  if (!validaTexto(nome.value)) {
    flag = false;
    invalido(nome);
  } else {
    valido(nome)
  }
  if (!validaEmail(email.value)) {
    flag = false;
    invalido(email);
  } else {
    valido(nome)
  }
  if (!validaLinkedin(linkedin.value)) {
    flag = false;
    invalido(linkedin);
  } else {
    valido(linkedin)
  }
  if (data_nascimento.value === '' ||
    new Date().getFullYear() - new Date(data_nascimento.value).getFullYear() <
      18
  ) {
    flag = false;
    invalido(data_nascimento);
  } else {
    valido(data_nascimento)
  }
  if (!validaTexto(descricao.value)) {
    flag = false;
    invalido(descricao);
  } else {
    valido(descricao)
  }
  if (!validaTexto(estado.value)) {
    flag = false;
    invalido(estado);
  } else {
    valido(estado)
  }
  if (!validaCPF(cpf.value)) {
    flag = false;
    invalido(cpf);
  } else {
    valido(cpf)
  }
  if (!validaCEP(cep.value)) {
    flag = false;
    invalido(cep);
  } else {
    valido(cep)
  }

  return flag;
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
  (form.querySelector('[name="linkedin"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cpf"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="estado"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="cep"]') as HTMLInputElement).value = "";
  (form.querySelector('[name="description"]') as HTMLTextAreaElement).value =
    "";
  (form.querySelector('[name="name"]') as HTMLInputElement).classList.remove(
    "is-invalid"
  );
  (form.querySelector('[name="email"]') as HTMLInputElement).classList.remove(
    "is-invalid"
  );
  (
    form.querySelector('[name="linkedin"]') as HTMLInputElement
  ).classList.remove("is-invalid");
  (form.querySelector('[name="cpf"]') as HTMLInputElement).classList.remove(
    "is-invalid"
  );
  (form.querySelector('[name="estado"]') as HTMLInputElement).classList.remove(
    "is-invalid"
  );
  (form.querySelector('[name="cep"]') as HTMLInputElement).classList.remove(
    "is-invalid"
  );
  (
    form.querySelector('[name="description"]') as HTMLTextAreaElement
  ).classList.remove("is-invalid");
  document
    .querySelectorAll<HTMLInputElement>(".btn-check")
    .forEach((e) => (e.checked = false));
}

function limparInputLogin(id: string) {
  (document.querySelector(id) as HTMLInputElement).value = "";
}

export function limparFormularioVaga(): void {
  const form = document.querySelector("#job-register") as HTMLFormElement;
  if (!form) return;

  form
    .querySelectorAll<HTMLInputElement | HTMLTextAreaElement>("input, textarea")
    .forEach((el) => {
      if (el.type === "checkbox" || el.type === "radio") {
        (el as HTMLInputElement).checked = false;
      } else {
        el.value = "";
      }
    });
}
