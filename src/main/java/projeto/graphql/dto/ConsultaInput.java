package projeto.graphql.dto;

public class ConsultaInput {
    private String dataHora;
    private String pacienteId;
    private String medicoId;
    private String descricao;

    // Construtores
    public ConsultaInput() {}

    public ConsultaInput(String dataHora, String pacienteId, String medicoId, String descricao) {
        this.dataHora = dataHora;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
