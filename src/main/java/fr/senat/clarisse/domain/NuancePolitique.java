package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NuancePolitique.
 */
@Entity
@Table(name = "nuance_politique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NuancePolitique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code_nuance_politique")
    private String codeNuancePolitique;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nuances", "matricules" }, allowSetters = true)
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NuancePolitique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeNuancePolitique() {
        return this.codeNuancePolitique;
    }

    public NuancePolitique codeNuancePolitique(String codeNuancePolitique) {
        this.setCodeNuancePolitique(codeNuancePolitique);
        return this;
    }

    public void setCodeNuancePolitique(String codeNuancePolitique) {
        this.codeNuancePolitique = codeNuancePolitique;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public NuancePolitique libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Personne getPersonne() {
        return this.personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public NuancePolitique personne(Personne personne) {
        this.setPersonne(personne);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NuancePolitique)) {
            return false;
        }
        return id != null && id.equals(((NuancePolitique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NuancePolitique{" +
            "id=" + getId() +
            ", codeNuancePolitique='" + getCodeNuancePolitique() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
