import type {quantifiedSkill} from "../models/Skill.ts";

export interface IGraphicRenderer {
    render(container: HTMLCanvasElement, data: quantifiedSkill[]): void;
}
