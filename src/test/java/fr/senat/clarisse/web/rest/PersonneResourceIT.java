package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.Personne;
import fr.senat.clarisse.repository.PersonneRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonneResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_DE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_DE_NAISSANCE = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTEMENT_DE_NAISSANCE = 1L;
    private static final Long UPDATED_DEPARTEMENT_DE_NAISSANCE = 2L;

    private static final LocalDate DEFAULT_DATE_DE_DECES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_DECES = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_DE_DECES = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_DE_DECES = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTEMENT_DE_DECES = 1L;
    private static final Long UPDATED_DEPARTEMENT_DE_DECES = 2L;

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final String DEFAULT_DECORATION = "AAAAAAAAAA";
    private static final String UPDATED_DECORATION = "BBBBBBBBBB";

    private static final Long DEFAULT_RANG_PROTOCOLAIRE = 1L;
    private static final Long UPDATED_RANG_PROTOCOLAIRE = 2L;

    private static final String DEFAULT_CSP_INSEE = "AAAAAAAAAA";
    private static final String UPDATED_CSP_INSEE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personnes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonneMockMvc;

    private Personne personne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .matricule(DEFAULT_MATRICULE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .lieuDeNaissance(DEFAULT_LIEU_DE_NAISSANCE)
            .departementDeNaissance(DEFAULT_DEPARTEMENT_DE_NAISSANCE)
            .dateDeDeces(DEFAULT_DATE_DE_DECES)
            .lieuDeDeces(DEFAULT_LIEU_DE_DECES)
            .departementDeDeces(DEFAULT_DEPARTEMENT_DE_DECES)
            .profession(DEFAULT_PROFESSION)
            .diplome(DEFAULT_DIPLOME)
            .decoration(DEFAULT_DECORATION)
            .rangProtocolaire(DEFAULT_RANG_PROTOCOLAIRE)
            .cspInsee(DEFAULT_CSP_INSEE);
        return personne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity(EntityManager em) {
        Personne personne = new Personne()
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .lieuDeNaissance(UPDATED_LIEU_DE_NAISSANCE)
            .departementDeNaissance(UPDATED_DEPARTEMENT_DE_NAISSANCE)
            .dateDeDeces(UPDATED_DATE_DE_DECES)
            .lieuDeDeces(UPDATED_LIEU_DE_DECES)
            .departementDeDeces(UPDATED_DEPARTEMENT_DE_DECES)
            .profession(UPDATED_PROFESSION)
            .diplome(UPDATED_DIPLOME)
            .decoration(UPDATED_DECORATION)
            .rangProtocolaire(UPDATED_RANG_PROTOCOLAIRE)
            .cspInsee(UPDATED_CSP_INSEE);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();
        // Create the Personne
        restPersonneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testPersonne.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testPersonne.getLieuDeNaissance()).isEqualTo(DEFAULT_LIEU_DE_NAISSANCE);
        assertThat(testPersonne.getDepartementDeNaissance()).isEqualTo(DEFAULT_DEPARTEMENT_DE_NAISSANCE);
        assertThat(testPersonne.getDateDeDeces()).isEqualTo(DEFAULT_DATE_DE_DECES);
        assertThat(testPersonne.getLieuDeDeces()).isEqualTo(DEFAULT_LIEU_DE_DECES);
        assertThat(testPersonne.getDepartementDeDeces()).isEqualTo(DEFAULT_DEPARTEMENT_DE_DECES);
        assertThat(testPersonne.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testPersonne.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testPersonne.getDecoration()).isEqualTo(DEFAULT_DECORATION);
        assertThat(testPersonne.getRangProtocolaire()).isEqualTo(DEFAULT_RANG_PROTOCOLAIRE);
        assertThat(testPersonne.getCspInsee()).isEqualTo(DEFAULT_CSP_INSEE);
    }

    @Test
    @Transactional
    void createPersonneWithExistingId() throws Exception {
        // Create the Personne with an existing ID
        personne.setId(1L);

        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuDeNaissance").value(hasItem(DEFAULT_LIEU_DE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].departementDeNaissance").value(hasItem(DEFAULT_DEPARTEMENT_DE_NAISSANCE.intValue())))
            .andExpect(jsonPath("$.[*].dateDeDeces").value(hasItem(DEFAULT_DATE_DE_DECES.toString())))
            .andExpect(jsonPath("$.[*].lieuDeDeces").value(hasItem(DEFAULT_LIEU_DE_DECES)))
            .andExpect(jsonPath("$.[*].departementDeDeces").value(hasItem(DEFAULT_DEPARTEMENT_DE_DECES.intValue())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME)))
            .andExpect(jsonPath("$.[*].decoration").value(hasItem(DEFAULT_DECORATION)))
            .andExpect(jsonPath("$.[*].rangProtocolaire").value(hasItem(DEFAULT_RANG_PROTOCOLAIRE.intValue())))
            .andExpect(jsonPath("$.[*].cspInsee").value(hasItem(DEFAULT_CSP_INSEE)));
    }

    @Test
    @Transactional
    void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc
            .perform(get(ENTITY_API_URL_ID, personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuDeNaissance").value(DEFAULT_LIEU_DE_NAISSANCE))
            .andExpect(jsonPath("$.departementDeNaissance").value(DEFAULT_DEPARTEMENT_DE_NAISSANCE.intValue()))
            .andExpect(jsonPath("$.dateDeDeces").value(DEFAULT_DATE_DE_DECES.toString()))
            .andExpect(jsonPath("$.lieuDeDeces").value(DEFAULT_LIEU_DE_DECES))
            .andExpect(jsonPath("$.departementDeDeces").value(DEFAULT_DEPARTEMENT_DE_DECES.intValue()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME))
            .andExpect(jsonPath("$.decoration").value(DEFAULT_DECORATION))
            .andExpect(jsonPath("$.rangProtocolaire").value(DEFAULT_RANG_PROTOCOLAIRE.intValue()))
            .andExpect(jsonPath("$.cspInsee").value(DEFAULT_CSP_INSEE));
    }

    @Test
    @Transactional
    void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .lieuDeNaissance(UPDATED_LIEU_DE_NAISSANCE)
            .departementDeNaissance(UPDATED_DEPARTEMENT_DE_NAISSANCE)
            .dateDeDeces(UPDATED_DATE_DE_DECES)
            .lieuDeDeces(UPDATED_LIEU_DE_DECES)
            .departementDeDeces(UPDATED_DEPARTEMENT_DE_DECES)
            .profession(UPDATED_PROFESSION)
            .diplome(UPDATED_DIPLOME)
            .decoration(UPDATED_DECORATION)
            .rangProtocolaire(UPDATED_RANG_PROTOCOLAIRE)
            .cspInsee(UPDATED_CSP_INSEE);

        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testPersonne.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testPersonne.getLieuDeNaissance()).isEqualTo(UPDATED_LIEU_DE_NAISSANCE);
        assertThat(testPersonne.getDepartementDeNaissance()).isEqualTo(UPDATED_DEPARTEMENT_DE_NAISSANCE);
        assertThat(testPersonne.getDateDeDeces()).isEqualTo(UPDATED_DATE_DE_DECES);
        assertThat(testPersonne.getLieuDeDeces()).isEqualTo(UPDATED_LIEU_DE_DECES);
        assertThat(testPersonne.getDepartementDeDeces()).isEqualTo(UPDATED_DEPARTEMENT_DE_DECES);
        assertThat(testPersonne.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testPersonne.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testPersonne.getDecoration()).isEqualTo(UPDATED_DECORATION);
        assertThat(testPersonne.getRangProtocolaire()).isEqualTo(UPDATED_RANG_PROTOCOLAIRE);
        assertThat(testPersonne.getCspInsee()).isEqualTo(UPDATED_CSP_INSEE);
    }

    @Test
    @Transactional
    void putNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonneWithPatch() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne using partial update
        Personne partialUpdatedPersonne = new Personne();
        partialUpdatedPersonne.setId(personne.getId());

        partialUpdatedPersonne
            .matricule(UPDATED_MATRICULE)
            .dateDeDeces(UPDATED_DATE_DE_DECES)
            .profession(UPDATED_PROFESSION)
            .diplome(UPDATED_DIPLOME)
            .rangProtocolaire(UPDATED_RANG_PROTOCOLAIRE)
            .cspInsee(UPDATED_CSP_INSEE);

        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testPersonne.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testPersonne.getLieuDeNaissance()).isEqualTo(DEFAULT_LIEU_DE_NAISSANCE);
        assertThat(testPersonne.getDepartementDeNaissance()).isEqualTo(DEFAULT_DEPARTEMENT_DE_NAISSANCE);
        assertThat(testPersonne.getDateDeDeces()).isEqualTo(UPDATED_DATE_DE_DECES);
        assertThat(testPersonne.getLieuDeDeces()).isEqualTo(DEFAULT_LIEU_DE_DECES);
        assertThat(testPersonne.getDepartementDeDeces()).isEqualTo(DEFAULT_DEPARTEMENT_DE_DECES);
        assertThat(testPersonne.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testPersonne.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testPersonne.getDecoration()).isEqualTo(DEFAULT_DECORATION);
        assertThat(testPersonne.getRangProtocolaire()).isEqualTo(UPDATED_RANG_PROTOCOLAIRE);
        assertThat(testPersonne.getCspInsee()).isEqualTo(UPDATED_CSP_INSEE);
    }

    @Test
    @Transactional
    void fullUpdatePersonneWithPatch() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne using partial update
        Personne partialUpdatedPersonne = new Personne();
        partialUpdatedPersonne.setId(personne.getId());

        partialUpdatedPersonne
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .lieuDeNaissance(UPDATED_LIEU_DE_NAISSANCE)
            .departementDeNaissance(UPDATED_DEPARTEMENT_DE_NAISSANCE)
            .dateDeDeces(UPDATED_DATE_DE_DECES)
            .lieuDeDeces(UPDATED_LIEU_DE_DECES)
            .departementDeDeces(UPDATED_DEPARTEMENT_DE_DECES)
            .profession(UPDATED_PROFESSION)
            .diplome(UPDATED_DIPLOME)
            .decoration(UPDATED_DECORATION)
            .rangProtocolaire(UPDATED_RANG_PROTOCOLAIRE)
            .cspInsee(UPDATED_CSP_INSEE);

        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonne))
            )
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testPersonne.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testPersonne.getLieuDeNaissance()).isEqualTo(UPDATED_LIEU_DE_NAISSANCE);
        assertThat(testPersonne.getDepartementDeNaissance()).isEqualTo(UPDATED_DEPARTEMENT_DE_NAISSANCE);
        assertThat(testPersonne.getDateDeDeces()).isEqualTo(UPDATED_DATE_DE_DECES);
        assertThat(testPersonne.getLieuDeDeces()).isEqualTo(UPDATED_LIEU_DE_DECES);
        assertThat(testPersonne.getDepartementDeDeces()).isEqualTo(UPDATED_DEPARTEMENT_DE_DECES);
        assertThat(testPersonne.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testPersonne.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testPersonne.getDecoration()).isEqualTo(UPDATED_DECORATION);
        assertThat(testPersonne.getRangProtocolaire()).isEqualTo(UPDATED_RANG_PROTOCOLAIRE);
        assertThat(testPersonne.getCspInsee()).isEqualTo(UPDATED_CSP_INSEE);
    }

    @Test
    @Transactional
    void patchNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();
        personne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc
            .perform(delete(ENTITY_API_URL_ID, personne.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
