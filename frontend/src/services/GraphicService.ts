import { computeSkillsStats } from "./CandidateService.ts";
import type { quantifiedSkill } from "../models/Skill.ts";
import { Chart } from "chart.js/auto";

export const renderGraphic = () => {
  const graphic = document.querySelector(
    ".graphic-container"
  ) as HTMLCanvasElement;

  const skills: quantifiedSkill[] = computeSkillsStats();

  return new Chart(graphic, {
    type: "bar",
    data: {
      labels: skills.map((c) => c.skill),
      datasets: [
        {
          label: "Quantidade de Candidatos",
          data: skills.map((c) => c.quantity),
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
