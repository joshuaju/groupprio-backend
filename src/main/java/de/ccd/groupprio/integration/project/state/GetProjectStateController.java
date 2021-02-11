package de.ccd.groupprio.integration.project.state;

import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;

import static de.ccd.groupprio.integration.util.JsonUtil.json;
import static spark.Spark.get;

public class GetProjectStateController {

    public GetProjectStateController(ProjectRepository pRepo, WeightRepository wRepo, SubmissionRepository sRepo) {
        var processor = new GetProjectStateProcessor(pRepo, wRepo, sRepo);
        get("/project/:id/prioritization", (req, res) -> {
            var qry = ProjectStateQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
