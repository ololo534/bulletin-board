package mail.technopark.bulletinBoard.firebase;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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
        else mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) goToBulletinFragment(); // When task complete successfully the BulletinFragment will open
                else Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ // When task complete successfully the data will add into Firestore
                    FirebaseUser fbUser = mAuth.getCurrentUser();
                    Objects.requireNonNull(fbUser).sendEmailVerification(); // Email verification (Link)
                    String userId = fbUser.getUid();

                    Map<String, Object> user = new HashMap<>();
                    user.put("surname", surname);
                    user.put("name", name);
                    user.put("email", email);
                    user.put("phone", phone);

                    mStore.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    goToBulletinFragment();
                }
            }
        });
    }
    public void goToBulletinFragment(){
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new BulletinFragment())
                .addToBackStack(BulletinFragment.class.getSimpleName()).commitAllowingStateLoss();
    }

    private boolean isEmailCorrect(String email){
        Pattern pattern = Pattern.compile(".+@(student\\.)?bmstu\\.ru");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
