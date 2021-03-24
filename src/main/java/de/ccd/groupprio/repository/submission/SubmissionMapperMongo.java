package de.ccd.groupprio.repository.submission;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
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
        BasicDBList dbList = (BasicDBList) submissionDoc.get("prio_items");
        List<PrioItem> prioItems = dbList.stream().map(obj -> new PrioItem(obj.toString())).collect(Collectors.toList());
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
        var submitterDb = (BasicDBObject) submitter.get("client_id");
        return submitterDb.toString();
    }
}
