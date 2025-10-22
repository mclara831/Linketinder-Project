import { computeSkillsStats } from "../../services/candidate/CandidateMetric.ts";
import type { quantifiedSkill } from "../../models/Skill.ts";
import { Chart } from "chart.js/auto";
import type {IGraphicRenderer} from "../IGraphicRenderer.ts";

class ChartJsRenderer implements IGraphicRenderer{
  render(container: HTMLCanvasElement, data: quantifiedSkill[]): void {
    new Chart(container, {
      type: "bar",
      data: {
        labels: data.map((c) => c.skill),
        datasets: [
          {
            label: "Quantidade de Candidatos",
            data: data.map((c) => c.quantity),
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
}

export const renderGraphic = (renderer: IGraphicRenderer = new ChartJsRenderer()) => {
  const graphic = document.querySelector(".graphic-container") as HTMLCanvasElement;
  const skills = computeSkillsStats();

  renderer.render(graphic, skills);
};
