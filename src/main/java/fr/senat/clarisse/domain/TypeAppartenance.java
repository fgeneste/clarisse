package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeAppartenance.
 */
@Entity
@Table(name = "type_appartenance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeAppartenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type_appartenance")
    private String typeAppartenance;

    @Column(name = "libelle")
    private String libelle;

    @JsonIgnoreProperties(value = { "typeAppartenance", "structure", "personne" }, allowSetters = true)
    @OneToOne(mappedBy = "typeAppartenance")
    private Appartenance appartenance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeAppartenance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAppartenance() {
        return this.typeAppartenance;
    }

    public TypeAppartenance typeAppartenance(String typeAppartenance) {
        this.setTypeAppartenance(typeAppartenance);
        return this;
    }

    public void setTypeAppartenance(String typeAppartenance) {
        this.typeAppartenance = typeAppartenance;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeAppartenance libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Appartenance getAppartenance() {
        return this.appartenance;
    }

    public void setAppartenance(Appartenance appartenance) {
        if (this.appartenance != null) {
            this.appartenance.setTypeAppartenance(null);
        }
        if (appartenance != null) {
            appartenance.setTypeAppartenance(this);
        }
        this.appartenance = appartenance;
    }

    public TypeAppartenance appartenance(Appartenance appartenance) {
        this.setAppartenance(appartenance);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAppartenance)) {
            return false;
        }
        return id != null && id.equals(((TypeAppartenance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeAppartenance{" +
            "id=" + getId() +
            ", typeAppartenance='" + getTypeAppartenance() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
