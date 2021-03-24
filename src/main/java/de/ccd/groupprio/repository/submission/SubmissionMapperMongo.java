package de.ccd.groupprio.repository.submission;

import com.mongodb.client.FindIterable;
import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubmissionMapperMongo {

    private SubmissionMapperMongo() {
    }

    public static List<Submission> mapToSubmissionList(FindIterable<Document> submissionsDocIterable) {
        List<Submission> result = new ArrayList<>();
        for (Document subDoc : submissionsDocIterable) {
            Submission submission = mapToSubmission(subDoc);
            result.add(submission);
        }
        return result;
    }

    private static Submission mapToSubmission(Document submissionDoc) {
        var prioItemNames = submissionDoc.getList("prio_items", String.class);
        List<PrioItem> prioItems = prioItemNames.stream()
                                                .map(PrioItem::new)
                                                .collect(Collectors.toList());
        return new Submission(prioItems);
    }

    public static List<String> mapToSubmitterIds(FindIterable<Document> submitterDocs) {
        List<String> submitterIds = new ArrayList<>();
        for (var submitterDoc : submitterDocs) {
            var submitterId = mapToSubmitterId(submitterDoc);
            submitterIds.add(submitterId);
        }
        return submitterIds;
    }

    private static String mapToSubmitterId(Document submitter) {
        return submitter.getString("client_id");
    }
}
