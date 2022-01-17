package fr.senat.clarisse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Noms.
 */
@Entity
@Table(name = "noms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Noms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "qualite")
    private String qualite;

    @Column(name = "nom_usuel")
    private String nomUsuel;

    @Column(name = "prenom_usuel")
    private String prenomUsuel;

    @Column(name = "nom_patronymique")
    private String nomPatronymique;

    @Column(name = "prenom_civil")
    private String prenomCivil;

    @Column(name = "nom_marital")
    private String nomMarital;

    @Column(name = "nom_distinctif")
    private String nomDistinctif;

    @Column(name = "nom_majuscule")
    private String nomMajuscule;

    @Column(name = "nom_technique")
    private String nomTechnique;

    @Column(name = "feminisation")
    private String feminisation;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nuancePolitique", "noms", "appartenances" }, allowSetters = true)
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Noms id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualite() {
        return this.qualite;
    }

    public Noms qualite(String qualite) {
        this.setQualite(qualite);
        return this;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getNomUsuel() {
        return this.nomUsuel;
    }

    public Noms nomUsuel(String nomUsuel) {
        this.setNomUsuel(nomUsuel);
        return this;
    }

    public void setNomUsuel(String nomUsuel) {
        this.nomUsuel = nomUsuel;
    }

    public String getPrenomUsuel() {
        return this.prenomUsuel;
    }

    public Noms prenomUsuel(String prenomUsuel) {
        this.setPrenomUsuel(prenomUsuel);
        return this;
    }

    public void setPrenomUsuel(String prenomUsuel) {
        this.prenomUsuel = prenomUsuel;
    }

    public String getNomPatronymique() {
        return this.nomPatronymique;
    }

    public Noms nomPatronymique(String nomPatronymique) {
        this.setNomPatronymique(nomPatronymique);
        return this;
    }

    public void setNomPatronymique(String nomPatronymique) {
        this.nomPatronymique = nomPatronymique;
    }

    public String getPrenomCivil() {
        return this.prenomCivil;
    }

    public Noms prenomCivil(String prenomCivil) {
        this.setPrenomCivil(prenomCivil);
        return this;
    }

    public void setPrenomCivil(String prenomCivil) {
        this.prenomCivil = prenomCivil;
    }

    public String getNomMarital() {
        return this.nomMarital;
    }

    public Noms nomMarital(String nomMarital) {
        this.setNomMarital(nomMarital);
        return this;
    }

    public void setNomMarital(String nomMarital) {
        this.nomMarital = nomMarital;
    }

    public String getNomDistinctif() {
        return this.nomDistinctif;
    }

    public Noms nomDistinctif(String nomDistinctif) {
        this.setNomDistinctif(nomDistinctif);
        return this;
    }

    public void setNomDistinctif(String nomDistinctif) {
        this.nomDistinctif = nomDistinctif;
    }

    public String getNomMajuscule() {
        return this.nomMajuscule;
    }

    public Noms nomMajuscule(String nomMajuscule) {
        this.setNomMajuscule(nomMajuscule);
        return this;
    }

    public void setNomMajuscule(String nomMajuscule) {
        this.nomMajuscule = nomMajuscule;
    }

    public String getNomTechnique() {
        return this.nomTechnique;
    }

    public Noms nomTechnique(String nomTechnique) {
        this.setNomTechnique(nomTechnique);
        return this;
    }

    public void setNomTechnique(String nomTechnique) {
        this.nomTechnique = nomTechnique;
    }

    public String getFeminisation() {
        return this.feminisation;
    }

    public Noms feminisation(String feminisation) {
        this.setFeminisation(feminisation);
        return this;
    }

    public void setFeminisation(String feminisation) {
        this.feminisation = feminisation;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Noms dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Noms dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Personne getPersonne() {
        return this.personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Noms personne(Personne personne) {
        this.setPersonne(personne);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Noms)) {
            return false;
        }
        return id != null && id.equals(((Noms) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Noms{" +
            "id=" + getId() +
            ", qualite='" + getQualite() + "'" +
            ", nomUsuel='" + getNomUsuel() + "'" +
            ", prenomUsuel='" + getPrenomUsuel() + "'" +
            ", nomPatronymique='" + getNomPatronymique() + "'" +
            ", prenomCivil='" + getPrenomCivil() + "'" +
            ", nomMarital='" + getNomMarital() + "'" +
            ", nomDistinctif='" + getNomDistinctif() + "'" +
            ", nomMajuscule='" + getNomMajuscule() + "'" +
            ", nomTechnique='" + getNomTechnique() + "'" +
            ", feminisation='" + getFeminisation() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
