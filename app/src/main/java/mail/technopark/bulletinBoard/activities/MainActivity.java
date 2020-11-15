package mail.technopark.bulletinBoard.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;
import mail.technopark.bulletinBoard.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, AuthFragment.newInstance(), "AuthFragment")
                    .addToBackStack(AuthFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
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
