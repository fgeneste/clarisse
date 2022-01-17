package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeStructure.
 */
@Entity
@Table(name = "type_structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code_type_structure")
    private String codeTypeStructure;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "libelle_court")
    private String libelleCourt;

    @Column(name = "libelle_pluriel")
    private String libellePluriel;

    @Column(name = "url_complete")
    private String urlComplete;

    @Column(name = "url_simplifie")
    private String urlSimplifie;

    @Column(name = "ordre")
    private Long ordre;

    @JsonIgnoreProperties(value = { "typeStructure", "appartenances" }, allowSetters = true)
    @OneToOne(mappedBy = "typeStructure")
    private Structure structure;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeStructure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeStructure() {
        return this.codeTypeStructure;
    }

    public TypeStructure codeTypeStructure(String codeTypeStructure) {
        this.setCodeTypeStructure(codeTypeStructure);
        return this;
    }

    public void setCodeTypeStructure(String codeTypeStructure) {
        this.codeTypeStructure = codeTypeStructure;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeStructure libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleCourt() {
        return this.libelleCourt;
    }

    public TypeStructure libelleCourt(String libelleCourt) {
        this.setLibelleCourt(libelleCourt);
        return this;
    }

    public void setLibelleCourt(String libelleCourt) {
        this.libelleCourt = libelleCourt;
    }

    public String getLibellePluriel() {
        return this.libellePluriel;
    }

    public TypeStructure libellePluriel(String libellePluriel) {
        this.setLibellePluriel(libellePluriel);
        return this;
    }

    public void setLibellePluriel(String libellePluriel) {
        this.libellePluriel = libellePluriel;
    }

    public String getUrlComplete() {
        return this.urlComplete;
    }

    public TypeStructure urlComplete(String urlComplete) {
        this.setUrlComplete(urlComplete);
        return this;
    }

    public void setUrlComplete(String urlComplete) {
        this.urlComplete = urlComplete;
    }

    public String getUrlSimplifie() {
        return this.urlSimplifie;
    }

    public TypeStructure urlSimplifie(String urlSimplifie) {
        this.setUrlSimplifie(urlSimplifie);
        return this;
    }

    public void setUrlSimplifie(String urlSimplifie) {
        this.urlSimplifie = urlSimplifie;
    }

    public Long getOrdre() {
        return this.ordre;
    }

    public TypeStructure ordre(Long ordre) {
        this.setOrdre(ordre);
        return this;
    }

    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public void setStructure(Structure structure) {
        if (this.structure != null) {
            this.structure.setTypeStructure(null);
        }
        if (structure != null) {
            structure.setTypeStructure(this);
        }
        this.structure = structure;
    }

    public TypeStructure structure(Structure structure) {
        this.setStructure(structure);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeStructure)) {
            return false;
        }
        return id != null && id.equals(((TypeStructure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeStructure{" +
            "id=" + getId() +
            ", codeTypeStructure='" + getCodeTypeStructure() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", libelleCourt='" + getLibelleCourt() + "'" +
            ", libellePluriel='" + getLibellePluriel() + "'" +
            ", urlComplete='" + getUrlComplete() + "'" +
            ", urlSimplifie='" + getUrlSimplifie() + "'" +
            ", ordre=" + getOrdre() +
            "}";
    }
}
