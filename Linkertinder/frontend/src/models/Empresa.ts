import type { Competencia } from "./Competencia"

export class Empresa {
    nome: string
    email: string
    cnpj: string
    estado: string
    cep: string
    pais: string
    descricao: string
    competencias: Competencia[] = new Array<Competencia>
    

    constructor(nome: string, email: string, cnpj: string, estado: string, cep: string, pais: string, descricao: string, competencias: Competencia[]) {
        this.nome = nome
        this.email = email
        this.cnpj = cnpj
        this.estado = estado
        this.cep = cep
        this.pais = pais
        this.descricao = descricao
        this.competencias = competencias
    }

}
