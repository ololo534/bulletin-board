package mail.technopark.bulletinBoard.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.util.Objects;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.authentication.AuthFragment;
import mail.technopark.bulletinBoard.firebase.bulletin.CreateBulletinFragment;

public class MainActivity extends AppCompatActivity {
    public static Bitmap bulletin_bitmap = null;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            if (requestCode == 65537) {
                Uri selectedImage = data.getData();
                try {
                    bulletin_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    ((CreateBulletinFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("CreateBulletinFragment"))).SetSelectedPhoto(bulletin_bitmap);
                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            }
    }
}
