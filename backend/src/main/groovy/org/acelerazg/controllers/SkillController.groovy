package org.acelerazg.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.acelerazg.models.DTO.skill.SkillRequestDTO
import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection
import org.acelerazg.repositories.db.connection.PostgresConnection
import org.acelerazg.repositories.db.factory.RepositoryFactory
import org.acelerazg.services.skill.SkillService

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/skills/*")
class SkillController extends HttpServlet {

    private SkillService skillService
    private ObjectMapper objectMapper

    @Override
    void init() throws ServletException {
        DatabaseConnection database = PostgresConnection.getInstance()

        RepositoryFactory factory = new RepositoryFactory(database)
        SkillRepository skillRepository = factory.createSkillRepository()

        skillService = new SkillService(skillRepository)

        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT)


            List<Skill> skills = skillService.findAll()
            objectMapper.writeValue(resp.getWriter(), skills)

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to find skills!", e)
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

            SkillRequestDTO newSkill = objectMapper.readValue(body.toString(), SkillRequestDTO.class)
            Skill response = skillService.create(newSkill)

            resp.setStatus(HttpServletResponse.SC_CREATED)
            objectMapper.writeValue(resp.getWriter(), response)

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to create skill!", e)
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

            String name = req.getPathInfo()

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            SkillRequestDTO newSkill = objectMapper.readValue(body.toString(), SkillRequestDTO.class)
            Skill skillDTO = skillService.update(name.substring(1), newSkill)

            resp.setStatus(HttpServletResponse.SC_OK)
            objectMapper.writeValue(resp.getWriter(), skillDTO)
        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to update skill!", e)
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

            String name = req.getPathInfo()

            skillService.delete(name.substring(1))
            resp.setStatus(HttpServletResponse.SC_OK)
        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to delete skill!", e)
        }
    }

    private void sendError(HttpServletResponse resp, int statusCode, String message, Exception e) throws IOException {
        resp.setStatus(statusCode)
        objectMapper.writeValue(resp.getWriter(), Map.of(
                "error", message,
                "details", e.getMessage()
        ))
    }
}
