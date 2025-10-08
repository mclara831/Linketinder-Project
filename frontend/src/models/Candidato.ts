import type { Competencia } from "./Competencia"

export class Candidato {
    nome: string
    email: string
    linkedin: string
    telefone: string
    data_nascimento: Date
    cpf: string
    estado: string
    cep: string
    descricao: string
    competencias: Competencia[] = new Array<Competencia>

    constructor(nome: string, email: string, linkedin: string, telefone: string, data_nascimento: Date, cpf: string, estado: string, cep: string, descricao: string, competencias: Competencia[]) {
        this.nome = nome
        this.email = email
        this.linkedin = linkedin
        this.telefone = telefone
        this.data_nascimento = data_nascimento
        this.cpf = cpf
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias
    }

}
