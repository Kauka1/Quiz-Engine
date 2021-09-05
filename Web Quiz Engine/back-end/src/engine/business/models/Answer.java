package engine.business.models;

/**
 * This class holds the correct options for any given question object
 */
public class Answer {

    /**
     * Array that indicated the correct solution to a question
     */
    private Integer[] answer;

    /**
     * Answer constructor
     */
    public Answer() {
        if (this.answer == null)
            this.answer = new Integer[]{};
    }

    /**
     * Answer getter
     * @return Answer array
     */
    public Integer[] getAnswer() {
        return answer;
    }

    /**
     * Answer setter
     * @param answers Answer parameter to set
     */
    public void setAnswer(Integer[] answers) {
        this.answer = answers;
    }
}
