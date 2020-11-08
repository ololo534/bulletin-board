package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;

public class BulletinFragment extends Fragment {
    private FirebaseHelper helper;

    public static BulletinFragment newInstance(){
        return new BulletinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);
        Button logoutBtn = view.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(v -> {
            if (helper.getAuth().getCurrentUser() != null) {
                helper.getAuth().signOut();
                getParentFragmentManager().popBackStack("AuthFragment", 0);
            }
        });
        return view;
    }
}
