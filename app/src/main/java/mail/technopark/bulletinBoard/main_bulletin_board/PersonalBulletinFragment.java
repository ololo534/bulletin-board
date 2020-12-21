package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.activities.MainActivity;
import mail.technopark.bulletinBoard.firebase.Bulletin;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.PhotoSupport;
import mail.technopark.bulletin_board.local_database.view_model.UserViewModel;

public class PersonalBulletinFragment extends Fragment implements RVAdapterClickable.BulListener {
    private FirebaseHelper helper;
    private final ArrayList<Bulletin> bulletins = new ArrayList<>();
    private EditText editText;
    private UserViewModel mUserViewModel;
    //private String userName;

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

//        editText = view.findViewById(R.id.ad_search);
//        editText.setOnKeyListener((v, keyCode, event) -> {
//            // If the event is a key-down event on the "enter" button
//            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                // Perform action on key press
//                setRecyclerView(view, editText.getText().toString());
//                return true;
//            }
//            return false;
//        });
        // Getting ads.
        setRecyclerView(view, "");
        return view;
    }

    private void setRecyclerView(View view, String text) {
        String user = helper.getAuth().getUid();
        if (text.isEmpty()) {
            helper.getFirestore().collection("bulletins")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.w("MYTAG", "Listen failed.", error);
                            return;
                        }
                        bulletins.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Bulletin bulletin = doc.toObject(Bulletin.class);
                            bulletin.setBulletinId(doc.getId());
                            if (bulletin.getUserId().equals(user))
                                bulletins.add(bulletin);
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.rv_personal);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        RVAdapterClickable adapter = new RVAdapterClickable(bulletins, PersonalBulletinFragment.this);
                        recyclerView.setAdapter(adapter);
                    });
        } else {
            helper.getFirestore().collection("bulletins")
                    .whereEqualTo("name", text.trim())
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.w("MYTAG", "Listen failed.", error);
                            return;
                        }
                        bulletins.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Bulletin bulletin = doc.toObject(Bulletin.class);
                            bulletin.setBulletinId(doc.getId());
                            if (bulletin.getUserId().equals(user))
                                bulletins.add(bulletin);
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.rv_personal);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        RVAdapterClickable adapter = new RVAdapterClickable(bulletins, PersonalBulletinFragment.this);
                        recyclerView.setAdapter(adapter);
                    });
        }
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
