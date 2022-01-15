package fr.senat.clarisse.repository;

import fr.senat.clarisse.domain.NuancePolitique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NuancePolitique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NuancePolitiqueRepository extends JpaRepository<NuancePolitique, Long> {}
