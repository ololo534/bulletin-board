package mail.technopark.bulletinBoard.main_bulletin_board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.Bulletin;
import mail.technopark.bulletinBoard.firebase.FirebaseHelper;

public class RVAdapterClickable extends RecyclerView.Adapter<RVAdapterClickable.BulletinViewHolder> {
    final FirebaseHelper firebaseHelper = new FirebaseHelper(null, null);
    private final ArrayList<Bulletin> bulletins;
    private final BulListener mOnBulletinListener;

    public RVAdapterClickable(ArrayList<Bulletin> bulletins, BulListener onBulletinListener) {
        this.bulletins = bulletins;
        this.mOnBulletinListener = onBulletinListener;
    }

    public static class BulletinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //final CardView cardView;
        final TextView bulletinHeader;
        final TextView bulletinDescription;
        final TextView bulletinDate;
        final TextView bulletinUser;
        final ImageView bulletinImageView;
        final BulListener myBulListener;

        public BulletinViewHolder(@NonNull View itemView, BulListener onBulletinListener) {
            super(itemView);
            //cardView = itemView.findViewById(R.id.card_view);
            bulletinHeader = itemView.findViewById(R.id.ad_header);
            bulletinDescription = itemView.findViewById(R.id.ad_description);
            bulletinDate = itemView.findViewById(R.id.ad_date);
            bulletinUser = itemView.findViewById(R.id.ad_user);
            bulletinImageView = itemView.findViewById(R.id.ad_image);
            itemView.setOnClickListener(this);
            this.myBulListener = onBulletinListener;
        }

        @Override
        public void onClick(View v) {
            myBulListener.onBulletinClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public BulletinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genfeeditem, parent, false);
        return new BulletinViewHolder(v, mOnBulletinListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BulletinViewHolder holder, int position) {
        Bulletin bulletin = bulletins.get(position);
        holder.bulletinHeader.setText(bulletin.getName());
        holder.bulletinDescription.setText(bulletin.getDescription());

        // Format date DAY/MONTH/YEAR. Example: 01/01/2000.
        String date = bulletin.getDate();
        String month = date.substring(4, 7);
        String day = date.substring(8, 10);
        String year = date.substring(30, 34);
        String newDate = day + "/" + month + "/" + year;
        holder.bulletinDate.setText(newDate);
        // Setting name
        //holder.bulletinUser.setText(bulletin.getUserName());
        if (bulletin.getUserVisibility()) {
            holder.bulletinUser.setText(bulletin.getUserName());
        }
        firebaseHelper.getBulletinPhoto(bulletin.getBulletinId(), holder.bulletinImageView);
    }

    @Override
    public int getItemCount() {
        return bulletins.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface BulListener {
        void onBulletinClick(int position);
    }
}
