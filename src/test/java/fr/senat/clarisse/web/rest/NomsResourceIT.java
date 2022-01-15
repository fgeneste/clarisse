package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.Noms;
import fr.senat.clarisse.repository.NomsRepository;
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
 * Integration tests for the {@link NomsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NomsResourceIT {

    private static final String DEFAULT_QUALITE = "AAAAAAAAAA";
    private static final String UPDATED_QUALITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_USUEL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_USUEL = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_USUEL = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_USUEL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PATRONYMIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PATRONYMIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CIVIL = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CIVIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MARITAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MARITAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_DISTINCTIF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DISTINCTIF = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MAJUSCULE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MAJUSCULE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_TECHNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TECHNIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_FEMINISATION = "AAAAAAAAAA";
    private static final String UPDATED_FEMINISATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/noms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NomsRepository nomsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNomsMockMvc;

    private Noms noms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noms createEntity(EntityManager em) {
        Noms noms = new Noms()
            .qualite(DEFAULT_QUALITE)
            .nomUsuel(DEFAULT_NOM_USUEL)
            .prenomUsuel(DEFAULT_PRENOM_USUEL)
            .nomPatronymique(DEFAULT_NOM_PATRONYMIQUE)
            .prenomCivil(DEFAULT_PRENOM_CIVIL)
            .nomMarital(DEFAULT_NOM_MARITAL)
            .nomDistinctif(DEFAULT_NOM_DISTINCTIF)
            .nomMajuscule(DEFAULT_NOM_MAJUSCULE)
            .nomTechnique(DEFAULT_NOM_TECHNIQUE)
            .feminisation(DEFAULT_FEMINISATION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return noms;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noms createUpdatedEntity(EntityManager em) {
        Noms noms = new Noms()
            .qualite(UPDATED_QUALITE)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .prenomCivil(UPDATED_PRENOM_CIVIL)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomDistinctif(UPDATED_NOM_DISTINCTIF)
            .nomMajuscule(UPDATED_NOM_MAJUSCULE)
            .nomTechnique(UPDATED_NOM_TECHNIQUE)
            .feminisation(UPDATED_FEMINISATION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        return noms;
    }

    @BeforeEach
    public void initTest() {
        noms = createEntity(em);
    }

    @Test
    @Transactional
    void createNoms() throws Exception {
        int databaseSizeBeforeCreate = nomsRepository.findAll().size();
        // Create the Noms
        restNomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noms)))
            .andExpect(status().isCreated());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeCreate + 1);
        Noms testNoms = nomsList.get(nomsList.size() - 1);
        assertThat(testNoms.getQualite()).isEqualTo(DEFAULT_QUALITE);
        assertThat(testNoms.getNomUsuel()).isEqualTo(DEFAULT_NOM_USUEL);
        assertThat(testNoms.getPrenomUsuel()).isEqualTo(DEFAULT_PRENOM_USUEL);
        assertThat(testNoms.getNomPatronymique()).isEqualTo(DEFAULT_NOM_PATRONYMIQUE);
        assertThat(testNoms.getPrenomCivil()).isEqualTo(DEFAULT_PRENOM_CIVIL);
        assertThat(testNoms.getNomMarital()).isEqualTo(DEFAULT_NOM_MARITAL);
        assertThat(testNoms.getNomDistinctif()).isEqualTo(DEFAULT_NOM_DISTINCTIF);
        assertThat(testNoms.getNomMajuscule()).isEqualTo(DEFAULT_NOM_MAJUSCULE);
        assertThat(testNoms.getNomTechnique()).isEqualTo(DEFAULT_NOM_TECHNIQUE);
        assertThat(testNoms.getFeminisation()).isEqualTo(DEFAULT_FEMINISATION);
        assertThat(testNoms.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testNoms.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createNomsWithExistingId() throws Exception {
        // Create the Noms with an existing ID
        noms.setId(1L);

        int databaseSizeBeforeCreate = nomsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noms)))
            .andExpect(status().isBadRequest());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNoms() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        // Get all the nomsList
        restNomsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noms.getId().intValue())))
            .andExpect(jsonPath("$.[*].qualite").value(hasItem(DEFAULT_QUALITE)))
            .andExpect(jsonPath("$.[*].nomUsuel").value(hasItem(DEFAULT_NOM_USUEL)))
            .andExpect(jsonPath("$.[*].prenomUsuel").value(hasItem(DEFAULT_PRENOM_USUEL)))
            .andExpect(jsonPath("$.[*].nomPatronymique").value(hasItem(DEFAULT_NOM_PATRONYMIQUE)))
            .andExpect(jsonPath("$.[*].prenomCivil").value(hasItem(DEFAULT_PRENOM_CIVIL)))
            .andExpect(jsonPath("$.[*].nomMarital").value(hasItem(DEFAULT_NOM_MARITAL)))
            .andExpect(jsonPath("$.[*].nomDistinctif").value(hasItem(DEFAULT_NOM_DISTINCTIF)))
            .andExpect(jsonPath("$.[*].nomMajuscule").value(hasItem(DEFAULT_NOM_MAJUSCULE)))
            .andExpect(jsonPath("$.[*].nomTechnique").value(hasItem(DEFAULT_NOM_TECHNIQUE)))
            .andExpect(jsonPath("$.[*].feminisation").value(hasItem(DEFAULT_FEMINISATION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    void getNoms() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        // Get the noms
        restNomsMockMvc
            .perform(get(ENTITY_API_URL_ID, noms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noms.getId().intValue()))
            .andExpect(jsonPath("$.qualite").value(DEFAULT_QUALITE))
            .andExpect(jsonPath("$.nomUsuel").value(DEFAULT_NOM_USUEL))
            .andExpect(jsonPath("$.prenomUsuel").value(DEFAULT_PRENOM_USUEL))
            .andExpect(jsonPath("$.nomPatronymique").value(DEFAULT_NOM_PATRONYMIQUE))
            .andExpect(jsonPath("$.prenomCivil").value(DEFAULT_PRENOM_CIVIL))
            .andExpect(jsonPath("$.nomMarital").value(DEFAULT_NOM_MARITAL))
            .andExpect(jsonPath("$.nomDistinctif").value(DEFAULT_NOM_DISTINCTIF))
            .andExpect(jsonPath("$.nomMajuscule").value(DEFAULT_NOM_MAJUSCULE))
            .andExpect(jsonPath("$.nomTechnique").value(DEFAULT_NOM_TECHNIQUE))
            .andExpect(jsonPath("$.feminisation").value(DEFAULT_FEMINISATION))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNoms() throws Exception {
        // Get the noms
        restNomsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoms() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();

        // Update the noms
        Noms updatedNoms = nomsRepository.findById(noms.getId()).get();
        // Disconnect from session so that the updates on updatedNoms are not directly saved in db
        em.detach(updatedNoms);
        updatedNoms
            .qualite(UPDATED_QUALITE)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .prenomCivil(UPDATED_PRENOM_CIVIL)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomDistinctif(UPDATED_NOM_DISTINCTIF)
            .nomMajuscule(UPDATED_NOM_MAJUSCULE)
            .nomTechnique(UPDATED_NOM_TECHNIQUE)
            .feminisation(UPDATED_FEMINISATION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);

        restNomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNoms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNoms))
            )
            .andExpect(status().isOk());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
        Noms testNoms = nomsList.get(nomsList.size() - 1);
        assertThat(testNoms.getQualite()).isEqualTo(UPDATED_QUALITE);
        assertThat(testNoms.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testNoms.getPrenomUsuel()).isEqualTo(UPDATED_PRENOM_USUEL);
        assertThat(testNoms.getNomPatronymique()).isEqualTo(UPDATED_NOM_PATRONYMIQUE);
        assertThat(testNoms.getPrenomCivil()).isEqualTo(UPDATED_PRENOM_CIVIL);
        assertThat(testNoms.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testNoms.getNomDistinctif()).isEqualTo(UPDATED_NOM_DISTINCTIF);
        assertThat(testNoms.getNomMajuscule()).isEqualTo(UPDATED_NOM_MAJUSCULE);
        assertThat(testNoms.getNomTechnique()).isEqualTo(UPDATED_NOM_TECHNIQUE);
        assertThat(testNoms.getFeminisation()).isEqualTo(UPDATED_FEMINISATION);
        assertThat(testNoms.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testNoms.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noms)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNomsWithPatch() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();

        // Update the noms using partial update
        Noms partialUpdatedNoms = new Noms();
        partialUpdatedNoms.setId(noms.getId());

        partialUpdatedNoms
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomMajuscule(UPDATED_NOM_MAJUSCULE)
            .feminisation(UPDATED_FEMINISATION)
            .dateFin(UPDATED_DATE_FIN);

        restNomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoms))
            )
            .andExpect(status().isOk());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
        Noms testNoms = nomsList.get(nomsList.size() - 1);
        assertThat(testNoms.getQualite()).isEqualTo(DEFAULT_QUALITE);
        assertThat(testNoms.getNomUsuel()).isEqualTo(DEFAULT_NOM_USUEL);
        assertThat(testNoms.getPrenomUsuel()).isEqualTo(UPDATED_PRENOM_USUEL);
        assertThat(testNoms.getNomPatronymique()).isEqualTo(UPDATED_NOM_PATRONYMIQUE);
        assertThat(testNoms.getPrenomCivil()).isEqualTo(DEFAULT_PRENOM_CIVIL);
        assertThat(testNoms.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testNoms.getNomDistinctif()).isEqualTo(DEFAULT_NOM_DISTINCTIF);
        assertThat(testNoms.getNomMajuscule()).isEqualTo(UPDATED_NOM_MAJUSCULE);
        assertThat(testNoms.getNomTechnique()).isEqualTo(DEFAULT_NOM_TECHNIQUE);
        assertThat(testNoms.getFeminisation()).isEqualTo(UPDATED_FEMINISATION);
        assertThat(testNoms.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testNoms.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateNomsWithPatch() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();

        // Update the noms using partial update
        Noms partialUpdatedNoms = new Noms();
        partialUpdatedNoms.setId(noms.getId());

        partialUpdatedNoms
            .qualite(UPDATED_QUALITE)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .prenomCivil(UPDATED_PRENOM_CIVIL)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomDistinctif(UPDATED_NOM_DISTINCTIF)
            .nomMajuscule(UPDATED_NOM_MAJUSCULE)
            .nomTechnique(UPDATED_NOM_TECHNIQUE)
            .feminisation(UPDATED_FEMINISATION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);

        restNomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoms))
            )
            .andExpect(status().isOk());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
        Noms testNoms = nomsList.get(nomsList.size() - 1);
        assertThat(testNoms.getQualite()).isEqualTo(UPDATED_QUALITE);
        assertThat(testNoms.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testNoms.getPrenomUsuel()).isEqualTo(UPDATED_PRENOM_USUEL);
        assertThat(testNoms.getNomPatronymique()).isEqualTo(UPDATED_NOM_PATRONYMIQUE);
        assertThat(testNoms.getPrenomCivil()).isEqualTo(UPDATED_PRENOM_CIVIL);
        assertThat(testNoms.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testNoms.getNomDistinctif()).isEqualTo(UPDATED_NOM_DISTINCTIF);
        assertThat(testNoms.getNomMajuscule()).isEqualTo(UPDATED_NOM_MAJUSCULE);
        assertThat(testNoms.getNomTechnique()).isEqualTo(UPDATED_NOM_TECHNIQUE);
        assertThat(testNoms.getFeminisation()).isEqualTo(UPDATED_FEMINISATION);
        assertThat(testNoms.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testNoms.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoms() throws Exception {
        int databaseSizeBeforeUpdate = nomsRepository.findAll().size();
        noms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(noms)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noms in the database
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoms() throws Exception {
        // Initialize the database
        nomsRepository.saveAndFlush(noms);

        int databaseSizeBeforeDelete = nomsRepository.findAll().size();

        // Delete the noms
        restNomsMockMvc
            .perform(delete(ENTITY_API_URL_ID, noms.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Noms> nomsList = nomsRepository.findAll();
        assertThat(nomsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
