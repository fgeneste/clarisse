package fr.senat.clarisse.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.clarisse.IntegrationTest;
import fr.senat.clarisse.domain.NuancePolitique;
import fr.senat.clarisse.repository.NuancePolitiqueRepository;
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
 * Integration tests for the {@link NuancePolitiqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NuancePolitiqueResourceIT {

    private static final String DEFAULT_CODE_NUANCE_POLITIQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NUANCE_POLITIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nuance-politiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NuancePolitiqueRepository nuancePolitiqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNuancePolitiqueMockMvc;

    private NuancePolitique nuancePolitique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NuancePolitique createEntity(EntityManager em) {
        NuancePolitique nuancePolitique = new NuancePolitique().codeNuancePolitique(DEFAULT_CODE_NUANCE_POLITIQUE).libelle(DEFAULT_LIBELLE);
        return nuancePolitique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NuancePolitique createUpdatedEntity(EntityManager em) {
        NuancePolitique nuancePolitique = new NuancePolitique().codeNuancePolitique(UPDATED_CODE_NUANCE_POLITIQUE).libelle(UPDATED_LIBELLE);
        return nuancePolitique;
    }

    @BeforeEach
    public void initTest() {
        nuancePolitique = createEntity(em);
    }

    @Test
    @Transactional
    void createNuancePolitique() throws Exception {
        int databaseSizeBeforeCreate = nuancePolitiqueRepository.findAll().size();
        // Create the NuancePolitique
        restNuancePolitiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isCreated());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeCreate + 1);
        NuancePolitique testNuancePolitique = nuancePolitiqueList.get(nuancePolitiqueList.size() - 1);
        assertThat(testNuancePolitique.getCodeNuancePolitique()).isEqualTo(DEFAULT_CODE_NUANCE_POLITIQUE);
        assertThat(testNuancePolitique.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createNuancePolitiqueWithExistingId() throws Exception {
        // Create the NuancePolitique with an existing ID
        nuancePolitique.setId(1L);

        int databaseSizeBeforeCreate = nuancePolitiqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNuancePolitiqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNuancePolitiques() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        // Get all the nuancePolitiqueList
        restNuancePolitiqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nuancePolitique.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeNuancePolitique").value(hasItem(DEFAULT_CODE_NUANCE_POLITIQUE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getNuancePolitique() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        // Get the nuancePolitique
        restNuancePolitiqueMockMvc
            .perform(get(ENTITY_API_URL_ID, nuancePolitique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nuancePolitique.getId().intValue()))
            .andExpect(jsonPath("$.codeNuancePolitique").value(DEFAULT_CODE_NUANCE_POLITIQUE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingNuancePolitique() throws Exception {
        // Get the nuancePolitique
        restNuancePolitiqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNuancePolitique() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();

        // Update the nuancePolitique
        NuancePolitique updatedNuancePolitique = nuancePolitiqueRepository.findById(nuancePolitique.getId()).get();
        // Disconnect from session so that the updates on updatedNuancePolitique are not directly saved in db
        em.detach(updatedNuancePolitique);
        updatedNuancePolitique.codeNuancePolitique(UPDATED_CODE_NUANCE_POLITIQUE).libelle(UPDATED_LIBELLE);

        restNuancePolitiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNuancePolitique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNuancePolitique))
            )
            .andExpect(status().isOk());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
        NuancePolitique testNuancePolitique = nuancePolitiqueList.get(nuancePolitiqueList.size() - 1);
        assertThat(testNuancePolitique.getCodeNuancePolitique()).isEqualTo(UPDATED_CODE_NUANCE_POLITIQUE);
        assertThat(testNuancePolitique.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nuancePolitique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNuancePolitiqueWithPatch() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();

        // Update the nuancePolitique using partial update
        NuancePolitique partialUpdatedNuancePolitique = new NuancePolitique();
        partialUpdatedNuancePolitique.setId(nuancePolitique.getId());

        partialUpdatedNuancePolitique.libelle(UPDATED_LIBELLE);

        restNuancePolitiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNuancePolitique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNuancePolitique))
            )
            .andExpect(status().isOk());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
        NuancePolitique testNuancePolitique = nuancePolitiqueList.get(nuancePolitiqueList.size() - 1);
        assertThat(testNuancePolitique.getCodeNuancePolitique()).isEqualTo(DEFAULT_CODE_NUANCE_POLITIQUE);
        assertThat(testNuancePolitique.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateNuancePolitiqueWithPatch() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();

        // Update the nuancePolitique using partial update
        NuancePolitique partialUpdatedNuancePolitique = new NuancePolitique();
        partialUpdatedNuancePolitique.setId(nuancePolitique.getId());

        partialUpdatedNuancePolitique.codeNuancePolitique(UPDATED_CODE_NUANCE_POLITIQUE).libelle(UPDATED_LIBELLE);

        restNuancePolitiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNuancePolitique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNuancePolitique))
            )
            .andExpect(status().isOk());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
        NuancePolitique testNuancePolitique = nuancePolitiqueList.get(nuancePolitiqueList.size() - 1);
        assertThat(testNuancePolitique.getCodeNuancePolitique()).isEqualTo(UPDATED_CODE_NUANCE_POLITIQUE);
        assertThat(testNuancePolitique.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nuancePolitique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isBadRequest());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNuancePolitique() throws Exception {
        int databaseSizeBeforeUpdate = nuancePolitiqueRepository.findAll().size();
        nuancePolitique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNuancePolitiqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nuancePolitique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NuancePolitique in the database
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNuancePolitique() throws Exception {
        // Initialize the database
        nuancePolitiqueRepository.saveAndFlush(nuancePolitique);

        int databaseSizeBeforeDelete = nuancePolitiqueRepository.findAll().size();

        // Delete the nuancePolitique
        restNuancePolitiqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, nuancePolitique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NuancePolitique> nuancePolitiqueList = nuancePolitiqueRepository.findAll();
        assertThat(nuancePolitiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
