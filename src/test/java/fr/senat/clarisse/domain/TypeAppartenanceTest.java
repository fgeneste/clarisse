package fr.senat.clarisse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.clarisse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeAppartenanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAppartenance.class);
        TypeAppartenance typeAppartenance1 = new TypeAppartenance();
        typeAppartenance1.setId(1L);
        TypeAppartenance typeAppartenance2 = new TypeAppartenance();
        typeAppartenance2.setId(typeAppartenance1.getId());
        assertThat(typeAppartenance1).isEqualTo(typeAppartenance2);
        typeAppartenance2.setId(2L);
        assertThat(typeAppartenance1).isNotEqualTo(typeAppartenance2);
        typeAppartenance1.setId(null);
        assertThat(typeAppartenance1).isNotEqualTo(typeAppartenance2);
    }
}
