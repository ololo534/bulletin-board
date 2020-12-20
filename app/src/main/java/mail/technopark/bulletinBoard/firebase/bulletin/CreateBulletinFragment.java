package mail.technopark.bulletinBoard.firebase.bulletin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;
import mail.technopark.bulletinBoard.firebase.User;

public class CreateBulletinFragment extends Fragment {
    FirebaseHelper helper;

    public static CreateBulletinFragment newInstance(){
        return new CreateBulletinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(getParentFragmentManager(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_bulletin, container, false);

        if (helper.getAuth().getCurrentUser() != null) {
            String userId = helper.getAuth().getCurrentUser().getUid();
            DocumentReference docRef = helper.getFirestore().collection("users")
                    .document(userId);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                // Get Current user as User class
                User user = documentSnapshot.toObject(User.class);
                // Create Bulletin
                Button saveButton = view.findViewById(R.id.create_bulletin_btn_save);
                saveButton.setOnClickListener(v -> {
                    // Get Bulletin's Data
                    assert user != null;
                    String userName = user.getName();
                    boolean userVisibility = user.getNameVisible();
                    String name = ((EditText) view.findViewById(R.id.bulletin_name))
                            .getText().toString();
                    String description = ((EditText) view.findViewById(R.id.bulletin_description))
                            .getText().toString();
                    String price = ((EditText) view.findViewById(R.id.bulletin_price))
                            .getText().toString();
                    String type = ((EditText) view.findViewById(R.id.bulletin_type))
                            .getText().toString();
                    helper.createBulletin(userName, userVisibility, name, description, price, type);
                });
            });
        }
        return view;
    }
}
