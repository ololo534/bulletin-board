package mail.technopark.bulletinBoard.firebase.authentication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.registration.RegisterFragment;
import mail.technopark.bulletin_board.local_database.entity.User;
import mail.technopark.bulletin_board.local_database.view_model.UserViewModel;

public class AuthFragment extends Fragment {
    private FirebaseHelper helper; // Class with all methods for Firebase
    public static AuthFragment newInstance(){
       return new AuthFragment();
    }
    private UserViewModel mUserViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, user -> {
            if(mUserViewModel.getUser().getValue()!=null)
            {
                String email = user.getEmail();
                String password = user.getPassword();
                helper.auth(email,password);
            }
        });
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        Button registerBtn = view.findViewById(R.id.register_btn);
        Button enterBtn = view.findViewById(R.id.enter_btn);
        TextView restoreTv = view.findViewById(R.id.restore_tv);
        final EditText loginEt = view.findViewById(R.id.login_et);
        final EditText passwordEt = view.findViewById(R.id.password_et);

        registerBtn.setOnClickListener(v -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .addToBackStack(RegisterFragment.class.getSimpleName())
                .commit());

        enterBtn.setOnClickListener(v -> {
            User user = new User(loginEt.getText().toString(), passwordEt.getText().toString());
            helper.auth(loginEt.getText().toString(), passwordEt.getText().toString());
            mUserViewModel.insert(user);
        });

        restoreTv.setOnClickListener(v -> { // Restore password using DialogBox
            final EditText resetEt = new EditText(v.getContext());
            final AlertDialog.Builder resetDialog = new AlertDialog.Builder(v.getContext());
            resetDialog.setTitle("Восстановить пароль?");
            resetDialog.setMessage("Введите Ваш e-mail");
            resetDialog.setView(resetEt);

            resetDialog.setPositiveButton("Да", (dialog, which) -> {
                String email = resetEt.getText().toString();
                helper.getAuth().sendPasswordResetEmail(email);
            });

            resetDialog.setNegativeButton("Нет", (dialog, which) -> { });
            resetDialog.create().show();
        });
        return view;
    }
}
