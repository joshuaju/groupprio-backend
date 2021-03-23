package de.ccd.groupprio.repository.submission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;

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
}
