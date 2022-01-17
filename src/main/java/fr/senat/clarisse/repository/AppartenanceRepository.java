package fr.senat.clarisse.repository;

import fr.senat.clarisse.domain.Appartenance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Appartenance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartenanceRepository extends JpaRepository<Appartenance, Long> {}
