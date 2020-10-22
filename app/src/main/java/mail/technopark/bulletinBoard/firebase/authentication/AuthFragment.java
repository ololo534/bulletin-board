package mail.technopark.bulletinBoard.firebase.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.registration.RegisterFragment;

public class AuthFragment extends Fragment {

    private FirebaseHelper helper; // Class with all methods for Firebase
    public AuthFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getFragmentManager(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        Button registerBtn = view.findViewById(R.id.register_btn);
        Button enterBtn = view.findViewById(R.id.enter_btn);
        TextView restoreTv = view.findViewById(R.id.restore_tv);
        final EditText loginEt = view.findViewById(R.id.login_et);
        final EditText passwordEt = view.findViewById(R.id.password_et);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new RegisterFragment())
                        .addToBackStack(RegisterFragment.class.getSimpleName()).commit();
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.auth(loginEt.getText().toString(), passwordEt.getText().toString());
            }
        });

        restoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Restore password using DialogBox
                final EditText resetEt = new EditText(v.getContext());
                final AlertDialog.Builder resetDialog = new AlertDialog.Builder(v.getContext());
                resetDialog.setTitle("Восстановить пароль?");
                resetDialog.setMessage("Введите Ваш e-mail");
                resetDialog.setView(resetEt);

                resetDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = resetEt.getText().toString();
                        helper.getAuth().sendPasswordResetEmail(email);
                    }
                });

                resetDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                resetDialog.create().show();
            }
        });
        return view;
    }
}
