package app.model.bodies;

public class AiRequestBody {
    private String input = null;

    public AiRequestBody() {
    }
    
    public AiRequestBody(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String toJsonString() {
        return String.format("{\"input\":\"%s\"}", input);
    }
}
