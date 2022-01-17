package fr.senat.clarisse.web.rest;

import fr.senat.clarisse.domain.TypeStructure;
import fr.senat.clarisse.repository.TypeStructureRepository;
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
 * REST controller for managing {@link fr.senat.clarisse.domain.TypeStructure}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeStructureResource {

    private final Logger log = LoggerFactory.getLogger(TypeStructureResource.class);

    private static final String ENTITY_NAME = "typeStructure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeStructureRepository typeStructureRepository;

    public TypeStructureResource(TypeStructureRepository typeStructureRepository) {
        this.typeStructureRepository = typeStructureRepository;
    }

    /**
     * {@code POST  /type-structures} : Create a new typeStructure.
     *
     * @param typeStructure the typeStructure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeStructure, or with status {@code 400 (Bad Request)} if the typeStructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-structures")
    public ResponseEntity<TypeStructure> createTypeStructure(@RequestBody TypeStructure typeStructure) throws URISyntaxException {
        log.debug("REST request to save TypeStructure : {}", typeStructure);
        if (typeStructure.getId() != null) {
            throw new BadRequestAlertException("A new typeStructure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeStructure result = typeStructureRepository.save(typeStructure);
        return ResponseEntity
            .created(new URI("/api/type-structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-structures/:id} : Updates an existing typeStructure.
     *
     * @param id the id of the typeStructure to save.
     * @param typeStructure the typeStructure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeStructure,
     * or with status {@code 400 (Bad Request)} if the typeStructure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeStructure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-structures/{id}")
    public ResponseEntity<TypeStructure> updateTypeStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeStructure typeStructure
    ) throws URISyntaxException {
        log.debug("REST request to update TypeStructure : {}, {}", id, typeStructure);
        if (typeStructure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeStructure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeStructureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeStructure result = typeStructureRepository.save(typeStructure);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeStructure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-structures/:id} : Partial updates given fields of an existing typeStructure, field will ignore if it is null
     *
     * @param id the id of the typeStructure to save.
     * @param typeStructure the typeStructure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeStructure,
     * or with status {@code 400 (Bad Request)} if the typeStructure is not valid,
     * or with status {@code 404 (Not Found)} if the typeStructure is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeStructure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-structures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeStructure> partialUpdateTypeStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeStructure typeStructure
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeStructure partially : {}, {}", id, typeStructure);
        if (typeStructure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeStructure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeStructureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeStructure> result = typeStructureRepository
            .findById(typeStructure.getId())
            .map(existingTypeStructure -> {
                if (typeStructure.getCodeTypeStructure() != null) {
                    existingTypeStructure.setCodeTypeStructure(typeStructure.getCodeTypeStructure());
                }
                if (typeStructure.getLibelle() != null) {
                    existingTypeStructure.setLibelle(typeStructure.getLibelle());
                }
                if (typeStructure.getLibelleCourt() != null) {
                    existingTypeStructure.setLibelleCourt(typeStructure.getLibelleCourt());
                }
                if (typeStructure.getLibellePluriel() != null) {
                    existingTypeStructure.setLibellePluriel(typeStructure.getLibellePluriel());
                }
                if (typeStructure.getUrlComplete() != null) {
                    existingTypeStructure.setUrlComplete(typeStructure.getUrlComplete());
                }
                if (typeStructure.getUrlSimplifie() != null) {
                    existingTypeStructure.setUrlSimplifie(typeStructure.getUrlSimplifie());
                }
                if (typeStructure.getOrdre() != null) {
                    existingTypeStructure.setOrdre(typeStructure.getOrdre());
                }

                return existingTypeStructure;
            })
            .map(typeStructureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeStructure.getId().toString())
        );
    }

    /**
     * {@code GET  /type-structures} : get all the typeStructures.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeStructures in body.
     */
    @GetMapping("/type-structures")
    public List<TypeStructure> getAllTypeStructures(@RequestParam(required = false) String filter) {
        if ("structure-is-null".equals(filter)) {
            log.debug("REST request to get all TypeStructures where structure is null");
            return StreamSupport
                .stream(typeStructureRepository.findAll().spliterator(), false)
                .filter(typeStructure -> typeStructure.getStructure() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TypeStructures");
        return typeStructureRepository.findAll();
    }

    /**
     * {@code GET  /type-structures/:id} : get the "id" typeStructure.
     *
     * @param id the id of the typeStructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeStructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-structures/{id}")
    public ResponseEntity<TypeStructure> getTypeStructure(@PathVariable Long id) {
        log.debug("REST request to get TypeStructure : {}", id);
        Optional<TypeStructure> typeStructure = typeStructureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeStructure);
    }

    /**
     * {@code DELETE  /type-structures/:id} : delete the "id" typeStructure.
     *
     * @param id the id of the typeStructure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-structures/{id}")
    public ResponseEntity<Void> deleteTypeStructure(@PathVariable Long id) {
        log.debug("REST request to delete TypeStructure : {}", id);
        typeStructureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
