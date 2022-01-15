package fr.senat.clarisse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.clarisse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NomsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noms.class);
        Noms noms1 = new Noms();
        noms1.setId(1L);
        Noms noms2 = new Noms();
        noms2.setId(noms1.getId());
        assertThat(noms1).isEqualTo(noms2);
        noms2.setId(2L);
        assertThat(noms1).isNotEqualTo(noms2);
        noms1.setId(null);
        assertThat(noms1).isNotEqualTo(noms2);
    }
}
