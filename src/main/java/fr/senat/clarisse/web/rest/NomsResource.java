package fr.senat.clarisse.web.rest;

import fr.senat.clarisse.domain.Noms;
import fr.senat.clarisse.repository.NomsRepository;
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
 * REST controller for managing {@link fr.senat.clarisse.domain.Noms}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NomsResource {

    private final Logger log = LoggerFactory.getLogger(NomsResource.class);

    private static final String ENTITY_NAME = "noms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NomsRepository nomsRepository;

    public NomsResource(NomsRepository nomsRepository) {
        this.nomsRepository = nomsRepository;
    }

    /**
     * {@code POST  /noms} : Create a new noms.
     *
     * @param noms the noms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noms, or with status {@code 400 (Bad Request)} if the noms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/noms")
    public ResponseEntity<Noms> createNoms(@RequestBody Noms noms) throws URISyntaxException {
        log.debug("REST request to save Noms : {}", noms);
        if (noms.getId() != null) {
            throw new BadRequestAlertException("A new noms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Noms result = nomsRepository.save(noms);
        return ResponseEntity
            .created(new URI("/api/noms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /noms/:id} : Updates an existing noms.
     *
     * @param id the id of the noms to save.
     * @param noms the noms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noms,
     * or with status {@code 400 (Bad Request)} if the noms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/noms/{id}")
    public ResponseEntity<Noms> updateNoms(@PathVariable(value = "id", required = false) final Long id, @RequestBody Noms noms)
        throws URISyntaxException {
        log.debug("REST request to update Noms : {}, {}", id, noms);
        if (noms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noms.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Noms result = nomsRepository.save(noms);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noms.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /noms/:id} : Partial updates given fields of an existing noms, field will ignore if it is null
     *
     * @param id the id of the noms to save.
     * @param noms the noms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noms,
     * or with status {@code 400 (Bad Request)} if the noms is not valid,
     * or with status {@code 404 (Not Found)} if the noms is not found,
     * or with status {@code 500 (Internal Server Error)} if the noms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/noms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Noms> partialUpdateNoms(@PathVariable(value = "id", required = false) final Long id, @RequestBody Noms noms)
        throws URISyntaxException {
        log.debug("REST request to partial update Noms partially : {}, {}", id, noms);
        if (noms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noms.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Noms> result = nomsRepository
            .findById(noms.getId())
            .map(existingNoms -> {
                if (noms.getQualite() != null) {
                    existingNoms.setQualite(noms.getQualite());
                }
                if (noms.getNomUsuel() != null) {
                    existingNoms.setNomUsuel(noms.getNomUsuel());
                }
                if (noms.getPrenomUsuel() != null) {
                    existingNoms.setPrenomUsuel(noms.getPrenomUsuel());
                }
                if (noms.getNomPatronymique() != null) {
                    existingNoms.setNomPatronymique(noms.getNomPatronymique());
                }
                if (noms.getPrenomCivil() != null) {
                    existingNoms.setPrenomCivil(noms.getPrenomCivil());
                }
                if (noms.getNomMarital() != null) {
                    existingNoms.setNomMarital(noms.getNomMarital());
                }
                if (noms.getNomDistinctif() != null) {
                    existingNoms.setNomDistinctif(noms.getNomDistinctif());
                }
                if (noms.getNomMajuscule() != null) {
                    existingNoms.setNomMajuscule(noms.getNomMajuscule());
                }
                if (noms.getNomTechnique() != null) {
                    existingNoms.setNomTechnique(noms.getNomTechnique());
                }
                if (noms.getFeminisation() != null) {
                    existingNoms.setFeminisation(noms.getFeminisation());
                }
                if (noms.getDateDebut() != null) {
                    existingNoms.setDateDebut(noms.getDateDebut());
                }
                if (noms.getDateFin() != null) {
                    existingNoms.setDateFin(noms.getDateFin());
                }

                return existingNoms;
            })
            .map(nomsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noms.getId().toString())
        );
    }

    /**
     * {@code GET  /noms} : get all the noms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noms in body.
     */
    @GetMapping("/noms")
    public List<Noms> getAllNoms() {
        log.debug("REST request to get all Noms");
        return nomsRepository.findAll();
    }

    /**
     * {@code GET  /noms/:id} : get the "id" noms.
     *
     * @param id the id of the noms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noms/{id}")
    public ResponseEntity<Noms> getNoms(@PathVariable Long id) {
        log.debug("REST request to get Noms : {}", id);
        Optional<Noms> noms = nomsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(noms);
    }

    /**
     * {@code DELETE  /noms/:id} : delete the "id" noms.
     *
     * @param id the id of the noms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/noms/{id}")
    public ResponseEntity<Void> deleteNoms(@PathVariable Long id) {
        log.debug("REST request to delete Noms : {}", id);
        nomsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
