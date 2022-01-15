package fr.senat.clarisse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.clarisse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NuancePolitiqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NuancePolitique.class);
        NuancePolitique nuancePolitique1 = new NuancePolitique();
        nuancePolitique1.setId(1L);
        NuancePolitique nuancePolitique2 = new NuancePolitique();
        nuancePolitique2.setId(nuancePolitique1.getId());
        assertThat(nuancePolitique1).isEqualTo(nuancePolitique2);
        nuancePolitique2.setId(2L);
        assertThat(nuancePolitique1).isNotEqualTo(nuancePolitique2);
        nuancePolitique1.setId(null);
        assertThat(nuancePolitique1).isNotEqualTo(nuancePolitique2);
    }
}
