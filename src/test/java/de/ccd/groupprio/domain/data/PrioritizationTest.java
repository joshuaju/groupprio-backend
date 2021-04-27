package de.ccd.groupprio.domain.data;

import de.ccd.groupprio.domain.logic.Prioritization;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PrioritizationTest {

    public static final PrioItem A = new PrioItem("A");
    public static final PrioItem B = new PrioItem("B");
    public static final PrioItem C = new PrioItem("C");
    public static final PrioItem D = new PrioItem("D");

    public static final String WEIGHED_A = "A";
    public static final String WEIGHED_B = "B";
    public static final String WEIGHED_C = "C";
    public static final String WEIGHED_D = "D";

    @RepeatedTest(100)
    void average() {
        var sub1 = new Submission(List.of(D, A, B, C));
        var sub2 = new Submission(List.of(D, B, A, C));
        //var sub3 = new Submission(List.of(C, B, A, D));

        var weightedItems = Prioritization.averageSubmissions(List.of(sub1, sub2));

        assertThat(weightedItems)
                  .containsExactly(WEIGHED_D, WEIGHED_A, WEIGHED_B, WEIGHED_C);
    }
}