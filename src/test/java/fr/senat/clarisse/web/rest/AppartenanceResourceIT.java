package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.Appartenance;
import fr.senat.clarisse.repository.AppartenanceRepository;
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
 * Integration tests for the {@link AppartenanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppartenanceResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ELECTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ELECTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTEMENT = 1L;
    private static final Long UPDATED_DEPARTEMENT = 2L;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF_DE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_DE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE_DE_CHANGEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_DE_CHANGEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appartenances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppartenanceRepository appartenanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppartenanceMockMvc;

    private Appartenance appartenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartenance createEntity(EntityManager em) {
        Appartenance appartenance = new Appartenance()
            .matricule(DEFAULT_MATRICULE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .dateElection(DEFAULT_DATE_ELECTION)
            .observation(DEFAULT_OBSERVATION)
            .departement(DEFAULT_DEPARTEMENT)
            .libelle(DEFAULT_LIBELLE)
            .article(DEFAULT_ARTICLE)
            .motifDeDebut(DEFAULT_MOTIF_DE_DEBUT)
            .commentaireDeChangement(DEFAULT_COMMENTAIRE_DE_CHANGEMENT);
        return appartenance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartenance createUpdatedEntity(EntityManager em) {
        Appartenance appartenance = new Appartenance()
            .matricule(UPDATED_MATRICULE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateElection(UPDATED_DATE_ELECTION)
            .observation(UPDATED_OBSERVATION)
            .departement(UPDATED_DEPARTEMENT)
            .libelle(UPDATED_LIBELLE)
            .article(UPDATED_ARTICLE)
            .motifDeDebut(UPDATED_MOTIF_DE_DEBUT)
            .commentaireDeChangement(UPDATED_COMMENTAIRE_DE_CHANGEMENT);
        return appartenance;
    }

    @BeforeEach
    public void initTest() {
        appartenance = createEntity(em);
    }

    @Test
    @Transactional
    void createAppartenance() throws Exception {
        int databaseSizeBeforeCreate = appartenanceRepository.findAll().size();
        // Create the Appartenance
        restAppartenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isCreated());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeCreate + 1);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testAppartenance.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAppartenance.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAppartenance.getDateElection()).isEqualTo(DEFAULT_DATE_ELECTION);
        assertThat(testAppartenance.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testAppartenance.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testAppartenance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAppartenance.getArticle()).isEqualTo(DEFAULT_ARTICLE);
        assertThat(testAppartenance.getMotifDeDebut()).isEqualTo(DEFAULT_MOTIF_DE_DEBUT);
        assertThat(testAppartenance.getCommentaireDeChangement()).isEqualTo(DEFAULT_COMMENTAIRE_DE_CHANGEMENT);
    }

    @Test
    @Transactional
    void createAppartenanceWithExistingId() throws Exception {
        // Create the Appartenance with an existing ID
        appartenance.setId(1L);

        int databaseSizeBeforeCreate = appartenanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppartenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppartenances() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        // Get all the appartenanceList
        restAppartenanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateElection").value(hasItem(DEFAULT_DATE_ELECTION.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)))
            .andExpect(jsonPath("$.[*].motifDeDebut").value(hasItem(DEFAULT_MOTIF_DE_DEBUT)))
            .andExpect(jsonPath("$.[*].commentaireDeChangement").value(hasItem(DEFAULT_COMMENTAIRE_DE_CHANGEMENT)));
    }

    @Test
    @Transactional
    void getAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        // Get the appartenance
        restAppartenanceMockMvc
            .perform(get(ENTITY_API_URL_ID, appartenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartenance.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.dateElection").value(DEFAULT_DATE_ELECTION.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE))
            .andExpect(jsonPath("$.motifDeDebut").value(DEFAULT_MOTIF_DE_DEBUT))
            .andExpect(jsonPath("$.commentaireDeChangement").value(DEFAULT_COMMENTAIRE_DE_CHANGEMENT));
    }

    @Test
    @Transactional
    void getNonExistingAppartenance() throws Exception {
        // Get the appartenance
        restAppartenanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();

        // Update the appartenance
        Appartenance updatedAppartenance = appartenanceRepository.findById(appartenance.getId()).get();
        // Disconnect from session so that the updates on updatedAppartenance are not directly saved in db
        em.detach(updatedAppartenance);
        updatedAppartenance
            .matricule(UPDATED_MATRICULE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateElection(UPDATED_DATE_ELECTION)
            .observation(UPDATED_OBSERVATION)
            .departement(UPDATED_DEPARTEMENT)
            .libelle(UPDATED_LIBELLE)
            .article(UPDATED_ARTICLE)
            .motifDeDebut(UPDATED_MOTIF_DE_DEBUT)
            .commentaireDeChangement(UPDATED_COMMENTAIRE_DE_CHANGEMENT);

        restAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppartenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testAppartenance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAppartenance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAppartenance.getDateElection()).isEqualTo(UPDATED_DATE_ELECTION);
        assertThat(testAppartenance.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testAppartenance.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testAppartenance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAppartenance.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testAppartenance.getMotifDeDebut()).isEqualTo(UPDATED_MOTIF_DE_DEBUT);
        assertThat(testAppartenance.getCommentaireDeChangement()).isEqualTo(UPDATED_COMMENTAIRE_DE_CHANGEMENT);
    }

    @Test
    @Transactional
    void putNonExistingAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appartenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartenance)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppartenanceWithPatch() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();

        // Update the appartenance using partial update
        Appartenance partialUpdatedAppartenance = new Appartenance();
        partialUpdatedAppartenance.setId(appartenance.getId());

        partialUpdatedAppartenance
            .dateFin(UPDATED_DATE_FIN)
            .dateElection(UPDATED_DATE_ELECTION)
            .observation(UPDATED_OBSERVATION)
            .departement(UPDATED_DEPARTEMENT)
            .article(UPDATED_ARTICLE)
            .motifDeDebut(UPDATED_MOTIF_DE_DEBUT);

        restAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testAppartenance.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAppartenance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAppartenance.getDateElection()).isEqualTo(UPDATED_DATE_ELECTION);
        assertThat(testAppartenance.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testAppartenance.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testAppartenance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAppartenance.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testAppartenance.getMotifDeDebut()).isEqualTo(UPDATED_MOTIF_DE_DEBUT);
        assertThat(testAppartenance.getCommentaireDeChangement()).isEqualTo(DEFAULT_COMMENTAIRE_DE_CHANGEMENT);
    }

    @Test
    @Transactional
    void fullUpdateAppartenanceWithPatch() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();

        // Update the appartenance using partial update
        Appartenance partialUpdatedAppartenance = new Appartenance();
        partialUpdatedAppartenance.setId(appartenance.getId());

        partialUpdatedAppartenance
            .matricule(UPDATED_MATRICULE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateElection(UPDATED_DATE_ELECTION)
            .observation(UPDATED_OBSERVATION)
            .departement(UPDATED_DEPARTEMENT)
            .libelle(UPDATED_LIBELLE)
            .article(UPDATED_ARTICLE)
            .motifDeDebut(UPDATED_MOTIF_DE_DEBUT)
            .commentaireDeChangement(UPDATED_COMMENTAIRE_DE_CHANGEMENT);

        restAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
        Appartenance testAppartenance = appartenanceList.get(appartenanceList.size() - 1);
        assertThat(testAppartenance.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testAppartenance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAppartenance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAppartenance.getDateElection()).isEqualTo(UPDATED_DATE_ELECTION);
        assertThat(testAppartenance.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testAppartenance.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testAppartenance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAppartenance.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testAppartenance.getMotifDeDebut()).isEqualTo(UPDATED_MOTIF_DE_DEBUT);
        assertThat(testAppartenance.getCommentaireDeChangement()).isEqualTo(UPDATED_COMMENTAIRE_DE_CHANGEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = appartenanceRepository.findAll().size();
        appartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appartenance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartenance in the database
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppartenance() throws Exception {
        // Initialize the database
        appartenanceRepository.saveAndFlush(appartenance);

        int databaseSizeBeforeDelete = appartenanceRepository.findAll().size();

        // Delete the appartenance
        restAppartenanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, appartenance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appartenance> appartenanceList = appartenanceRepository.findAll();
        assertThat(appartenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
