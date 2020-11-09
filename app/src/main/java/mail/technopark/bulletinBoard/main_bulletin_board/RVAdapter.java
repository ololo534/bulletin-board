package mail.technopark.bulletinBoard.main_bulletin_board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.Bulletin;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.BulletinViewHolder> {

    public static class BulletinViewHolder extends RecyclerView.ViewHolder {
        //final CardView cardView;
        final TextView bulletinHeader;
        final TextView bulletinDescription;
        final TextView bulletinDate;
        final TextView bulletinUser;
        // Add imageView;

        public BulletinViewHolder(@NonNull View itemView) {
            super(itemView);
            //cardView = itemView.findViewById(R.id.card_view);
            bulletinHeader = itemView.findViewById(R.id.ad_header);
            bulletinDescription = itemView.findViewById(R.id.ad_description);
            bulletinDate = itemView.findViewById(R.id.ad_date);
            bulletinUser = itemView.findViewById(R.id.ad_user);
            // imageView;
        }
    }

    final List<Bulletin> bulletins;
    RVAdapter(List<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }

    @NonNull
    @Override
    public BulletinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genfeeditem, parent, false);
        return new BulletinViewHolder(v);
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
    }

    @Override
    public int getItemCount() {
        return bulletins.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
