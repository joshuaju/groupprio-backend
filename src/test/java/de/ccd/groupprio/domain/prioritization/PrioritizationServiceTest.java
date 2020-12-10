package de.ccd.groupprio.domain.prioritization;

import de.ccd.groupprio.domain.submission.PrioItem;
import de.ccd.groupprio.domain.submission.Submission;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PrioritizationServiceTest {

    public static final PrioItem A = new PrioItem("A");
    public static final PrioItem B = new PrioItem("B");
    public static final PrioItem C = new PrioItem("C");
    public static final PrioItem D = new PrioItem("D");

    public static final WeightedItem WEIGHED_A = new WeightedItem("A");
    public static final WeightedItem WEIGHED_B = new WeightedItem("B");
    public static final WeightedItem WEIGHED_C = new WeightedItem("C");
    public static final WeightedItem WEIGHED_D = new WeightedItem("D");

    @Test
    void average() {
        var sub1 = new Submission(List.of(A, B, C, D));
        var sub2 = new Submission(List.of(C, B, A, D));
        var sub3 = new Submission(List.of(C, B, A, D));

        var weightedItems = PrioritizationService.averageSubmissions(List.of(sub1, sub2, sub3));

        assertThat(weightedItems)
                  .containsExactly(WEIGHED_C, WEIGHED_B, WEIGHED_A, WEIGHED_D);
    }
}