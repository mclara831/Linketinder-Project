package org.acelerazg.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.acelerazg.models.Candidate
import org.acelerazg.models.DTO.CandidateDTO
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

@WebServlet("/candidates")
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
            String cpf = req.getParameter("cpf")

            if (cpf) {
                Candidate candidate = candidateService.findByCpf(cpf)

                if (candidate != null) {
                    resp.setStatus(HttpServletResponse.SC_OK)
                    mapper.writeValue(resp.writer, candidate)
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
                    resp.writer.write("{\"message\": \"Candidate not found!\"}")
                }
            } else {

                List<CandidateDTO> candidates = candidateService.findAll()

                mapper.writeValue(resp.getWriter(), candidates)
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to find candidates!\n[ERROR]: ${e.getMessage()}")
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

            CandidateDTO newCandidate = mapper.readValue(body.toString(), CandidateDTO.class)
            CandidateDTO response = candidateService.create(newCandidate)

            resp.setStatus(HttpServletResponse.SC_CREATED)
            mapper.writeValue( resp.getWriter(), response)

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to create candidate!\n[ERROR]: ${e.getMessage()}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        mapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cpf = req.getParameter("cpf")

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            CandidateDTO newCandidate = mapper.readValue(body.toString(), CandidateDTO.class)

            CandidateDTO candidateDTO = candidateService.updateByCpf(cpf, newCandidate)

            resp.setStatus(HttpServletResponse.SC_OK)
            mapper.writeValue( resp.getWriter(), candidateDTO)
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
            String cpf = req.getParameter("cpf")
            candidateService.deleteByCpf(cpf)
            resp.setStatus(HttpServletResponse.SC_OK)
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to delete candidate!\n[ERROR]: ${e.getMessage()}")
        }
    }
}