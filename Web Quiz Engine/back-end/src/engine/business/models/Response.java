package engine.business.models;

/**
 * Response class provides objects for if an answer is correct or incorrect
 */
public class Response {
    private boolean success;
    private String feedback;
    private boolean[] correct;

    public Response(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public Response(boolean success, String feedback, boolean[] correct) {
        this.success = success;
        this.feedback = feedback;
        this.correct = correct;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean[] getCorrect() {
        return correct;
    }

    public void setCorrect(boolean[] correct) {
        this.correct = correct;
    }
}
