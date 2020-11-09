package mail.technopark.bulletinBoard.firebase;

public class Bulletin {
    private String bulletinId;
    private String userId;
    private String name;
    private String description;
    private String price;
    private String type;
    private String date;
    private boolean status;
    private String userName;
    private boolean userVisibility;


    public Bulletin() {}

    public Bulletin(String userId, String name, String description, String price, String type) {
        // Required empty;
    }

    public String getBulletinId() {return bulletinId;}
    public void setBulletinId(String id) {this.bulletinId = id;}

    public String getUserId() {return userId;}
    public void setUserId(String id) {this.userId = id;}

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

    public boolean getStatus() {return status;}
    public void setStatus(boolean status) {this.status = status;}

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getUserVisibility() {
        return userVisibility;
    }
    public void setUserVisibility(boolean userVisibility) {
        this.userVisibility = userVisibility;
    }
}
