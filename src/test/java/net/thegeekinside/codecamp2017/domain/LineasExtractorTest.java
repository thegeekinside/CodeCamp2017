package net.thegeekinside.codecamp2017.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineasExtractorTest {
    @Test
    public void getLineasTest() {
        LineasExtractor extractor = new LineasExtractor();

        RedMetro redMetro = extractor.extract();

        assertThat(redMetro.lineas.size() == 0).isFalse();
        assertThat(redMetro.estaciones.size() == 162).isTrue();
    }
}
