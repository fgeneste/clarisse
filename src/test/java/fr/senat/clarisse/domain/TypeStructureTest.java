package fr.senat.clarisse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.clarisse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeStructureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeStructure.class);
        TypeStructure typeStructure1 = new TypeStructure();
        typeStructure1.setId(1L);
        TypeStructure typeStructure2 = new TypeStructure();
        typeStructure2.setId(typeStructure1.getId());
        assertThat(typeStructure1).isEqualTo(typeStructure2);
        typeStructure2.setId(2L);
        assertThat(typeStructure1).isNotEqualTo(typeStructure2);
        typeStructure1.setId(null);
        assertThat(typeStructure1).isNotEqualTo(typeStructure2);
    }
}
