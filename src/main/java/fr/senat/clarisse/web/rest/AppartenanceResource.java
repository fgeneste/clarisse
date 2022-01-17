package fr.senat.clarisse.web.rest;

import fr.senat.clarisse.domain.Appartenance;
import fr.senat.clarisse.repository.AppartenanceRepository;
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
 * REST controller for managing {@link fr.senat.clarisse.domain.Appartenance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppartenanceResource {

    private final Logger log = LoggerFactory.getLogger(AppartenanceResource.class);

    private static final String ENTITY_NAME = "appartenance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppartenanceRepository appartenanceRepository;

    public AppartenanceResource(AppartenanceRepository appartenanceRepository) {
        this.appartenanceRepository = appartenanceRepository;
    }

    /**
     * {@code POST  /appartenances} : Create a new appartenance.
     *
     * @param appartenance the appartenance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appartenance, or with status {@code 400 (Bad Request)} if the appartenance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appartenances")
    public ResponseEntity<Appartenance> createAppartenance(@RequestBody Appartenance appartenance) throws URISyntaxException {
        log.debug("REST request to save Appartenance : {}", appartenance);
        if (appartenance.getId() != null) {
            throw new BadRequestAlertException("A new appartenance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appartenance result = appartenanceRepository.save(appartenance);
        return ResponseEntity
            .created(new URI("/api/appartenances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appartenances/:id} : Updates an existing appartenance.
     *
     * @param id the id of the appartenance to save.
     * @param appartenance the appartenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartenance,
     * or with status {@code 400 (Bad Request)} if the appartenance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appartenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appartenances/{id}")
    public ResponseEntity<Appartenance> updateAppartenance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appartenance appartenance
    ) throws URISyntaxException {
        log.debug("REST request to update Appartenance : {}, {}", id, appartenance);
        if (appartenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Appartenance result = appartenanceRepository.save(appartenance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appartenance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appartenances/:id} : Partial updates given fields of an existing appartenance, field will ignore if it is null
     *
     * @param id the id of the appartenance to save.
     * @param appartenance the appartenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartenance,
     * or with status {@code 400 (Bad Request)} if the appartenance is not valid,
     * or with status {@code 404 (Not Found)} if the appartenance is not found,
     * or with status {@code 500 (Internal Server Error)} if the appartenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appartenances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appartenance> partialUpdateAppartenance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appartenance appartenance
    ) throws URISyntaxException {
        log.debug("REST request to partial update Appartenance partially : {}, {}", id, appartenance);
        if (appartenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appartenance> result = appartenanceRepository
            .findById(appartenance.getId())
            .map(existingAppartenance -> {
                if (appartenance.getMatricule() != null) {
                    existingAppartenance.setMatricule(appartenance.getMatricule());
                }
                if (appartenance.getDateDebut() != null) {
                    existingAppartenance.setDateDebut(appartenance.getDateDebut());
                }
                if (appartenance.getDateFin() != null) {
                    existingAppartenance.setDateFin(appartenance.getDateFin());
                }
                if (appartenance.getDateElection() != null) {
                    existingAppartenance.setDateElection(appartenance.getDateElection());
                }
                if (appartenance.getObservation() != null) {
                    existingAppartenance.setObservation(appartenance.getObservation());
                }
                if (appartenance.getDepartement() != null) {
                    existingAppartenance.setDepartement(appartenance.getDepartement());
                }
                if (appartenance.getLibelle() != null) {
                    existingAppartenance.setLibelle(appartenance.getLibelle());
                }
                if (appartenance.getArticle() != null) {
                    existingAppartenance.setArticle(appartenance.getArticle());
                }
                if (appartenance.getMotifDeDebut() != null) {
                    existingAppartenance.setMotifDeDebut(appartenance.getMotifDeDebut());
                }
                if (appartenance.getCommentaireDeChangement() != null) {
                    existingAppartenance.setCommentaireDeChangement(appartenance.getCommentaireDeChangement());
                }

                return existingAppartenance;
            })
            .map(appartenanceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appartenance.getId().toString())
        );
    }

    /**
     * {@code GET  /appartenances} : get all the appartenances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appartenances in body.
     */
    @GetMapping("/appartenances")
    public List<Appartenance> getAllAppartenances() {
        log.debug("REST request to get all Appartenances");
        return appartenanceRepository.findAll();
    }

    /**
     * {@code GET  /appartenances/:id} : get the "id" appartenance.
     *
     * @param id the id of the appartenance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appartenance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appartenances/{id}")
    public ResponseEntity<Appartenance> getAppartenance(@PathVariable Long id) {
        log.debug("REST request to get Appartenance : {}", id);
        Optional<Appartenance> appartenance = appartenanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appartenance);
    }

    /**
     * {@code DELETE  /appartenances/:id} : delete the "id" appartenance.
     *
     * @param id the id of the appartenance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appartenances/{id}")
    public ResponseEntity<Void> deleteAppartenance(@PathVariable Long id) {
        log.debug("REST request to delete Appartenance : {}", id);
        appartenanceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
