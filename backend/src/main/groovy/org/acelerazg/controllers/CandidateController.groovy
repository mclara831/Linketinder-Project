package org.acelerazg.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.candidate.CandidateRequestDTO
import org.acelerazg.models.DTO.candidate.CandidateResponseDTO
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CandidateRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection
import org.acelerazg.repositories.db.connection.PostgresConnection
import org.acelerazg.repositories.db.factory.RepositoryFactory
import org.acelerazg.services.address.AddressService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.candidate.CandidateService
import org.acelerazg.services.mappers.CandidateMapper
import org.acelerazg.services.skill.CandidateSkillService

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/candidates/*")
class CandidateController extends HttpServlet {

    private CandidateService candidateService
    private ObjectMapper mapper
    private CandidateMapper candidateMapper

    @Override
    void init() throws ServletException {
        try {
            DatabaseConnection database = PostgresConnection.getInstance()

            RepositoryFactory factory = new RepositoryFactory(database)

            AddressRepository addressRepository = factory.createAddressRepository()
            CandidateRepository candidateRepository = factory.createCandidateRepository()
            SkillRepository skillRepository = factory.createSkillRepository()

            CandidateSkillService candidateSkillService = new CandidateSkillService(skillRepository)
            IAddressService addressService = new AddressService(addressRepository)
            candidateMapper = new CandidateMapper()

            candidateService = new CandidateService(
                    candidateRepository,
                    addressService,
                    candidateSkillService,
                    candidateMapper
            )

            mapper = new ObjectMapper()
            mapper.registerModule(new JavaTimeModule())

        } catch (Exception e) {
            e.printStackTrace()
            throw new ServletException("Erro ao inicializar o CandidateController", e)
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        mapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cpf = req.getPathInfo()

            if (cpf) {
                Candidate candidate = candidateService.findByCpf(cpf.substring(1))
                resp.setStatus(HttpServletResponse.SC_OK)
                mapper.writeValue(resp.getWriter(), candidate)

            } else {
                List<CandidateResponseDTO> candidates = candidateService.findAll()
                mapper.writeValue(resp.getWriter(), candidates)
            }

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "NOT FOUND", e)
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            CandidateRequestDTO newCandidate = mapper.readValue(body.toString(), CandidateRequestDTO.class)
            CandidateResponseDTO response = candidateService.create(newCandidate)

            resp.setStatus(HttpServletResponse.SC_CREATED)
            mapper.writeValue(resp.getWriter(), response)

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to create candidate", e)
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        mapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cpf = req.getPathInfo().substring(1)

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            CandidateRequestDTO newCandidate = mapper.readValue(body.toString(), CandidateRequestDTO.class)
            CandidateResponseDTO candidateDTO = candidateService.updateByCpf(cpf, newCandidate)

            resp.setStatus(HttpServletResponse.SC_OK)
            mapper.writeValue(resp.getWriter(), candidateDTO)
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to update candidate!\n[ERROR]: ${e.getMessage()}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        mapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cpf = req.getPathInfo().substring(1)
            candidateService.deleteByCpf(cpf)
            resp.setStatus(HttpServletResponse.SC_OK)
        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to delete candidate", e)
        }
    }

    private void sendError(HttpServletResponse resp, int statusCode, String message, Exception e) throws IOException {
        resp.setStatus(statusCode)
        mapper.writeValue(resp.getWriter(), Map.of(
                "error", message,
                "details", e.getMessage()
        ))
    }
}