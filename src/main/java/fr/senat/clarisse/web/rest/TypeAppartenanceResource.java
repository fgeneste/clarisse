package fr.senat.clarisse.web.rest;

import fr.senat.clarisse.domain.TypeAppartenance;
import fr.senat.clarisse.repository.TypeAppartenanceRepository;
import fr.senat.clarisse.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.senat.clarisse.domain.TypeAppartenance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeAppartenanceResource {

    private final Logger log = LoggerFactory.getLogger(TypeAppartenanceResource.class);

    private static final String ENTITY_NAME = "typeAppartenance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAppartenanceRepository typeAppartenanceRepository;

    public TypeAppartenanceResource(TypeAppartenanceRepository typeAppartenanceRepository) {
        this.typeAppartenanceRepository = typeAppartenanceRepository;
    }

    /**
     * {@code POST  /type-appartenances} : Create a new typeAppartenance.
     *
     * @param typeAppartenance the typeAppartenance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAppartenance, or with status {@code 400 (Bad Request)} if the typeAppartenance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-appartenances")
    public ResponseEntity<TypeAppartenance> createTypeAppartenance(@RequestBody TypeAppartenance typeAppartenance)
        throws URISyntaxException {
        log.debug("REST request to save TypeAppartenance : {}", typeAppartenance);
        if (typeAppartenance.getId() != null) {
            throw new BadRequestAlertException("A new typeAppartenance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAppartenance result = typeAppartenanceRepository.save(typeAppartenance);
        return ResponseEntity
            .created(new URI("/api/type-appartenances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-appartenances/:id} : Updates an existing typeAppartenance.
     *
     * @param id the id of the typeAppartenance to save.
     * @param typeAppartenance the typeAppartenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAppartenance,
     * or with status {@code 400 (Bad Request)} if the typeAppartenance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAppartenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-appartenances/{id}")
    public ResponseEntity<TypeAppartenance> updateTypeAppartenance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAppartenance typeAppartenance
    ) throws URISyntaxException {
        log.debug("REST request to update TypeAppartenance : {}, {}", id, typeAppartenance);
        if (typeAppartenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAppartenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAppartenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeAppartenance result = typeAppartenanceRepository.save(typeAppartenance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAppartenance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-appartenances/:id} : Partial updates given fields of an existing typeAppartenance, field will ignore if it is null
     *
     * @param id the id of the typeAppartenance to save.
     * @param typeAppartenance the typeAppartenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAppartenance,
     * or with status {@code 400 (Bad Request)} if the typeAppartenance is not valid,
     * or with status {@code 404 (Not Found)} if the typeAppartenance is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeAppartenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-appartenances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeAppartenance> partialUpdateTypeAppartenance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAppartenance typeAppartenance
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeAppartenance partially : {}, {}", id, typeAppartenance);
        if (typeAppartenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAppartenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAppartenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeAppartenance> result = typeAppartenanceRepository
            .findById(typeAppartenance.getId())
            .map(existingTypeAppartenance -> {
                if (typeAppartenance.getTypeAppartenance() != null) {
                    existingTypeAppartenance.setTypeAppartenance(typeAppartenance.getTypeAppartenance());
                }
                if (typeAppartenance.getLibelle() != null) {
                    existingTypeAppartenance.setLibelle(typeAppartenance.getLibelle());
                }

                return existingTypeAppartenance;
            })
            .map(typeAppartenanceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAppartenance.getId().toString())
        );
    }

    /**
     * {@code GET  /type-appartenances} : get all the typeAppartenances.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAppartenances in body.
     */
    @GetMapping("/type-appartenances")
    public List<TypeAppartenance> getAllTypeAppartenances(@RequestParam(required = false) String filter) {
        if ("appartenance-is-null".equals(filter)) {
            log.debug("REST request to get all TypeAppartenances where appartenance is null");
            return StreamSupport
                .stream(typeAppartenanceRepository.findAll().spliterator(), false)
                .filter(typeAppartenance -> typeAppartenance.getAppartenance() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TypeAppartenances");
        return typeAppartenanceRepository.findAll();
    }

    /**
     * {@code GET  /type-appartenances/:id} : get the "id" typeAppartenance.
     *
     * @param id the id of the typeAppartenance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAppartenance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-appartenances/{id}")
    public ResponseEntity<TypeAppartenance> getTypeAppartenance(@PathVariable Long id) {
        log.debug("REST request to get TypeAppartenance : {}", id);
        Optional<TypeAppartenance> typeAppartenance = typeAppartenanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeAppartenance);
    }

    /**
     * {@code DELETE  /type-appartenances/:id} : delete the "id" typeAppartenance.
     *
     * @param id the id of the typeAppartenance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-appartenances/{id}")
    public ResponseEntity<Void> deleteTypeAppartenance(@PathVariable Long id) {
        log.debug("REST request to delete TypeAppartenance : {}", id);
        typeAppartenanceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
