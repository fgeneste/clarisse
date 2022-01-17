package fr.senat.clarisse.repository;

import fr.senat.clarisse.domain.Noms;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Noms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NomsRepository extends JpaRepository<Noms, Long> {}
