package de.ccd.groupprio.integration.project.get;

import de.ccd.groupprio.repository.project.ProjectRepository;

import static de.ccd.groupprio.integration.util.JsonUtil.json;
import static spark.Spark.get;

public class GetAllProjectsController {

    public GetAllProjectsController(ProjectRepository projectRepository) {
        var processor = new GetAllProjectsProcessor(projectRepository);

        get("/project", (req, res) -> {
            var qry = GetAllProjectsQuery.from(req);
            return processor.process(qry);
        }, json());
    }
}
