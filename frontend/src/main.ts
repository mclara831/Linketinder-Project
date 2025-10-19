import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {initializeCandidateModule,} from "./services/CandidateService.ts";
import {carregarGraphic} from "./services/GraphicService.ts";
import "./styles/style.css";
import {initializeJobModule, renderJobs} from "./services/JobService.ts";
import {carregarCompetencias} from "./utils/Utils";
import {initializeCompanyModule} from "./services/CompanyService.ts";

initializeCandidateModule()
initializeCompanyModule();
renderJobs()
initializeJobModule();
carregarCompetencias();
carregarGraphic();
initializeCompanyModule();

