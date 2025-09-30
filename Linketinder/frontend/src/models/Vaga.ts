import type { Competencia } from "./Competencia"
import { Empresa } from "./Empresa"
import { v4 as uuidv4 } from "uuid"; 

export class Vaga {
    id: string
    nome: string
    descricao: string
    empresa: Empresa | null
    data_publicacao: Date
    competencias: Competencia[] = new Array<Competencia>;

    constructor(nome: string, descricao: string, empresa: Empresa | null, data_publicacao: Date, competencias: Competencia[]) {
        this.id = uuidv4()
        this.nome = nome
        this.descricao = descricao
        this.empresa = empresa
        this.data_publicacao = data_publicacao
        this.competencias = competencias
    }

}
