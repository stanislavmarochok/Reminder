package sample;

public class Note {
    private String username;
    private String text;
    private String data_time;

    public Note(String username, String text, String data_time) {
        this.username = username;
        this.text = text;
        this.data_time = data_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData_time() {
        return data_time;
    }

    public void setData_time(String data_time) {
        this.data_time = data_time;
    }

    public Note() {}
}
