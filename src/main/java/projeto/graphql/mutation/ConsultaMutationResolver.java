package projeto.graphql.mutation;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import projeto.graphql.dto.ConsultaGraphQL;
import projeto.graphql.dto.ConsultaInput;
import projeto.graphql.exception.NotFoundException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Controller
public class ConsultaMutationResolver {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080";

    public ConsultaMutationResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // -------------------- CRIAR --------------------
    @MutationMapping
    public ConsultaGraphQL criarConsulta(@Argument String dataHora,
                                         @Argument String pacienteId,
                                         @Argument String medicoId,
                                         @Argument String descricao,
                                         @Argument String token) {
        try {
            ConsultaInput input = new ConsultaInput(dataHora, pacienteId, medicoId, descricao);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<ConsultaInput> entity = new HttpEntity<>(input, headers);

            return restTemplate.postForObject(
                    baseUrl + "/consultas",
                    entity,
                    ConsultaGraphQL.class
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao criar consulta: " + e.getMessage()
            );
        }
    }

    // -------------------- ATUALIZAR --------------------
    @MutationMapping
    public ConsultaGraphQL atualizarConsulta(@Argument String id,
                                             @Argument String descricao,
                                             @Argument String token) {
        try {
            ConsultaInput input = new ConsultaInput();
            input.setDescricao(descricao);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<ConsultaInput> entity = new HttpEntity<>(input, headers);

            restTemplate.exchange(
                    baseUrl + "/consultas/{id}",
                    HttpMethod.PUT,
                    entity,
                    Void.class,
                    id
            );

            // Retornar a consulta atualizada
            ResponseEntity<ConsultaGraphQL> updated = restTemplate.exchange(
                    baseUrl + "/consultas/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    ConsultaGraphQL.class,
                    id
            );

            ConsultaGraphQL consulta = updated.getBody();
            if (consulta == null) {
                throw new NotFoundException("Consulta com ID " + id + " não encontrada após atualização");
            }

            return consulta;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Consulta com ID " + id + " não encontrada");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao atualizar consulta: " + e.getMessage()
            );
        }
    }

    // -------------------- DELETAR --------------------
    @MutationMapping
    public Boolean deletarConsulta(@Argument String id,
                                   @Argument String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                    baseUrl + "/consultas/{id}",
                    HttpMethod.DELETE,
                    entity,
                    Void.class,
                    id
            );

            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Consulta com ID " + id + " não encontrada para deleção");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_GATEWAY,
                    "Erro ao deletar consulta: " + e.getMessage()
            );
        }
    }
}
