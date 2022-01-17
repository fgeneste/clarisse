package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.TypeAppartenance;
import fr.senat.clarisse.repository.TypeAppartenanceRepository;
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
 * Integration tests for the {@link TypeAppartenanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeAppartenanceResourceIT {

    private static final String DEFAULT_TYPE_APPARTENANCE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_APPARTENANCE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-appartenances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeAppartenanceRepository typeAppartenanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeAppartenanceMockMvc;

    private TypeAppartenance typeAppartenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAppartenance createEntity(EntityManager em) {
        TypeAppartenance typeAppartenance = new TypeAppartenance().typeAppartenance(DEFAULT_TYPE_APPARTENANCE).libelle(DEFAULT_LIBELLE);
        return typeAppartenance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAppartenance createUpdatedEntity(EntityManager em) {
        TypeAppartenance typeAppartenance = new TypeAppartenance().typeAppartenance(UPDATED_TYPE_APPARTENANCE).libelle(UPDATED_LIBELLE);
        return typeAppartenance;
    }

    @BeforeEach
    public void initTest() {
        typeAppartenance = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeAppartenance() throws Exception {
        int databaseSizeBeforeCreate = typeAppartenanceRepository.findAll().size();
        // Create the TypeAppartenance
        restTypeAppartenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isCreated());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAppartenance testTypeAppartenance = typeAppartenanceList.get(typeAppartenanceList.size() - 1);
        assertThat(testTypeAppartenance.getTypeAppartenance()).isEqualTo(DEFAULT_TYPE_APPARTENANCE);
        assertThat(testTypeAppartenance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createTypeAppartenanceWithExistingId() throws Exception {
        // Create the TypeAppartenance with an existing ID
        typeAppartenance.setId(1L);

        int databaseSizeBeforeCreate = typeAppartenanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAppartenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeAppartenances() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        // Get all the typeAppartenanceList
        restTypeAppartenanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAppartenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeAppartenance").value(hasItem(DEFAULT_TYPE_APPARTENANCE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getTypeAppartenance() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        // Get the typeAppartenance
        restTypeAppartenanceMockMvc
            .perform(get(ENTITY_API_URL_ID, typeAppartenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAppartenance.getId().intValue()))
            .andExpect(jsonPath("$.typeAppartenance").value(DEFAULT_TYPE_APPARTENANCE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingTypeAppartenance() throws Exception {
        // Get the typeAppartenance
        restTypeAppartenanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeAppartenance() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();

        // Update the typeAppartenance
        TypeAppartenance updatedTypeAppartenance = typeAppartenanceRepository.findById(typeAppartenance.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAppartenance are not directly saved in db
        em.detach(updatedTypeAppartenance);
        updatedTypeAppartenance.typeAppartenance(UPDATED_TYPE_APPARTENANCE).libelle(UPDATED_LIBELLE);

        restTypeAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeAppartenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
        TypeAppartenance testTypeAppartenance = typeAppartenanceList.get(typeAppartenanceList.size() - 1);
        assertThat(testTypeAppartenance.getTypeAppartenance()).isEqualTo(UPDATED_TYPE_APPARTENANCE);
        assertThat(testTypeAppartenance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeAppartenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeAppartenanceWithPatch() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();

        // Update the typeAppartenance using partial update
        TypeAppartenance partialUpdatedTypeAppartenance = new TypeAppartenance();
        partialUpdatedTypeAppartenance.setId(typeAppartenance.getId());

        partialUpdatedTypeAppartenance.typeAppartenance(UPDATED_TYPE_APPARTENANCE);

        restTypeAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAppartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
        TypeAppartenance testTypeAppartenance = typeAppartenanceList.get(typeAppartenanceList.size() - 1);
        assertThat(testTypeAppartenance.getTypeAppartenance()).isEqualTo(UPDATED_TYPE_APPARTENANCE);
        assertThat(testTypeAppartenance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateTypeAppartenanceWithPatch() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();

        // Update the typeAppartenance using partial update
        TypeAppartenance partialUpdatedTypeAppartenance = new TypeAppartenance();
        partialUpdatedTypeAppartenance.setId(typeAppartenance.getId());

        partialUpdatedTypeAppartenance.typeAppartenance(UPDATED_TYPE_APPARTENANCE).libelle(UPDATED_LIBELLE);

        restTypeAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAppartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAppartenance))
            )
            .andExpect(status().isOk());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
        TypeAppartenance testTypeAppartenance = typeAppartenanceList.get(typeAppartenanceList.size() - 1);
        assertThat(testTypeAppartenance.getTypeAppartenance()).isEqualTo(UPDATED_TYPE_APPARTENANCE);
        assertThat(testTypeAppartenance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeAppartenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeAppartenance() throws Exception {
        int databaseSizeBeforeUpdate = typeAppartenanceRepository.findAll().size();
        typeAppartenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAppartenanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAppartenance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAppartenance in the database
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeAppartenance() throws Exception {
        // Initialize the database
        typeAppartenanceRepository.saveAndFlush(typeAppartenance);

        int databaseSizeBeforeDelete = typeAppartenanceRepository.findAll().size();

        // Delete the typeAppartenance
        restTypeAppartenanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeAppartenance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAppartenance> typeAppartenanceList = typeAppartenanceRepository.findAll();
        assertThat(typeAppartenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
