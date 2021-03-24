package de.ccd.groupprio.repository.submission;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;

import java.util.List;
import java.util.stream.Collectors;

public class SubmissionMapperMongo {

    private SubmissionMapperMongo() {
    }

    public static List<Submission> mapToSubmissionList(DBCursor submissions) {
        List<DBObject> submissionList = submissions.toArray();
        return submissionList.stream()
                             .map(SubmissionMapperMongo::mapToSubmission)
                             .collect(Collectors.toList());
    }

    private static Submission mapToSubmission(DBObject submission) {
        BasicDBList prioItemList = (BasicDBList) submission.get("prio_items");
        List<PrioItem> prioItems = prioItemList.stream()
                                         .map(obj -> new PrioItem(obj.toString()))
                                         .collect(Collectors.toList());
        return new Submission(prioItems);
    }

    public static List<String> mapToSubmitterIds(DBCursor submitters) {
        List<DBObject> submitterList = submitters.toArray();
        return submitterList.stream()
                     .map(SubmissionMapperMongo::mapToSubmitterId)
                     .collect(Collectors.toList());

    }

    private static String mapToSubmitterId(DBObject submitter) {
        var submitterDb = (BasicDBObject) submitter.get("client_id");
        return submitterDb.toString();
    }
}
