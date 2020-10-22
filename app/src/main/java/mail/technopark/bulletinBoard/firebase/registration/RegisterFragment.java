package mail.technopark.bulletinBoard.firebase.registration;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;

public class RegisterFragment extends Fragment {
    FirebaseHelper helper;

    public RegisterFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getFragmentManager(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        if (helper.getAuth().getCurrentUser() != null){
            helper.goToBulletinFragment();
        }

        Button createAccBtn = view.findViewById(R.id.create_acc_btn);

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surname = ((EditText) view.findViewById(R.id.surname_et)).getText().toString();
                String name = ((EditText) view.findViewById(R.id.name_et)).getText().toString();
                String email = ((EditText) view.findViewById(R.id.email_et)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.password_reg_et)).getText().toString();
                String phone = ((EditText) view.findViewById(R.id.phone_et)).getText().toString();
                helper.createAccount(email, password, surname, name, phone);
            }
        });
        return view;
    }
}
