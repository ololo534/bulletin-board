package mail.technopark.bulletinBoard.firebase.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletin_board.local_database.entity.User;
import mail.technopark.bulletin_board.local_database.view_model.UserViewModel;

public class RegisterFragment extends Fragment {
    FirebaseHelper helper;
    private UserViewModel mUserViewModel;

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        if (helper.getAuth().getCurrentUser() != null) helper.getAuth().signOut();
        Button createAccBtn = view.findViewById(R.id.create_acc_btn);
        createAccBtn.setOnClickListener(v -> {
            String surname = ((EditText) view.findViewById(R.id.surname_et)).getText().toString();
            String name = ((EditText) view.findViewById(R.id.name_et)).getText().toString();
            String email = ((EditText) view.findViewById(R.id.email_et)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.password_reg_et)).getText().toString();
            String phone = ((EditText) view.findViewById(R.id.phone_et)).getText().toString();
            helper.createAccount(email, password, surname, name, phone);
            User user = new User(email, password);
            mUserViewModel.insert(user);
        });
        return view;
    }
}
