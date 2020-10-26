package mail.technopark.bulletinBoard.firebase;

public class User {
    private String userId;
    private String surname;
    private String name;
    private String phone;
    private String email;
    boolean isPhoneVisible;
    boolean isNameVisible;
    PhotoSupport photo;

    public User() {}

    public User(String userId, String surname, String name, String phone, String email, boolean isNameVisible, boolean isPhoneVisible, PhotoSupport photo) {
        // Required empty constructor
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String id) {
        this.userId = id;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getPhoneVisible() {
        return isPhoneVisible;
    }
    public void setPhoneVisible(boolean flag) {
        this.isPhoneVisible = flag;
    }

    public boolean getNameVisible() {
        return isNameVisible;
    }
    public void setNameVisible(boolean flag) {
        this.isNameVisible = flag;
    }

    public PhotoSupport getPhoto() {
        return photo;
    }
    public void setPhoto(PhotoSupport photo) {
        this.photo = photo;
    }
}
