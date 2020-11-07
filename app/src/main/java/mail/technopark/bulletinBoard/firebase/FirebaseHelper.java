package mail.technopark.bulletinBoard.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.main_bulletin_board.BulletinFragment;

public class FirebaseHelper { // Class for Firebase methods
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mStore;
    private final FragmentManager fragmentManager;
    private final Context context;

    public FirebaseHelper(FragmentManager fragmentManager, Context context){ // Constructor
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        this.fragmentManager = fragmentManager;
        this.context = context;
    }
    // Some getters
    public FirebaseAuth getAuth(){
        return mAuth;
    }

    public FirebaseFirestore getFirestore(){
        return mStore;
    }

    public void auth(String login, String password){ // Auth method
        if (login.isEmpty() || password.isEmpty()) Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
        else mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                goToBulletinFragment();
            } // When task complete successfully the BulletinFragment will open
            else Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        });
    }

    public void createAccount(final String email, String password, final String surname, final String name, final String phone) { // Create account method
        if (!isEmailCorrect(email)) {
            Toast.makeText(context, "Введите почту в формате почты МГТУ", Toast.LENGTH_SHORT).show();
            return; // Validation of email
        }
        if (password.length() < 8) {
            Toast.makeText(context, "Пароль должен состоять более чем из 8 символов", Toast.LENGTH_SHORT).show();
            return; // Validation of password
        }
        if (surname.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){ // When task complete successfully the data will add into Firestore
                FirebaseUser fbUser = mAuth.getCurrentUser();
                Objects.requireNonNull(fbUser).sendEmailVerification(); // Email verification (Link)
                String userId = fbUser.getUid();

                User user = new User();
                user.setSurname(surname);
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setNameVisible(true);
                user.setPhoneVisible(false);

                mStore.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());

                goToBulletinFragment();
            }
        });
    }
    public void goToBulletinFragment(){
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, BulletinFragment.newInstance())
                .addToBackStack(BulletinFragment.class.getSimpleName())
                .commitAllowingStateLoss();
    }

    private boolean isEmailCorrect(String email){
        Pattern pattern = Pattern.compile(".+@(student\\.)?bmstu\\.ru");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public void createBulletin(String userId, String name, String description,
                               String price, String type) {

        if (name.isEmpty() || description.isEmpty() || price.isEmpty() || type.isEmpty()) {
            Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }
        Date date = new Date();

        Bulletin bulletin = new Bulletin();
        bulletin.setUserId(userId);
        bulletin.setName(name);
        bulletin.setDescription(description);
        bulletin.setPrice(price);
        bulletin.setType(type);
        bulletin.setDate(date.toString());
        bulletin.setStatus(true);

        mStore.collection("bulletins").document()
                .set(bulletin)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Объявление создано успешно", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
