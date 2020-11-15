package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.Bulletin;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletin_board.local_database.view_model.UserViewModel;

public class BulletinFragment extends Fragment {
    private FirebaseHelper helper;
    private final ArrayList<Bulletin> bulletins = new ArrayList<>();
    private UserViewModel mUserViewModel;
    //private String userName;

    public static BulletinFragment newInstance(){
        return new BulletinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);
        Button logoutBtn = view.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(v -> {
            if (helper.getAuth().getCurrentUser() != null) {
                helper.getAuth().signOut();
                mUserViewModel.delete();
                getParentFragmentManager().popBackStack("AuthFragment", 0);
            }
        });

        // Getting ads.
        helper.getFirestore().collection("bulletins")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Bulletin bulletin = document.toObject(Bulletin.class);
                            bulletins.add(bulletin);
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.rv);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                        RVAdapter adapter = new RVAdapter(bulletins);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("TAG", "Error on reading docs");
                    }
                });
        return view;
    }
}
