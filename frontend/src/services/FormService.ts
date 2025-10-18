import {Candidate} from "../models/Candidate.ts";
import {Company} from "../models/Company.ts";
import {obterCompetenciasSelecionadas} from "../utils/Utils";
import {getObjects} from "./ArmazenamentoService";
import {showCandidateProfile} from "./CandidateService.ts";
import {showCompanyProfile} from "./CompanyService.ts";
import {
    invalido, validaCEP, validaCNPJ, validaCPF, validaEmail, validaLinkedin, validaTelefone, validaTexto, valido,
} from "./ValidacaoService";

export function setupCompanyLogin(): void {
    const login_btn = document.querySelector("#login-btn-empresa") as HTMLButtonElement;
    const main_form = document.querySelector(".login-empresa") as HTMLDivElement;
    const main_profile = document.querySelector("#perfil-empresa") as HTMLDivElement;
    const cnpj = document.querySelector("#inputCnpj") as HTMLInputElement;

    login_btn?.addEventListener("click", function (e) {
        e.preventDefault();
        var empresa: Company[] = getObjects<Company>("empresas");
        var encontrado: boolean = false;

        console.log(empresa);

        if (!validaCNPJ(cnpj.value)) {
            invalido(cnpj);
        } else {
            valido(cnpj);
        }

        empresa.forEach((empresa) => {
            if (empresa.cnpj === cnpj.value) {
                main_profile.style.display = "initial";
                main_form.style.display = "none";
                encontrado = true;
                showCompanyProfile(empresa);
            }
        });

        if (!encontrado) {
            invalido(cnpj);
        }
        limparInputLogin("#inputCnpj");
    });
}

export function setupCandidateLogin(): void {
    const login_btn = document.querySelector("#login-btn-candidato") as HTMLButtonElement;
    const main_form = document.querySelector("#login-candidato") as HTMLDivElement;
    const main_profile = document.querySelector("#perfil-candidato") as HTMLDivElement;
    const cpf = document.querySelector("#inputCpf") as HTMLInputElement;

    login_btn?.addEventListener("click", function (e) {
        e.preventDefault();
        var candidatos: Candidate[] = getObjects<Candidate>("candidatos");
        var encontrado: boolean = false;

        if (!validaCPF(cpf.value)) {
            invalido(cpf);
        } else {
            valido(cpf);
        }

        candidatos.forEach((candidato) => {
            if (candidato.cpf === cpf.value) {
                main_profile.style.display = "initial";
                main_form.style.display = "none";
                encontrado = true;
                showCandidateProfile(candidato);
            }
        });

        if (!encontrado) {
            invalido(cpf);
        }
        limparInputLogin("#inputCpf");
    });
}

export function lerInfoFomularioCandidato(): Candidate | null {
    const form = document.querySelector("#candidate-form") as HTMLInputElement;

    let nome = form.querySelector('[name="name"]') as HTMLInputElement;
    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    let telefone = form.querySelector('[name="telefone"]') as HTMLInputElement;
    let cpf = form.querySelector('[name="cpf"]') as HTMLInputElement;
    let data_nascimento = form.querySelector('[name="data-nascimento"]') as HTMLInputElement;
    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    let descricao = form.querySelector('[name="description"]') as HTMLInputElement;
    let competencias = obterCompetenciasSelecionadas();
    let estado = form.querySelector('[name="estado"]') as HTMLInputElement;

    var result = validaCampos(nome, email, linkedin, telefone, cpf, null, cep, descricao, estado, data_nascimento, null);

    if (!result) {
        return null;
    }

    const [ano, mes, dia] = data_nascimento.value.split("-").map(Number);

    return new Candidate(nome.value, email.value, linkedin.value, telefone.value, new Date(ano, mes - 1, dia), cpf.value, estado.value, cep.value, descricao.value, competencias);
}

function validaCampos(nome: HTMLInputElement, email: HTMLInputElement, linkedin: HTMLInputElement, telefone: HTMLInputElement | null, cpf: HTMLInputElement | null, cnpj: HTMLInputElement | null, cep: HTMLInputElement, descricao: HTMLInputElement, estado: HTMLInputElement, data_nascimento: HTMLInputElement | null, pais: HTMLInputElement | null): boolean {
    let flag: boolean = true;

    if (!validaTexto(nome.value)) {
        flag = false;
        invalido(nome);
    } else {
        valido(nome);
    }
    if (!validaEmail(email.value)) {
        flag = false;
        invalido(email);
    } else {
        valido(email);
    }
    if (!validaLinkedin(linkedin.value)) {
        flag = false;
        invalido(linkedin);
    } else {
        valido(linkedin);
    }
    if (telefone != null && !validaTelefone(telefone.value)) {
        flag = false;
        invalido(telefone);
    } else if (telefone != null) {
        valido(telefone);
    }
    if (data_nascimento != null && (data_nascimento.value === "" || new Date().getFullYear() - new Date(data_nascimento.value).getFullYear() < 18)) {
        flag = false;
        invalido(data_nascimento);
    } else if (data_nascimento != null) {
        valido(data_nascimento);
    }
    if (!validaTexto(descricao.value)) {
        flag = false;
        invalido(descricao);
    } else {
        valido(descricao);
    }
    if (!validaTexto(estado.value)) {
        flag = false;
        invalido(estado);
    } else {
        valido(estado);
    }
    if (cpf != null && !validaCPF(cpf.value)) {
        flag = false;
        invalido(cpf);
    } else if (cpf != null) {
        valido(cpf);
    }
    if (cnpj != null && !validaCNPJ(cnpj.value)) {
        flag = false;
        invalido(cnpj);
    } else if (cnpj != null) {
        valido(cnpj);
    }
    if (!validaCEP(cep.value)) {
        flag = false;
        invalido(cep);
    } else {
        valido(cep);
    }
    if (pais != null && !validaTexto(pais.value)) {
        flag = false;
        invalido(pais);
    } else if (pais != null) {
        valido(pais);
    }

    return flag;
}

export function lerInfoFomularioEmpresa(): Company | null {
    const form = document.querySelector("#company-form") as HTMLInputElement;

    let nome = form.querySelector('[name="name"]') as HTMLInputElement;
    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    let cnpj = form.querySelector('[name="cnpj"]') as HTMLInputElement;
    let estado = form.querySelector('[name="estado"]') as HTMLInputElement;
    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    let pais = form.querySelector('[name="pais"]') as HTMLInputElement;
    let descricao = form.querySelector('[name="description"]') as HTMLInputElement;
    let competencias = obterCompetenciasSelecionadas();

    var resultado: boolean = validaCampos(nome, email, linkedin, null, null, cnpj, cep, descricao, estado, null, pais);
    if (!resultado) {
        return null;
    }

    return new Company(nome.value, email.value, linkedin.value, cnpj.value, estado.value, cep.value, pais.value, descricao.value, competencias);
}

export function clearCompanyForm() {
    const form = document.querySelector("#company-form") as HTMLInputElement;

    let name = form.querySelector('[name="name"]') as HTMLInputElement;
    name.value = "";
    name.classList.remove("is-invalid");

    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    email.value = "";
    email.classList.remove("is-invalid");

    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    linkedin.value = "";
    linkedin.classList.remove("is-invalid");

    let cnpj = form.querySelector('[name="cnpj"]') as HTMLInputElement;
    cnpj.value = "";
    cnpj.classList.remove("is-invalid");

    let estado = form.querySelector('[name="estado"]') as HTMLInputElement;
    estado.value = "";
    estado.classList.remove("is-invalid");

    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    cep.value = "";
    cep.classList.remove("is-invalid");

    let pais = form.querySelector('[name="pais"]') as HTMLInputElement;
    pais.value = "";
    pais.classList.remove("is-invalid");

    let descricao = form.querySelector('[name="description"]') as HTMLTextAreaElement;
    descricao.value = "";
    descricao.classList.remove("is-invalid");

    document
        .querySelectorAll<HTMLInputElement>(".btn-check")
        .forEach((e) => (e.checked = false));
}

export function clearCandidateForm() {
    const form = document.querySelector("#candidate-form") as HTMLInputElement;

    let nome = form.querySelector('[name="name"]') as HTMLInputElement;
    nome.value = "";
    nome.classList.remove("is-invalid");

    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    email.value = "";
    email.classList.remove("is-invalid");

    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    linkedin.value = "";
    linkedin.classList.remove("is-invalid");

    let telefone = form.querySelector('[name="telefone"]') as HTMLInputElement;
    telefone.value = "";
    telefone.classList.remove("is-invalid");

    let data_nascimento = form.querySelector('[name="data-nascimento"]') as HTMLInputElement;
    data_nascimento.value = "";
    data_nascimento.classList.remove("is-invalid");

    let cpf = form.querySelector('[name="cpf"]') as HTMLInputElement;
    cpf.value = "";
    cpf.classList.remove("is-invalid");

    let estado = form.querySelector('[name="estado"]') as HTMLInputElement;
    estado.value = "";
    estado.classList.remove("is-invalid");

    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    cep.value = "";
    cep.classList.remove("is-invalid");

    let description = form.querySelector('[name="description"]') as HTMLTextAreaElement;
    description.value = "";
    description.classList.remove("is-invalid");

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
