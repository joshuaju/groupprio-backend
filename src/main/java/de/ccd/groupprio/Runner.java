package de.ccd.groupprio;

import com.mongodb.DB;
import de.ccd.groupprio.environment.MongoConnector;
import de.ccd.groupprio.environment.SparkServer;
import de.ccd.groupprio.integration.project.create.CreateProjectController;
import de.ccd.groupprio.integration.project.get.GetAllProjectsController;
import de.ccd.groupprio.integration.project.get.GetOneProjectController;
import de.ccd.groupprio.integration.project.state.GetProjectStateController;
import de.ccd.groupprio.integration.submit.SubmitController;
import de.ccd.groupprio.repository.project.ProjectRepository;
import de.ccd.groupprio.repository.project.ProjectRepositoryMongo;
import de.ccd.groupprio.repository.submission.SubmissionRepository;
import de.ccd.groupprio.repository.submission.SubmissionRepositoryMongo;
import de.ccd.groupprio.repository.weight.WeightRepository;
import de.ccd.groupprio.repository.weight.WeightRepositoryMongo;

public class Runner {

    public static void main(String[] args) {
        DB groupprioDB = MongoConnector.connectMongoDb();

        ProjectRepository projectRepository = new ProjectRepositoryMongo(groupprioDB);
        WeightRepository weightRepository = new WeightRepositoryMongo(groupprioDB);
        SubmissionRepository submissionRepository = new SubmissionRepositoryMongo(groupprioDB);

        SparkServer
                .port(8080)
                .enableCors()
                .register(() -> new CreateProjectController(projectRepository))
                .register(() -> new GetOneProjectController(projectRepository))
                .register(() -> new GetAllProjectsController(projectRepository))
                .register(() -> new GetProjectStateController(projectRepository, weightRepository, submissionRepository))
                .register(() -> new SubmitController(submissionRepository, weightRepository, projectRepository))
                .run();
    }

}