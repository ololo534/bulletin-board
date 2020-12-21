package mail.technopark.bulletinBoard.main_bulletin_board;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class BulletinFragment extends Fragment {
    private FirebaseHelper helper;
    private final ArrayList<Bulletin> bulletins = new ArrayList<>();
//    private String searchText = "";

    public static BulletinFragment newInstance(){
        return new BulletinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
        PhotoSupport photoSupport = new PhotoSupport();
        UserViewModel mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);
        EditText editText = view.findViewById(R.id.ad_search);
        editText.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                setRecyclerView(view, editText.getText().toString());
                return true;
            }
            return false;
        });
        // Getting ads.
        setRecyclerView(view, editText.getText().toString());
        return view;
    }

    private void setRecyclerView(View view, String text) {
        if (text.isEmpty()) {
            helper.getFirestore().collection("bulletins")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            bulletins.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Bulletin bulletin = document.toObject(Bulletin.class);
                                bulletin.setBulletinId(document.getId());
                                bulletins.add(bulletin);
                            }
                            RecyclerView recyclerView = view.findViewById(R.id.rv);
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            RVAdapter adapter = new RVAdapter(bulletins);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.d("TAG", "Error on reading docs");
                        }
                    });
        } else {
            helper.getFirestore().collection("bulletins")
                    .whereEqualTo("name", text.trim())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            bulletins.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Bulletin bulletin = document.toObject(Bulletin.class);
                                bulletin.setBulletinId(document.getId());
                                bulletins.add(bulletin);
                            }
                            RecyclerView recyclerView = view.findViewById(R.id.rv);
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            RVAdapter adapter = new RVAdapter(bulletins);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.d("TAG", "Error on reading docs");
                        }
                    });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setBottomAppBarShow();
    }
}
