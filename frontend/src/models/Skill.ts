// Objeto como Enum
export const Skill = {
  Java: "Java",
  Angular: "Angular",
  Docker: "Docker",
  Javascript: "Javascript",
  DevOps: "DevOps",
  PHP: "PHP",
  Typescript: "Typescript",
  AWS: "AWS",
  Grails: "Grails",
  SpringFramework: "SpringFramework",
  React: "React",
  NodeJS: "Node.js",
  SQL: "SQL",
  Python: "Python",
  Git: "Git",
  Kubernetes: "Kubernetes",
  Terraform: "Terraform",
  Azure: "Azure",
  GCP: "Google Cloud Platform",
  CSharp: "C#",
  CPlusPlus: "C++",
  Ruby: "Ruby",
  Go: "Go",
  Swift: "Swift",
  Kotlin: "Kotlin",
  HTML: "HTML",
  CSS: "CSS",
  Sass: "Sass",
  TailwindCSS: "Tailwind CSS",
  NextJS: "Next.js",
  NestJS: "NestJS",
  Express: "Express",
  MongoDB: "MongoDB",
  PostgreSQL: "PostgreSQL",
  MySQL: "MySQL",
  Redis: "Redis",
  GraphQL: "GraphQL",
  Elasticsearch: "Elasticsearch",
  Jenkins: "Jenkins",
  GitLabCI: "GitLab CI",
  CI_CD: "CI/CD",
  TDD: "TDD",
  BDD: "BDD",
  DDD: "DDD",
  Scrum: "Scrum",
  Kanban: "Kanban",
  Linux: "Linux",
  ShellScript: "Shell Script"
} as const;

export type Skill = typeof Skill[keyof typeof Skill];

export function mapStringsToCompetencias(values: string[]): Skill[] {
  const competenciasValidas = Object.values(Skill);
  return values.filter((val): val is Skill =>
    competenciasValidas.includes(val as Skill)
  );
}

export interface quantifiedSkill {
  skill: Skill;
  quantity: number;
}
