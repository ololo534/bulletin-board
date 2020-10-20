package mail.technopark.bulletin_board.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import mail.technopark.bulletin_board.firebase.authentication.AuthFragment;
import mail.technopark.bulletin_board.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new AuthFragment(), "AuthFragment")
                    .addToBackStack(AuthFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        AuthFragment fragment = (AuthFragment) fragmentManager.findFragmentByTag("AuthFragment");
        if (fragment != null && fragment.isVisible()) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
                fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
