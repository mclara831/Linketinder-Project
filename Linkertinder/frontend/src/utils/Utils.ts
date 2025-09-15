import type { Competencia } from "../models/Competencia";

export function obterCompetenciasSelecionadas(): Competencia[] {
  const inputs = document.querySelectorAll<HTMLInputElement>(".btn-check:checked");

  return Array.from(inputs).map((input) => input.value as Competencia);
}