import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {initializeCandidateModule,} from "./services/CandidateService.ts";
import {carregarGraphic} from "./services/GraficoService";
import "./styles/style.css";
import {adicionaEventosCardVagas, carregarVagas} from "./services/VagaService";
import {carregarCompetencias} from "./utils/Utils";
import {adicionaEventosAManipulacaoEmpresas} from "./services/EmpresaService";

initializeCandidateModule()
adicionaEventosAManipulacaoEmpresas();
carregarVagas();
carregarCompetencias();
carregarGraphic();
adicionaEventosCardVagas();

