import type { Competencia } from "./Competencia"
import { Company } from "./Company.ts"
import { v4 as uuidv4 } from "uuid"; 

export class Vaga {
    id: string
    nome: string
    descricao: string
    empresa: Company | null
    data_publicacao: Date
    competencias: Competencia[] = new Array<Competencia>;

    constructor(nome: string, descricao: string, empresa: Company | null, data_publicacao: Date, competencias: Competencia[]) {
        this.id = uuidv4()
        this.nome = nome
        this.descricao = descricao
        this.empresa = empresa
        this.data_publicacao = data_publicacao
        this.competencias = competencias
    }

}
