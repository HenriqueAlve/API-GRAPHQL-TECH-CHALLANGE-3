package projeto.graphql.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import projeto.graphql.dto.ConsultaGraphQL;
import projeto.graphql.dto.MedicoGraphQL;
import projeto.graphql.dto.PacienteGraphQL;
import projeto.graphql.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

@Controller
public class ConsultaQueryResolver {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080";

    public ConsultaQueryResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // -------------------- CONSULTAS --------------------

    @QueryMapping
    public List<ConsultaGraphQL> consultasPaciente(@Argument String pacienteId,
                                                   @Argument String token) {
        try {
            ConsultaGraphQL[] consultas = getWithToken("/consultas/paciente/{id}", ConsultaGraphQL[].class, token, pacienteId);
            if (consultas == null || consultas.length == 0) {
                throw new NotFoundException("Nenhuma consulta encontrada para o paciente " + pacienteId);
            }
            return Arrays.asList(consultas);
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar consultas do paciente: " + e.getMessage());
        }
    }

    @QueryMapping
    public List<ConsultaGraphQL> consultasMedico(@Argument String medicoId,
                                                 @Argument String token) {
        try {
            ConsultaGraphQL[] consultas = getWithToken("/consultas/medico/{id}", ConsultaGraphQL[].class, token, medicoId);
            if (consultas == null || consultas.length == 0) {
                throw new NotFoundException("Nenhuma consulta encontrada para o médico " + medicoId);
            }
            return Arrays.asList(consultas);
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar consultas do médico: " + e.getMessage());
        }
    }

    @QueryMapping
    public List<ConsultaGraphQL> consultasFuturas(@Argument String token) {
        try {
            ConsultaGraphQL[] consultas = getWithToken("/consultas/futuras", ConsultaGraphQL[].class, token);
            return consultas != null ? Arrays.asList(consultas) : List.of();
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar consultas futuras: " + e.getMessage());
        }
    }

    @QueryMapping
    public List<ConsultaGraphQL> todasConsultas(@Argument String token) {
        try {
            ConsultaGraphQL[] consultas = getWithToken("/consultas", ConsultaGraphQL[].class, token);
            return consultas != null ? Arrays.asList(consultas) : List.of();
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar todas as consultas: " + e.getMessage());
        }
    }

    @QueryMapping
    public ConsultaGraphQL consultaPorId(@Argument String id,
                                         @Argument String token) {
        try {
            ConsultaGraphQL consulta = getWithToken("/consultas/{id}", ConsultaGraphQL.class, token, id);
            if (consulta == null) {
                throw new NotFoundException("Consulta com ID " + id + " não encontrada");
            }
            return consulta;
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar consulta por ID: " + e.getMessage());
        }
    }

    @QueryMapping
    public List<ConsultaGraphQL> consultasPorPeriodo(@Argument String dataInicio,
                                                     @Argument String dataFim,
                                                     @Argument String token) {
        try {
            ConsultaGraphQL[] consultas = getWithToken(
                    "/consultas/periodo?inicio={inicio}&fim={fim}",
                    ConsultaGraphQL[].class,
                    token,
                    dataInicio, dataFim
            );
            return consultas != null ? Arrays.asList(consultas) : List.of();
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar consultas por período: " + e.getMessage());
        }
    }

    // -------------------- PACIENTES --------------------

    @QueryMapping
    public List<PacienteGraphQL> pacientes(@Argument String token) {
        try {
            PacienteGraphQL[] pacientes = getWithToken("/pacientes", PacienteGraphQL[].class, token);
            return pacientes != null ? Arrays.asList(pacientes) : List.of();
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar pacientes: " + e.getMessage());
        }
    }

    @QueryMapping
    public PacienteGraphQL pacientePorId(@Argument String id,
                                         @Argument String token) {
        try {
            PacienteGraphQL paciente = getWithToken("/pacientes/{id}", PacienteGraphQL.class, token, id);
            if (paciente == null) {
                throw new NotFoundException("Paciente com ID " + id + " não encontrado");
            }
            return paciente;
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar paciente: " + e.getMessage());
        }
    }

    // -------------------- MÉDICOS --------------------

    @QueryMapping
    public List<MedicoGraphQL> medicos(@Argument String token) {
        try {
            MedicoGraphQL[] medicos = getWithToken("/medicos", MedicoGraphQL[].class, token);
            return medicos != null ? Arrays.asList(medicos) : List.of();
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar médicos: " + e.getMessage());
        }
    }

    @QueryMapping
    public MedicoGraphQL medicoPorId(@Argument String id,
                                     @Argument String token) {
        try {
            MedicoGraphQL medico = getWithToken("/medicos/{id}", MedicoGraphQL.class, token, id);
            if (medico == null) {
                throw new NotFoundException("Médico com ID " + id + " não encontrado");
            }
            return medico;
        } catch (Exception e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao buscar médico: " + e.getMessage());
        }
    }

    // -------------------- MÉTODO AUXILIAR PARA CHAMAR API COM TOKEN --------------------

    private <T> T getWithToken(String url, Class<T> responseType, String token, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(
                baseUrl + url,
                HttpMethod.GET,
                entity,
                responseType,
                uriVariables
        );

        return response.getBody();
    }
}
