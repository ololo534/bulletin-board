package mail.technopark.bulletinBoard.firebase;

public class User {
    private String id;
    private String surname;
    private String name;
    private String phone;
    private String email;
    boolean is_phone_visible;
    boolean is_name_visible;
    PhotoSupport photo;

    public User() {}

    public User (String surname, String name, String phone, String email) {
        // Required empty constructor
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
        return is_phone_visible;
    }
    public void setPhoneVisible(boolean flag) {
        this.is_phone_visible = flag;
    }

    public boolean getNameVisible() {
        return is_name_visible;
    }
    public void setNameVisible(boolean flag) {
        this.is_name_visible = flag;
    }

    public PhotoSupport getPhoto() {
        return photo;
    }
    public void setPhoto(PhotoSupport photo) {
        this.photo = photo;
    }
}
