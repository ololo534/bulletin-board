package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.activities.MainActivity;
import mail.technopark.bulletinBoard.firebase.Bulletin;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.PhotoSupport;
import mail.technopark.bulletin_board.local_database.view_model.UserViewModel;

public class PersonalBulletinFragment extends Fragment implements RVAdapterClickable.BulListener {
    private FirebaseHelper helper;
    private final ArrayList<Bulletin> bulletins = new ArrayList<>();
    private UserViewModel mUserViewModel;

    public static PersonalBulletinFragment newInstance(){
        return new PersonalBulletinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
        PhotoSupport photoSupport = new PhotoSupport();
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_bulletin, container, false);
        Button logoutBtn = view.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(v -> {
            if (helper.getAuth().getCurrentUser() != null) {
                helper.getAuth().signOut();
                mUserViewModel.delete();
                getParentFragmentManager().popBackStack("AuthFragment", 0);
            }
        });
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View view) {
        String user = helper.getAuth().getUid();
        helper.getFirestore().collection("bulletins")
                .whereEqualTo("userId", user)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bulletins.clear();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Bulletin bulletin = document.toObject(Bulletin.class);
                            bulletin.setBulletinId(document.getId());
                            bulletins.add(bulletin);
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.rv_personal);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        RVAdapter adapter = new RVAdapter(bulletins);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("TAG", "Error on reading docs");
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setBottomAppBarShow();
    }

    @Override
    public void onBulletinClick(int position) {
        helper.deleteBulletin(bulletins.get(position).getBulletinId());
    }
}
