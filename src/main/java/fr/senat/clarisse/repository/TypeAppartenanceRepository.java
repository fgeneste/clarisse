package fr.senat.clarisse.repository;

import fr.senat.clarisse.domain.TypeAppartenance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeAppartenance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAppartenanceRepository extends JpaRepository<TypeAppartenance, Long> {}
