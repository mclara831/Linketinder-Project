import type { Competencia } from "./Competencia"
import { Empresa } from "./Empresa"

export class Vaga {
    nome: string
    descricao: string
    empresa: Empresa | null
    data_publicacao: Date
    competencias: Competencia[] = new Array<Competencia>;

    constructor(nome: string, descricao: string, empresa: Empresa | null, data_publicacao: Date, competencias: Competencia[]) {
        this.nome = nome
        this.descricao = descricao
        this.empresa = empresa
        this.data_publicacao = data_publicacao
        this.competencias = competencias
    }

}
