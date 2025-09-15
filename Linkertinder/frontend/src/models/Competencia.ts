export enum Competencia {
  Java = "Java",
  Angular = "Angular",
  Docker = "Docker",
  Javascript = "Javascript",
  DevOps = "DevOps",
  PHP = "PHP",
  Typescript = "Typescript",
  AWS = "AWS",
  Grails = "Grails",
  SpringFramework = "SpringFramework",
  React = "React",
  NodeJS = "Node.js",
  SQL = "SQL",
  Python = "Python",
  Git = "Git"
}

export function mapStringsToCompetencias(values: string[]): Competencia[] {
  return values
    .map((val) => {
      if (Object.values(Competencia).includes(val as Competencia)) {
        return val as Competencia;
      }
      return undefined; // caso venha uma string inválida
    })
    .filter((val): val is Competencia => val !== undefined);
}


export interface CompetenciaQuantificada {
  competencia: string;
  quantidade: number;
}
