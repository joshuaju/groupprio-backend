package de.ccd.groupprio.repository.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.ccd.groupprio.domain.data.PrioItem;
import de.ccd.groupprio.domain.data.Submission;

public class SubmissionMapperMongo {

    private SubmissionMapperMongo() {
    }

    public static List<Submission> mapToSubmissionList(DBCursor cursor) {
        List<DBObject> dbObjects = cursor.toArray();
        return dbObjects.stream().map(SubmissionMapperMongo::mapToSubmission).collect(Collectors.toList());
    }

    private static Submission mapToSubmission(DBObject dbObject) {
        BasicDBList dbList = (BasicDBList) dbObject.get("prio_items");
        List<PrioItem> prioItems = dbList.stream().map(obj -> new PrioItem(obj.toString())).collect(Collectors.toList());
        return new Submission(prioItems);
    }
}
