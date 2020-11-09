package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.Bulletin;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;

public class BulletinFragment extends Fragment {
    private FirebaseHelper helper;
    private ArrayList<Bulletin> bulletins = new ArrayList<>();

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
                //clear last fragment from back stack
                goToAuthFragment();
            }
        });

        // Getting ads.
        helper.getFirestore().collection("bulletins")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Bulletin bulletin = document.toObject(Bulletin.class);
                            bulletins.add(bulletin);
                            Log.d("-------BULLETIN------", bulletin.getName());
                        }
                    } else {
                        Log.d("TAG", "Error on reading docs");
                    }
                });

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RVAdapter adapter = new RVAdapter(bulletins);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void goToAuthFragment(){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AuthFragment())
                .addToBackStack(AuthFragment.class.getSimpleName())
                .commit();
    }
}
