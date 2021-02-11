package de.ccd.groupprio.integration.submit;

import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.weight.WeightRepository;

import static de.ccd.groupprio.integration.util.JsonUtil.json;

public class SubmitController {

    public SubmitController(SubmissionRepository sRepo, WeightRepository wRepo, ProjectRepository pRepo) {
        var processor = new SubmitProcessor(sRepo, wRepo, pRepo);
        spark.Spark.post("project/:id/submission", (req, res) -> {
            var cmd = SubmitCommand.from(req);
            return processor.process(cmd);
        }, json());
    }
}
