package de.ccd.groupprio.integration.submit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SubmitResponse {
    final boolean success;
    final boolean isSubmissionAllowed;
}
