package exam;

public class Post {
    public String user;
    public int topicid;
    public String text;
    public int date;

    public Post() {}

    public Post(String user, int topicid, String text, int date) {
        this.user = user;
        this.topicid = topicid;
        this.text = text;
        this.date = date;
    }

    public String getUser() {
        return user;
    }
    public String getText() { return text; }
    public int getTopicid() { return topicid; }
    public int getDate() {
        return date;
    }
}
