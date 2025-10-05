package projeto.graphql.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaGraphQL {
    private String id;
    private String dataHora;
    private String descricao;
    private PacienteGraphQL paciente;
    private MedicoGraphQL medico;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public PacienteGraphQL getPaciente() { return paciente; }
    public void setPaciente(PacienteGraphQL paciente) { this.paciente = paciente; }

    public MedicoGraphQL getMedico() { return medico; }
    public void setMedico(MedicoGraphQL medico) { this.medico = medico; }
}