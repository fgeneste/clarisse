package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Appartenance.
 */
@Entity
@Table(name = "appartenance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appartenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_election")
    private LocalDate dateElection;

    @Column(name = "observation")
    private String observation;

    @Column(name = "departement")
    private Long departement;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "article")
    private String article;

    @Column(name = "motif_de_debut")
    private String motifDeDebut;

    @Column(name = "commentaire_de_changement")
    private String commentaireDeChangement;

    @JsonIgnoreProperties(value = { "appartenance" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private TypeAppartenance typeAppartenance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "typeStructure", "appartenances" }, allowSetters = true)
    private Structure structure;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nuancePolitique", "noms", "appartenances" }, allowSetters = true)
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appartenance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Appartenance matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Appartenance dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Appartenance dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateElection() {
        return this.dateElection;
    }

    public Appartenance dateElection(LocalDate dateElection) {
        this.setDateElection(dateElection);
        return this;
    }

    public void setDateElection(LocalDate dateElection) {
        this.dateElection = dateElection;
    }

    public String getObservation() {
        return this.observation;
    }

    public Appartenance observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getDepartement() {
        return this.departement;
    }

    public Appartenance departement(Long departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(Long departement) {
        this.departement = departement;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Appartenance libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getArticle() {
        return this.article;
    }

    public Appartenance article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getMotifDeDebut() {
        return this.motifDeDebut;
    }

    public Appartenance motifDeDebut(String motifDeDebut) {
        this.setMotifDeDebut(motifDeDebut);
        return this;
    }

    public void setMotifDeDebut(String motifDeDebut) {
        this.motifDeDebut = motifDeDebut;
    }

    public String getCommentaireDeChangement() {
        return this.commentaireDeChangement;
    }

    public Appartenance commentaireDeChangement(String commentaireDeChangement) {
        this.setCommentaireDeChangement(commentaireDeChangement);
        return this;
    }

    public void setCommentaireDeChangement(String commentaireDeChangement) {
        this.commentaireDeChangement = commentaireDeChangement;
    }

    public TypeAppartenance getTypeAppartenance() {
        return this.typeAppartenance;
    }

    public void setTypeAppartenance(TypeAppartenance typeAppartenance) {
        this.typeAppartenance = typeAppartenance;
    }

    public Appartenance typeAppartenance(TypeAppartenance typeAppartenance) {
        this.setTypeAppartenance(typeAppartenance);
        return this;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Appartenance structure(Structure structure) {
        this.setStructure(structure);
        return this;
    }

    public Personne getPersonne() {
        return this.personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Appartenance personne(Personne personne) {
        this.setPersonne(personne);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appartenance)) {
            return false;
        }
        return id != null && id.equals(((Appartenance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appartenance{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", dateElection='" + getDateElection() + "'" +
            ", observation='" + getObservation() + "'" +
            ", departement=" + getDepartement() +
            ", libelle='" + getLibelle() + "'" +
            ", article='" + getArticle() + "'" +
            ", motifDeDebut='" + getMotifDeDebut() + "'" +
            ", commentaireDeChangement='" + getCommentaireDeChangement() + "'" +
            "}";
    }
}
