package de.ccd.groupprio.integration.project.create;

import de.ccd.groupprio.repository.project.ProjectRepository;

import static de.ccd.groupprio.integration.util.JsonUtil.json;
import static spark.Spark.post;

public class CreateProjectController {

    public CreateProjectController(ProjectRepository repository) {
        var processor = new CreateProjectProcessor(repository);

        post("/project", (req, res) -> {
            var cmd = CreateProjectCommand.from(req);
            return processor.process(cmd);
        }, json());
    }

}
