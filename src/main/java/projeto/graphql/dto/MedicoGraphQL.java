package projeto.graphql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicoGraphQL {
    private String id;
    private String nome;
    private String crm;

    public MedicoGraphQL() {}
    public MedicoGraphQL(String id, String nome, String crm) {
        this.id = id; this.nome = nome; this.crm = crm;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
}