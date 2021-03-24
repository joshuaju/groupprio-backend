package de.ccd.groupprio.integration.project.get.one;

import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;

import static de.ccd.groupprio.integration.util.JsonUtil.json;
import static spark.Spark.get;

public class GetOneProjectController {

    public GetOneProjectController(ProjectRepository projectRepository) {
        var processor = new GetOneProjectProcessor(projectRepository);

        get("/project/:id", (req, res) -> {
            var qry = GetOneProjectQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
