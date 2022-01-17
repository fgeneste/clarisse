package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.TypeStructure;
import fr.senat.clarisse.repository.TypeStructureRepository;
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
 * Integration tests for the {@link TypeStructureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeStructureResourceIT {

    private static final String DEFAULT_CODE_TYPE_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_STRUCTURE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_COURT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_PLURIEL = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PLURIEL = "BBBBBBBBBB";

    private static final String DEFAULT_URL_COMPLETE = "AAAAAAAAAA";
    private static final String UPDATED_URL_COMPLETE = "BBBBBBBBBB";

    private static final String DEFAULT_URL_SIMPLIFIE = "AAAAAAAAAA";
    private static final String UPDATED_URL_SIMPLIFIE = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDRE = 1L;
    private static final Long UPDATED_ORDRE = 2L;

    private static final String ENTITY_API_URL = "/api/type-structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeStructureRepository typeStructureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeStructureMockMvc;

    private TypeStructure typeStructure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeStructure createEntity(EntityManager em) {
        TypeStructure typeStructure = new TypeStructure()
            .codeTypeStructure(DEFAULT_CODE_TYPE_STRUCTURE)
            .libelle(DEFAULT_LIBELLE)
            .libelleCourt(DEFAULT_LIBELLE_COURT)
            .libellePluriel(DEFAULT_LIBELLE_PLURIEL)
            .urlComplete(DEFAULT_URL_COMPLETE)
            .urlSimplifie(DEFAULT_URL_SIMPLIFIE)
            .ordre(DEFAULT_ORDRE);
        return typeStructure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeStructure createUpdatedEntity(EntityManager em) {
        TypeStructure typeStructure = new TypeStructure()
            .codeTypeStructure(UPDATED_CODE_TYPE_STRUCTURE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libellePluriel(UPDATED_LIBELLE_PLURIEL)
            .urlComplete(UPDATED_URL_COMPLETE)
            .urlSimplifie(UPDATED_URL_SIMPLIFIE)
            .ordre(UPDATED_ORDRE);
        return typeStructure;
    }

    @BeforeEach
    public void initTest() {
        typeStructure = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeStructure() throws Exception {
        int databaseSizeBeforeCreate = typeStructureRepository.findAll().size();
        // Create the TypeStructure
        restTypeStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeStructure)))
            .andExpect(status().isCreated());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeCreate + 1);
        TypeStructure testTypeStructure = typeStructureList.get(typeStructureList.size() - 1);
        assertThat(testTypeStructure.getCodeTypeStructure()).isEqualTo(DEFAULT_CODE_TYPE_STRUCTURE);
        assertThat(testTypeStructure.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeStructure.getLibelleCourt()).isEqualTo(DEFAULT_LIBELLE_COURT);
        assertThat(testTypeStructure.getLibellePluriel()).isEqualTo(DEFAULT_LIBELLE_PLURIEL);
        assertThat(testTypeStructure.getUrlComplete()).isEqualTo(DEFAULT_URL_COMPLETE);
        assertThat(testTypeStructure.getUrlSimplifie()).isEqualTo(DEFAULT_URL_SIMPLIFIE);
        assertThat(testTypeStructure.getOrdre()).isEqualTo(DEFAULT_ORDRE);
    }

    @Test
    @Transactional
    void createTypeStructureWithExistingId() throws Exception {
        // Create the TypeStructure with an existing ID
        typeStructure.setId(1L);

        int databaseSizeBeforeCreate = typeStructureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeStructure)))
            .andExpect(status().isBadRequest());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeStructures() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        // Get all the typeStructureList
        restTypeStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeStructure").value(hasItem(DEFAULT_CODE_TYPE_STRUCTURE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].libelleCourt").value(hasItem(DEFAULT_LIBELLE_COURT)))
            .andExpect(jsonPath("$.[*].libellePluriel").value(hasItem(DEFAULT_LIBELLE_PLURIEL)))
            .andExpect(jsonPath("$.[*].urlComplete").value(hasItem(DEFAULT_URL_COMPLETE)))
            .andExpect(jsonPath("$.[*].urlSimplifie").value(hasItem(DEFAULT_URL_SIMPLIFIE)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE.intValue())));
    }

    @Test
    @Transactional
    void getTypeStructure() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        // Get the typeStructure
        restTypeStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, typeStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeStructure.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeStructure").value(DEFAULT_CODE_TYPE_STRUCTURE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleCourt").value(DEFAULT_LIBELLE_COURT))
            .andExpect(jsonPath("$.libellePluriel").value(DEFAULT_LIBELLE_PLURIEL))
            .andExpect(jsonPath("$.urlComplete").value(DEFAULT_URL_COMPLETE))
            .andExpect(jsonPath("$.urlSimplifie").value(DEFAULT_URL_SIMPLIFIE))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTypeStructure() throws Exception {
        // Get the typeStructure
        restTypeStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeStructure() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();

        // Update the typeStructure
        TypeStructure updatedTypeStructure = typeStructureRepository.findById(typeStructure.getId()).get();
        // Disconnect from session so that the updates on updatedTypeStructure are not directly saved in db
        em.detach(updatedTypeStructure);
        updatedTypeStructure
            .codeTypeStructure(UPDATED_CODE_TYPE_STRUCTURE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libellePluriel(UPDATED_LIBELLE_PLURIEL)
            .urlComplete(UPDATED_URL_COMPLETE)
            .urlSimplifie(UPDATED_URL_SIMPLIFIE)
            .ordre(UPDATED_ORDRE);

        restTypeStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeStructure))
            )
            .andExpect(status().isOk());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
        TypeStructure testTypeStructure = typeStructureList.get(typeStructureList.size() - 1);
        assertThat(testTypeStructure.getCodeTypeStructure()).isEqualTo(UPDATED_CODE_TYPE_STRUCTURE);
        assertThat(testTypeStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeStructure.getLibelleCourt()).isEqualTo(UPDATED_LIBELLE_COURT);
        assertThat(testTypeStructure.getLibellePluriel()).isEqualTo(UPDATED_LIBELLE_PLURIEL);
        assertThat(testTypeStructure.getUrlComplete()).isEqualTo(UPDATED_URL_COMPLETE);
        assertThat(testTypeStructure.getUrlSimplifie()).isEqualTo(UPDATED_URL_SIMPLIFIE);
        assertThat(testTypeStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    void putNonExistingTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeStructure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeStructureWithPatch() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();

        // Update the typeStructure using partial update
        TypeStructure partialUpdatedTypeStructure = new TypeStructure();
        partialUpdatedTypeStructure.setId(typeStructure.getId());

        partialUpdatedTypeStructure
            .codeTypeStructure(UPDATED_CODE_TYPE_STRUCTURE)
            .libelle(UPDATED_LIBELLE)
            .urlComplete(UPDATED_URL_COMPLETE)
            .urlSimplifie(UPDATED_URL_SIMPLIFIE)
            .ordre(UPDATED_ORDRE);

        restTypeStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeStructure))
            )
            .andExpect(status().isOk());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
        TypeStructure testTypeStructure = typeStructureList.get(typeStructureList.size() - 1);
        assertThat(testTypeStructure.getCodeTypeStructure()).isEqualTo(UPDATED_CODE_TYPE_STRUCTURE);
        assertThat(testTypeStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeStructure.getLibelleCourt()).isEqualTo(DEFAULT_LIBELLE_COURT);
        assertThat(testTypeStructure.getLibellePluriel()).isEqualTo(DEFAULT_LIBELLE_PLURIEL);
        assertThat(testTypeStructure.getUrlComplete()).isEqualTo(UPDATED_URL_COMPLETE);
        assertThat(testTypeStructure.getUrlSimplifie()).isEqualTo(UPDATED_URL_SIMPLIFIE);
        assertThat(testTypeStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    void fullUpdateTypeStructureWithPatch() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();

        // Update the typeStructure using partial update
        TypeStructure partialUpdatedTypeStructure = new TypeStructure();
        partialUpdatedTypeStructure.setId(typeStructure.getId());

        partialUpdatedTypeStructure
            .codeTypeStructure(UPDATED_CODE_TYPE_STRUCTURE)
            .libelle(UPDATED_LIBELLE)
            .libelleCourt(UPDATED_LIBELLE_COURT)
            .libellePluriel(UPDATED_LIBELLE_PLURIEL)
            .urlComplete(UPDATED_URL_COMPLETE)
            .urlSimplifie(UPDATED_URL_SIMPLIFIE)
            .ordre(UPDATED_ORDRE);

        restTypeStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeStructure))
            )
            .andExpect(status().isOk());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
        TypeStructure testTypeStructure = typeStructureList.get(typeStructureList.size() - 1);
        assertThat(testTypeStructure.getCodeTypeStructure()).isEqualTo(UPDATED_CODE_TYPE_STRUCTURE);
        assertThat(testTypeStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeStructure.getLibelleCourt()).isEqualTo(UPDATED_LIBELLE_COURT);
        assertThat(testTypeStructure.getLibellePluriel()).isEqualTo(UPDATED_LIBELLE_PLURIEL);
        assertThat(testTypeStructure.getUrlComplete()).isEqualTo(UPDATED_URL_COMPLETE);
        assertThat(testTypeStructure.getUrlSimplifie()).isEqualTo(UPDATED_URL_SIMPLIFIE);
        assertThat(testTypeStructure.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeStructure() throws Exception {
        int databaseSizeBeforeUpdate = typeStructureRepository.findAll().size();
        typeStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeStructureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeStructure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeStructure in the database
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeStructure() throws Exception {
        // Initialize the database
        typeStructureRepository.saveAndFlush(typeStructure);

        int databaseSizeBeforeDelete = typeStructureRepository.findAll().size();

        // Delete the typeStructure
        restTypeStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeStructure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeStructure> typeStructureList = typeStructureRepository.findAll();
        assertThat(typeStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
