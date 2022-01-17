package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.Structure;
import fr.senat.clarisse.repository.StructureRepository;
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
 * Integration tests for the {@link StructureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StructureResourceIT {

    private static final String DEFAULT_CODE_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_STRUCTURE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DE_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_AMELI = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AMELI = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_SIRPAS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SIRPAS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_REPROGRAPHIE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_REPROGRAPHIE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_COURT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_LONG = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_LONG = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDRE = 1L;
    private static final Long UPDATED_ORDRE = 2L;

    private static final String DEFAULT_PUBLICATION_SUR_INTERNET = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_SUR_INTERNET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStructureMockMvc;

    private Structure structure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createEntity(EntityManager em) {
        Structure structure = new Structure()
            .codeStructure(DEFAULT_CODE_STRUCTURE)
            .dateDeCreation(DEFAULT_DATE_DE_CREATION)
            .dateDeCloture(DEFAULT_DATE_DE_CLOTURE)
            .codeAmeli(DEFAULT_CODE_AMELI)
            .codeSirpas(DEFAULT_CODE_SIRPAS)
            .codeReprographie(DEFAULT_CODE_REPROGRAPHIE)
            .article(DEFAULT_ARTICLE)
            .libelle(DEFAULT_LIBELLE)
            .libelleCourt(DEFAULT_LIBELLE_COURT)
            .libelleLong(DEFAULT_LIBELLE_LONG)
            .ordre(DEFAULT_ORDRE)
            .publicationSurInternet(DEFAULT_PUBLICATION_SUR_INTERNET);
        return structure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createUpdatedEntity(EntityManager em) {
        Structure structure = new Structure()
            .codeStructure(UPDATED_CODE_STRUCTURE)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .dateDeCloture(UPDATED_DATE_DE_CLOTURE)
            .codeAmeli(UPDATED_CODE_AMELI)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeReprographie(UPDATED_CODE_REPROGRAPHIE)
            .article(UPDATED_ARTICLE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libelleLong(UPDATED_LIBELLE_LONG)
            .ordre(UPDATED_ORDRE)
            .publicationSurInternet(UPDATED_PUBLICATION_SUR_INTERNET);
        return structure;
    }

    @BeforeEach
    public void initTest() {
        structure = createEntity(em);
    }

    @Test
    @Transactional
    void createStructure() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();
        // Create the Structure
        restStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isCreated());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate + 1);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCodeStructure()).isEqualTo(DEFAULT_CODE_STRUCTURE);
        assertThat(testStructure.getDateDeCreation()).isEqualTo(DEFAULT_DATE_DE_CREATION);
        assertThat(testStructure.getDateDeCloture()).isEqualTo(DEFAULT_DATE_DE_CLOTURE);
        assertThat(testStructure.getCodeAmeli()).isEqualTo(DEFAULT_CODE_AMELI);
        assertThat(testStructure.getCodeSirpas()).isEqualTo(DEFAULT_CODE_SIRPAS);
        assertThat(testStructure.getCodeReprographie()).isEqualTo(DEFAULT_CODE_REPROGRAPHIE);
        assertThat(testStructure.getArticle()).isEqualTo(DEFAULT_ARTICLE);
        assertThat(testStructure.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStructure.getLibelleCourt()).isEqualTo(DEFAULT_LIBELLE_COURT);
        assertThat(testStructure.getLibelleLong()).isEqualTo(DEFAULT_LIBELLE_LONG);
        assertThat(testStructure.getOrdre()).isEqualTo(DEFAULT_ORDRE);
        assertThat(testStructure.getPublicationSurInternet()).isEqualTo(DEFAULT_PUBLICATION_SUR_INTERNET);
    }

    @Test
    @Transactional
    void createStructureWithExistingId() throws Exception {
        // Create the Structure with an existing ID
        structure.setId(1L);

        int databaseSizeBeforeCreate = structureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStructures() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList
        restStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeStructure").value(hasItem(DEFAULT_CODE_STRUCTURE)))
            .andExpect(jsonPath("$.[*].dateDeCreation").value(hasItem(DEFAULT_DATE_DE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateDeCloture").value(hasItem(DEFAULT_DATE_DE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].codeAmeli").value(hasItem(DEFAULT_CODE_AMELI)))
            .andExpect(jsonPath("$.[*].codeSirpas").value(hasItem(DEFAULT_CODE_SIRPAS)))
            .andExpect(jsonPath("$.[*].codeReprographie").value(hasItem(DEFAULT_CODE_REPROGRAPHIE)))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].libelleCourt").value(hasItem(DEFAULT_LIBELLE_COURT)))
            .andExpect(jsonPath("$.[*].libelleLong").value(hasItem(DEFAULT_LIBELLE_LONG)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE.intValue())))
            .andExpect(jsonPath("$.[*].publicationSurInternet").value(hasItem(DEFAULT_PUBLICATION_SUR_INTERNET)));
    }

    @Test
    @Transactional
    void getStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get the structure
        restStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, structure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(structure.getId().intValue()))
            .andExpect(jsonPath("$.codeStructure").value(DEFAULT_CODE_STRUCTURE))
            .andExpect(jsonPath("$.dateDeCreation").value(DEFAULT_DATE_DE_CREATION.toString()))
            .andExpect(jsonPath("$.dateDeCloture").value(DEFAULT_DATE_DE_CLOTURE.toString()))
            .andExpect(jsonPath("$.codeAmeli").value(DEFAULT_CODE_AMELI))
            .andExpect(jsonPath("$.codeSirpas").value(DEFAULT_CODE_SIRPAS))
            .andExpect(jsonPath("$.codeReprographie").value(DEFAULT_CODE_REPROGRAPHIE))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleCourt").value(DEFAULT_LIBELLE_COURT))
            .andExpect(jsonPath("$.libelleLong").value(DEFAULT_LIBELLE_LONG))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE.intValue()))
            .andExpect(jsonPath("$.publicationSurInternet").value(DEFAULT_PUBLICATION_SUR_INTERNET));
    }

    @Test
    @Transactional
    void getNonExistingStructure() throws Exception {
        // Get the structure
        restStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure
        Structure updatedStructure = structureRepository.findById(structure.getId()).get();
        // Disconnect from session so that the updates on updatedStructure are not directly saved in db
        em.detach(updatedStructure);
        updatedStructure
            .codeStructure(UPDATED_CODE_STRUCTURE)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .dateDeCloture(UPDATED_DATE_DE_CLOTURE)
            .codeAmeli(UPDATED_CODE_AMELI)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeReprographie(UPDATED_CODE_REPROGRAPHIE)
            .article(UPDATED_ARTICLE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libelleLong(UPDATED_LIBELLE_LONG)
            .ordre(UPDATED_ORDRE)
            .publicationSurInternet(UPDATED_PUBLICATION_SUR_INTERNET);

        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStructure))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCodeStructure()).isEqualTo(UPDATED_CODE_STRUCTURE);
        assertThat(testStructure.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testStructure.getDateDeCloture()).isEqualTo(UPDATED_DATE_DE_CLOTURE);
        assertThat(testStructure.getCodeAmeli()).isEqualTo(UPDATED_CODE_AMELI);
        assertThat(testStructure.getCodeSirpas()).isEqualTo(UPDATED_CODE_SIRPAS);
        assertThat(testStructure.getCodeReprographie()).isEqualTo(UPDATED_CODE_REPROGRAPHIE);
        assertThat(testStructure.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStructure.getLibelleCourt()).isEqualTo(UPDATED_LIBELLE_COURT);
        assertThat(testStructure.getLibelleLong()).isEqualTo(UPDATED_LIBELLE_LONG);
        assertThat(testStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testStructure.getPublicationSurInternet()).isEqualTo(UPDATED_PUBLICATION_SUR_INTERNET);
    }

    @Test
    @Transactional
    void putNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, structure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(structure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(structure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStructureWithPatch() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure using partial update
        Structure partialUpdatedStructure = new Structure();
        partialUpdatedStructure.setId(structure.getId());

        partialUpdatedStructure
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .article(UPDATED_ARTICLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libelleLong(UPDATED_LIBELLE_LONG)
            .ordre(UPDATED_ORDRE);

        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStructure))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCodeStructure()).isEqualTo(DEFAULT_CODE_STRUCTURE);
        assertThat(testStructure.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testStructure.getDateDeCloture()).isEqualTo(DEFAULT_DATE_DE_CLOTURE);
        assertThat(testStructure.getCodeAmeli()).isEqualTo(DEFAULT_CODE_AMELI);
        assertThat(testStructure.getCodeSirpas()).isEqualTo(DEFAULT_CODE_SIRPAS);
        assertThat(testStructure.getCodeReprographie()).isEqualTo(DEFAULT_CODE_REPROGRAPHIE);
        assertThat(testStructure.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testStructure.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStructure.getLibelleCourt()).isEqualTo(UPDATED_LIBELLE_COURT);
        assertThat(testStructure.getLibelleLong()).isEqualTo(UPDATED_LIBELLE_LONG);
        assertThat(testStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testStructure.getPublicationSurInternet()).isEqualTo(DEFAULT_PUBLICATION_SUR_INTERNET);
    }

    @Test
    @Transactional
    void fullUpdateStructureWithPatch() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure using partial update
        Structure partialUpdatedStructure = new Structure();
        partialUpdatedStructure.setId(structure.getId());

        partialUpdatedStructure
            .codeStructure(UPDATED_CODE_STRUCTURE)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .dateDeCloture(UPDATED_DATE_DE_CLOTURE)
            .codeAmeli(UPDATED_CODE_AMELI)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeReprographie(UPDATED_CODE_REPROGRAPHIE)
            .article(UPDATED_ARTICLE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libelleLong(UPDATED_LIBELLE_LONG)
            .ordre(UPDATED_ORDRE)
            .publicationSurInternet(UPDATED_PUBLICATION_SUR_INTERNET);

        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStructure))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCodeStructure()).isEqualTo(UPDATED_CODE_STRUCTURE);
        assertThat(testStructure.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testStructure.getDateDeCloture()).isEqualTo(UPDATED_DATE_DE_CLOTURE);
        assertThat(testStructure.getCodeAmeli()).isEqualTo(UPDATED_CODE_AMELI);
        assertThat(testStructure.getCodeSirpas()).isEqualTo(UPDATED_CODE_SIRPAS);
        assertThat(testStructure.getCodeReprographie()).isEqualTo(UPDATED_CODE_REPROGRAPHIE);
        assertThat(testStructure.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStructure.getLibelleCourt()).isEqualTo(UPDATED_LIBELLE_COURT);
        assertThat(testStructure.getLibelleLong()).isEqualTo(UPDATED_LIBELLE_LONG);
        assertThat(testStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testStructure.getPublicationSurInternet()).isEqualTo(UPDATED_PUBLICATION_SUR_INTERNET);
    }

    @Test
    @Transactional
    void patchNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, structure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(structure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(structure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(structure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeDelete = structureRepository.findAll().size();

        // Delete the structure
        restStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, structure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
