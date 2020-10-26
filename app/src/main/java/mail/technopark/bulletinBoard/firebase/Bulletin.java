package mail.technopark.bulletinBoard.firebase;

public class Bulletin {
    private String bulletinId;
    private String userId;
    private String name;
    private String description;
    private String price;
    private String type;
    private String date;
    private PhotoSupport photo;
    private String status;

    public Bulletin() {}

    public Bulletin(String id, String userId, String name, String description, String price,
                    String type, String date, PhotoSupport photo, String status) {
        // Required empty;
    }

    public String getBulletinId() {return bulletinId;}
    public void setBulletinId(String id) {this.bulletinId = id;}

    public String getUserId() {return userId;}
    public void setUserId(String id) {this.userId = userId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getPrice() {return price;}
    public void setPrice(String price) {this.price = price;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public PhotoSupport getPhoto() {
        return photo;
    }
    public void setPhoto(PhotoSupport photo) {
        this.photo = photo;
    }

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
