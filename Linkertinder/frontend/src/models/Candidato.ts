import type { Competencia } from "./Competencia"

export class Candidato {
    nome: string
    email: string
    idade: number
    cpf: string
    estado: string
    cep: string
    descricao: string
    competencias: Competencia[] = new Array<Competencia>

    constructor(nome: string, email: string,idade: number, cpf: string, estado: string, cep: string, descricao: string, competencias: Competencia[]) {
        this.nome = nome
        this.email = email
        this.idade = idade
        this.cpf = cpf
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias
    }

}
