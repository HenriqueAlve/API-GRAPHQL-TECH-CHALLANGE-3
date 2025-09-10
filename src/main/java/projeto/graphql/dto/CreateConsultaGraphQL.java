package projeto.graphql.dto;

import java.util.UUID;

public record CreateConsultaGraphQL(
        String dataHora,
        UUID pacienteId,
        UUID medicoId,
        String descricao
) {
}
