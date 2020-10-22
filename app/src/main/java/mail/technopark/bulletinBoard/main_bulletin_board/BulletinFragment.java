package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;

public class BulletinFragment extends Fragment {
    private FirebaseHelper helper;

    public BulletinFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getFragmentManager(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);

        Button logoutBtn = view.findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getAuth().getCurrentUser() != null) {
                    helper.getAuth().signOut();
                    goToAuthFragment();
                }
            }
        });
        return view;
    }

    private void goToAuthFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AuthFragment())
                .addToBackStack(AuthFragment.class.getSimpleName()).commit();
    }
}
