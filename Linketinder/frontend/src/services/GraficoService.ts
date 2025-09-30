import { carregarCompetencias } from "./CandidatoService";
import type { CompetenciaQuantificada } from "../models/Competencia";
import { Chart } from "chart.js/auto";

export const carregarGraphic = () => {
  const graphic = document.querySelector(
    ".graphic-container"
  ) as HTMLCanvasElement;

  const competencias: CompetenciaQuantificada[] = carregarCompetencias();

  return new Chart(graphic, {
    type: "bar",
    data: {
      labels: competencias.map((c) => c.competencia),
      datasets: [
        {
          label: "Quantidade de Candidatos",
          data: competencias.map((c) => c.quantidade), 
          backgroundColor: [
            "#ff6384",
            "#36a2eb",
            "#ffcd56",
            "#4bc0c0",
            "#9966ff",
            "#ff9f40",
            "#66cc99",
            "#ff6666",
            "#3399ff",
            "#cc66ff",
          ],
          borderWidth: 1,
        },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: "top",
        },
        title: {
          display: true,
          text: "Número de candidatos por competência",
        },
      },
    },
  });
};
