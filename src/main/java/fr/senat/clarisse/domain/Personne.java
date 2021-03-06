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
 * A Personne.
 */
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Column(name = "lieu_de_naissance")
    private String lieuDeNaissance;

    @Column(name = "departement_de_naissance")
    private Long departementDeNaissance;

    @Column(name = "date_de_deces")
    private LocalDate dateDeDeces;

    @Column(name = "lieu_de_deces")
    private String lieuDeDeces;

    @Column(name = "departement_de_deces")
    private Long departementDeDeces;

    @Column(name = "profession")
    private String profession;

    @Column(name = "diplome")
    private String diplome;

    @Column(name = "decoration")
    private String decoration;

    @Column(name = "rang_protocolaire")
    private Long rangProtocolaire;

    @Column(name = "csp_insee")
    private String cspInsee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "personnes" }, allowSetters = true)
    private NuancePolitique nuancePolitique;

    @OneToMany(mappedBy = "personne")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personne" }, allowSetters = true)
    private Set<Noms> noms = new HashSet<>();

    @OneToMany(mappedBy = "personne")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeAppartenance", "structure", "personne" }, allowSetters = true)
    private Set<Appartenance> appartenances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Personne id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Personne matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Personne dateDeNaissance(LocalDate dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getLieuDeNaissance() {
        return this.lieuDeNaissance;
    }

    public Personne lieuDeNaissance(String lieuDeNaissance) {
        this.setLieuDeNaissance(lieuDeNaissance);
        return this;
    }

    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }

    public Long getDepartementDeNaissance() {
        return this.departementDeNaissance;
    }

    public Personne departementDeNaissance(Long departementDeNaissance) {
        this.setDepartementDeNaissance(departementDeNaissance);
        return this;
    }

    public void setDepartementDeNaissance(Long departementDeNaissance) {
        this.departementDeNaissance = departementDeNaissance;
    }

    public LocalDate getDateDeDeces() {
        return this.dateDeDeces;
    }

    public Personne dateDeDeces(LocalDate dateDeDeces) {
        this.setDateDeDeces(dateDeDeces);
        return this;
    }

    public void setDateDeDeces(LocalDate dateDeDeces) {
        this.dateDeDeces = dateDeDeces;
    }

    public String getLieuDeDeces() {
        return this.lieuDeDeces;
    }

    public Personne lieuDeDeces(String lieuDeDeces) {
        this.setLieuDeDeces(lieuDeDeces);
        return this;
    }

    public void setLieuDeDeces(String lieuDeDeces) {
        this.lieuDeDeces = lieuDeDeces;
    }

    public Long getDepartementDeDeces() {
        return this.departementDeDeces;
    }

    public Personne departementDeDeces(Long departementDeDeces) {
        this.setDepartementDeDeces(departementDeDeces);
        return this;
    }

    public void setDepartementDeDeces(Long departementDeDeces) {
        this.departementDeDeces = departementDeDeces;
    }

    public String getProfession() {
        return this.profession;
    }

    public Personne profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDiplome() {
        return this.diplome;
    }

    public Personne diplome(String diplome) {
        this.setDiplome(diplome);
        return this;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getDecoration() {
        return this.decoration;
    }

    public Personne decoration(String decoration) {
        this.setDecoration(decoration);
        return this;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public Long getRangProtocolaire() {
        return this.rangProtocolaire;
    }

    public Personne rangProtocolaire(Long rangProtocolaire) {
        this.setRangProtocolaire(rangProtocolaire);
        return this;
    }

    public void setRangProtocolaire(Long rangProtocolaire) {
        this.rangProtocolaire = rangProtocolaire;
    }

    public String getCspInsee() {
        return this.cspInsee;
    }

    public Personne cspInsee(String cspInsee) {
        this.setCspInsee(cspInsee);
        return this;
    }

    public void setCspInsee(String cspInsee) {
        this.cspInsee = cspInsee;
    }

    public NuancePolitique getNuancePolitique() {
        return this.nuancePolitique;
    }

    public void setNuancePolitique(NuancePolitique nuancePolitique) {
        this.nuancePolitique = nuancePolitique;
    }

    public Personne nuancePolitique(NuancePolitique nuancePolitique) {
        this.setNuancePolitique(nuancePolitique);
        return this;
    }

    public Set<Noms> getNoms() {
        return this.noms;
    }

    public void setNoms(Set<Noms> noms) {
        if (this.noms != null) {
            this.noms.forEach(i -> i.setPersonne(null));
        }
        if (noms != null) {
            noms.forEach(i -> i.setPersonne(this));
        }
        this.noms = noms;
    }

    public Personne noms(Set<Noms> noms) {
        this.setNoms(noms);
        return this;
    }

    public Personne addNoms(Noms noms) {
        this.noms.add(noms);
        noms.setPersonne(this);
        return this;
    }

    public Personne removeNoms(Noms noms) {
        this.noms.remove(noms);
        noms.setPersonne(null);
        return this;
    }

    public Set<Appartenance> getAppartenances() {
        return this.appartenances;
    }

    public void setAppartenances(Set<Appartenance> appartenances) {
        if (this.appartenances != null) {
            this.appartenances.forEach(i -> i.setPersonne(null));
        }
        if (appartenances != null) {
            appartenances.forEach(i -> i.setPersonne(this));
        }
        this.appartenances = appartenances;
    }

    public Personne appartenances(Set<Appartenance> appartenances) {
        this.setAppartenances(appartenances);
        return this;
    }

    public Personne addAppartenance(Appartenance appartenance) {
        this.appartenances.add(appartenance);
        appartenance.setPersonne(this);
        return this;
    }

    public Personne removeAppartenance(Appartenance appartenance) {
        this.appartenances.remove(appartenance);
        appartenance.setPersonne(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", lieuDeNaissance='" + getLieuDeNaissance() + "'" +
            ", departementDeNaissance=" + getDepartementDeNaissance() +
            ", dateDeDeces='" + getDateDeDeces() + "'" +
            ", lieuDeDeces='" + getLieuDeDeces() + "'" +
            ", departementDeDeces=" + getDepartementDeDeces() +
            ", profession='" + getProfession() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", decoration='" + getDecoration() + "'" +
            ", rangProtocolaire=" + getRangProtocolaire() +
            ", cspInsee='" + getCspInsee() + "'" +
            "}";
    }
}
