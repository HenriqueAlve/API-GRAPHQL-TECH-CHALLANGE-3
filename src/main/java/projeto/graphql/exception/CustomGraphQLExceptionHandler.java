package projeto.graphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

@Component
public class CustomGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof NotFoundException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .extensions(Map.of(
                            "code", "NOT_FOUND",
                            "httpStatus", 404
                    ))
                    .build();
        }

        if (ex instanceof ResponseStatusException rse) {
            return GraphqlErrorBuilder.newError(env)
                    .message(rse.getReason() != null ? rse.getReason() : "Erro na API REST")
                    .extensions(Map.of(
                            "code", rse.getStatusCode().toString(),
                            "httpStatus", rse.getStatusCode().value()
                    ))
                    .build();
        }

        return GraphqlErrorBuilder.newError(env)
                .message("Erro interno do servidor")
                .extensions(Map.of(
                        "code", "INTERNAL_ERROR",
                        "httpStatus", 500
                ))
                .build();
    }
}