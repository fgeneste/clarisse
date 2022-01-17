package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Structure.
 */
@Entity
@Table(name = "structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Structure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code_structure")
    private String codeStructure;

    @Column(name = "date_de_creation")
    private LocalDate dateDeCreation;

    @Column(name = "date_de_cloture")
    private LocalDate dateDeCloture;

    @Column(name = "code_ameli")
    private String codeAmeli;

    @Column(name = "code_sirpas")
    private String codeSirpas;

    @Column(name = "code_reprographie")
    private String codeReprographie;

    @Column(name = "article")
    private String article;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "libelle_court")
    private String libelleCourt;

    @Column(name = "libelle_long")
    private String libelleLong;

    @Column(name = "ordre")
    private Long ordre;

    @Column(name = "publication_sur_internet")
    private String publicationSurInternet;

    @JsonIgnoreProperties(value = { "structure" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private TypeStructure typeStructure;

    @OneToMany(mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeAppartenance", "structure", "personne" }, allowSetters = true)
    private Set<Appartenance> appartenances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Structure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeStructure() {
        return this.codeStructure;
    }

    public Structure codeStructure(String codeStructure) {
        this.setCodeStructure(codeStructure);
        return this;
    }

    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public LocalDate getDateDeCreation() {
        return this.dateDeCreation;
    }

    public Structure dateDeCreation(LocalDate dateDeCreation) {
        this.setDateDeCreation(dateDeCreation);
        return this;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public LocalDate getDateDeCloture() {
        return this.dateDeCloture;
    }

    public Structure dateDeCloture(LocalDate dateDeCloture) {
        this.setDateDeCloture(dateDeCloture);
        return this;
    }

    public void setDateDeCloture(LocalDate dateDeCloture) {
        this.dateDeCloture = dateDeCloture;
    }

    public String getCodeAmeli() {
        return this.codeAmeli;
    }

    public Structure codeAmeli(String codeAmeli) {
        this.setCodeAmeli(codeAmeli);
        return this;
    }

    public void setCodeAmeli(String codeAmeli) {
        this.codeAmeli = codeAmeli;
    }

    public String getCodeSirpas() {
        return this.codeSirpas;
    }

    public Structure codeSirpas(String codeSirpas) {
        this.setCodeSirpas(codeSirpas);
        return this;
    }

    public void setCodeSirpas(String codeSirpas) {
        this.codeSirpas = codeSirpas;
    }

    public String getCodeReprographie() {
        return this.codeReprographie;
    }

    public Structure codeReprographie(String codeReprographie) {
        this.setCodeReprographie(codeReprographie);
        return this;
    }

    public void setCodeReprographie(String codeReprographie) {
        this.codeReprographie = codeReprographie;
    }

    public String getArticle() {
        return this.article;
    }

    public Structure article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Structure libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleCourt() {
        return this.libelleCourt;
    }

    public Structure libelleCourt(String libelleCourt) {
        this.setLibelleCourt(libelleCourt);
        return this;
    }

    public void setLibelleCourt(String libelleCourt) {
        this.libelleCourt = libelleCourt;
    }

    public String getLibelleLong() {
        return this.libelleLong;
    }

    public Structure libelleLong(String libelleLong) {
        this.setLibelleLong(libelleLong);
        return this;
    }

    public void setLibelleLong(String libelleLong) {
        this.libelleLong = libelleLong;
    }

    public Long getOrdre() {
        return this.ordre;
    }

    public Structure ordre(Long ordre) {
        this.setOrdre(ordre);
        return this;
    }

    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    public String getPublicationSurInternet() {
        return this.publicationSurInternet;
    }

    public Structure publicationSurInternet(String publicationSurInternet) {
        this.setPublicationSurInternet(publicationSurInternet);
        return this;
    }

    public void setPublicationSurInternet(String publicationSurInternet) {
        this.publicationSurInternet = publicationSurInternet;
    }

    public TypeStructure getTypeStructure() {
        return this.typeStructure;
    }

    public void setTypeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
    }

    public Structure typeStructure(TypeStructure typeStructure) {
        this.setTypeStructure(typeStructure);
        return this;
    }

    public Set<Appartenance> getAppartenances() {
        return this.appartenances;
    }

    public void setAppartenances(Set<Appartenance> appartenances) {
        if (this.appartenances != null) {
            this.appartenances.forEach(i -> i.setStructure(null));
        }
        if (appartenances != null) {
            appartenances.forEach(i -> i.setStructure(this));
        }
        this.appartenances = appartenances;
    }

    public Structure appartenances(Set<Appartenance> appartenances) {
        this.setAppartenances(appartenances);
        return this;
    }

    public Structure addAppartenance(Appartenance appartenance) {
        this.appartenances.add(appartenance);
        appartenance.setStructure(this);
        return this;
    }

    public Structure removeAppartenance(Appartenance appartenance) {
        this.appartenances.remove(appartenance);
        appartenance.setStructure(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Structure)) {
            return false;
        }
        return id != null && id.equals(((Structure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Structure{" +
            "id=" + getId() +
            ", codeStructure='" + getCodeStructure() + "'" +
            ", dateDeCreation='" + getDateDeCreation() + "'" +
            ", dateDeCloture='" + getDateDeCloture() + "'" +
            ", codeAmeli='" + getCodeAmeli() + "'" +
            ", codeSirpas='" + getCodeSirpas() + "'" +
            ", codeReprographie='" + getCodeReprographie() + "'" +
            ", article='" + getArticle() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", libelleCourt='" + getLibelleCourt() + "'" +
            ", libelleLong='" + getLibelleLong() + "'" +
            ", ordre=" + getOrdre() +
            ", publicationSurInternet='" + getPublicationSurInternet() + "'" +
            "}";
    }
}
