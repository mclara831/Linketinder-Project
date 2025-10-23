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
              '#003f7d', // azul profundo
              '#004b99', // azul royal escuro
              '#005f73', // azul-petróleo
              '#00798c', // ciano escuro
              '#4a148c', // roxo escuro
              '#5a189a', // roxo-azulado
              '#006d3b', // verde escuro
              '#228b22', // verde-musgo
              '#8b0000', // vinho
              '#cc5500'  // laranja queimado
            ],
            borderColor: '#ffffff33',
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
