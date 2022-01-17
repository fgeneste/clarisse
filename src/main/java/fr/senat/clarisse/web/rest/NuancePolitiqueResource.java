package fr.senat.clarisse.web.rest;

import fr.senat.clarisse.domain.NuancePolitique;
import fr.senat.clarisse.repository.NuancePolitiqueRepository;
import fr.senat.clarisse.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.senat.clarisse.domain.NuancePolitique}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NuancePolitiqueResource {

    private final Logger log = LoggerFactory.getLogger(NuancePolitiqueResource.class);

    private static final String ENTITY_NAME = "nuancePolitique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NuancePolitiqueRepository nuancePolitiqueRepository;

    public NuancePolitiqueResource(NuancePolitiqueRepository nuancePolitiqueRepository) {
        this.nuancePolitiqueRepository = nuancePolitiqueRepository;
    }

    /**
     * {@code POST  /nuance-politiques} : Create a new nuancePolitique.
     *
     * @param nuancePolitique the nuancePolitique to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nuancePolitique, or with status {@code 400 (Bad Request)} if the nuancePolitique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nuance-politiques")
    public ResponseEntity<NuancePolitique> createNuancePolitique(@RequestBody NuancePolitique nuancePolitique) throws URISyntaxException {
        log.debug("REST request to save NuancePolitique : {}", nuancePolitique);
        if (nuancePolitique.getId() != null) {
            throw new BadRequestAlertException("A new nuancePolitique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NuancePolitique result = nuancePolitiqueRepository.save(nuancePolitique);
        return ResponseEntity
            .created(new URI("/api/nuance-politiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nuance-politiques/:id} : Updates an existing nuancePolitique.
     *
     * @param id the id of the nuancePolitique to save.
     * @param nuancePolitique the nuancePolitique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nuancePolitique,
     * or with status {@code 400 (Bad Request)} if the nuancePolitique is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nuancePolitique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nuance-politiques/{id}")
    public ResponseEntity<NuancePolitique> updateNuancePolitique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NuancePolitique nuancePolitique
    ) throws URISyntaxException {
        log.debug("REST request to update NuancePolitique : {}, {}", id, nuancePolitique);
        if (nuancePolitique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nuancePolitique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nuancePolitiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NuancePolitique result = nuancePolitiqueRepository.save(nuancePolitique);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nuancePolitique.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nuance-politiques/:id} : Partial updates given fields of an existing nuancePolitique, field will ignore if it is null
     *
     * @param id the id of the nuancePolitique to save.
     * @param nuancePolitique the nuancePolitique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nuancePolitique,
     * or with status {@code 400 (Bad Request)} if the nuancePolitique is not valid,
     * or with status {@code 404 (Not Found)} if the nuancePolitique is not found,
     * or with status {@code 500 (Internal Server Error)} if the nuancePolitique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nuance-politiques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NuancePolitique> partialUpdateNuancePolitique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NuancePolitique nuancePolitique
    ) throws URISyntaxException {
        log.debug("REST request to partial update NuancePolitique partially : {}, {}", id, nuancePolitique);
        if (nuancePolitique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nuancePolitique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nuancePolitiqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NuancePolitique> result = nuancePolitiqueRepository
            .findById(nuancePolitique.getId())
            .map(existingNuancePolitique -> {
                if (nuancePolitique.getCodeNuancePolitique() != null) {
                    existingNuancePolitique.setCodeNuancePolitique(nuancePolitique.getCodeNuancePolitique());
                }
                if (nuancePolitique.getLibelle() != null) {
                    existingNuancePolitique.setLibelle(nuancePolitique.getLibelle());
                }

                return existingNuancePolitique;
            })
            .map(nuancePolitiqueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nuancePolitique.getId().toString())
        );
    }

    /**
     * {@code GET  /nuance-politiques} : get all the nuancePolitiques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nuancePolitiques in body.
     */
    @GetMapping("/nuance-politiques")
    public List<NuancePolitique> getAllNuancePolitiques() {
        log.debug("REST request to get all NuancePolitiques");
        return nuancePolitiqueRepository.findAll();
    }

    /**
     * {@code GET  /nuance-politiques/:id} : get the "id" nuancePolitique.
     *
     * @param id the id of the nuancePolitique to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nuancePolitique, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nuance-politiques/{id}")
    public ResponseEntity<NuancePolitique> getNuancePolitique(@PathVariable Long id) {
        log.debug("REST request to get NuancePolitique : {}", id);
        Optional<NuancePolitique> nuancePolitique = nuancePolitiqueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nuancePolitique);
    }

    /**
     * {@code DELETE  /nuance-politiques/:id} : delete the "id" nuancePolitique.
     *
     * @param id the id of the nuancePolitique to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nuance-politiques/{id}")
    public ResponseEntity<Void> deleteNuancePolitique(@PathVariable Long id) {
        log.debug("REST request to delete NuancePolitique : {}", id);
        nuancePolitiqueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
