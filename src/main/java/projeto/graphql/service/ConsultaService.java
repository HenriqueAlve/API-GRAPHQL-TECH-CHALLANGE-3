package projeto.graphql.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projeto.graphql.dto.ConsultaGraphQL;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080"; // API REST

    public ConsultaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ConsultaGraphQL> getConsultasPaciente(UUID pacienteId) {
        ConsultaGraphQL[] consultas = restTemplate.getForObject(
                baseUrl + "/consultas?pacienteId={id}",
                ConsultaGraphQL[].class,
                pacienteId
        );
        return Arrays.asList(consultas);
    }

    public List<ConsultaGraphQL> getConsultasFuturas() {
        ConsultaGraphQL[] consultas = restTemplate.getForObject(
                baseUrl + "/consultas/futuras",
                ConsultaGraphQL[].class
        );
        return Arrays.asList(consultas);
    }
}