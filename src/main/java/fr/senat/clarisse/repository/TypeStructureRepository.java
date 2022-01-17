package fr.senat.clarisse.repository;

import fr.senat.clarisse.domain.TypeStructure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeStructure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeStructureRepository extends JpaRepository<TypeStructure, Long> {}
