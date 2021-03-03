package de.ccd.groupprio.domain.logic;

import de.ccd.groupprio.domain.data.Project;

public class IsSubmissionAllowed {

    public static boolean check(Project project, boolean hasClientAlreadySubmitted){
        var allowMultiSubmission = project.isMultiSubmissionAllowed();
        return allowMultiSubmission || !hasClientAlreadySubmitted;
    }
}
