package mail.technopark.bulletinBoard.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;
import mail.technopark.bulletinBoard.firebase.bulletin.CreateBulletinFragment;
import mail.technopark.bulletinBoard.main_bulletin_board.BulletinFragment;

public class MainActivity extends AppCompatActivity{

    BottomAppBar bottomAppBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);
        setBottomAppBarHide();
        setUpBottomAppBarAndFab();

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, AuthFragment.newInstance(), "AuthFragment")
                    .addToBackStack(AuthFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void setUpBottomAppBarAndFab() {
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.home):
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, BulletinFragment.newInstance())
                            .addToBackStack(BulletinFragment.class.getSimpleName())
                            .commit();
                    setBottomAppBarShow();
                    break;
                case (R.id.liked):
                    setBottomAppBarShow();
                    Toast.makeText(MainActivity.this, "Liked clicked.", Toast.LENGTH_SHORT).show();
                    break;
                case (R.id.profile):
                    setBottomAppBarShow();
                    Toast.makeText(MainActivity.this, "Profile clicked.", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
        fab.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, CreateBulletinFragment.newInstance())
                    .addToBackStack(CreateBulletinFragment.class.getSimpleName())
                    .commit();
            setBottomAppBarHide();
        });
    }

    public void setBottomAppBarShow(){
        bottomAppBar.setVisibility(View.VISIBLE);
        bottomAppBar.performShow();
        fab.show();
    }

    public void setBottomAppBarHide(){
        bottomAppBar.performHide();
        fab.hide();
        bottomAppBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
