import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  adicionaEventosAManipulacaoCandidatos,

} from "./services/CandidatoService";
import { carregarGraphic } from "./services/GraficoService";
import "./styles/style.css";
import {
  adicionaEventosCardVagas, carregarVagas
} from "./services/VagaService";
import { carregarCompetencias } from "./utils/Utils";
import { adicionaEventosAManipulacaoEmpresas } from "./services/EmpresaService";

adicionaEventosAManipulacaoCandidatos()
adicionaEventosAManipulacaoEmpresas();
carregarVagas();
carregarCompetencias();
carregarGraphic();
adicionaEventosCardVagas();

