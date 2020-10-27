package lab8;

public class Image {
    public String path;
    public String owner;
    public int votes;

    public Image() {}

    public Image(String path, String owner, int votes) {
        this.path = path;
        this.owner = owner;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", owner='" + owner + '\'' +
                ", votes=" + votes +
                '}';
    }

    public String getPath() {
        return path;
    }

    public String getOwner() {
        return owner;
    }

    public String getVotes() {
        return Integer.toString(votes);
    }
}
