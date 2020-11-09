package mail.technopark.bulletinBoard.main_bulletin_board;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mail.technopark.bulletinBoard.R;
import mail.technopark.bulletinBoard.firebase.Bulletin;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.BulletinViewHolder> {

    public static class BulletinViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView bulletinHeader;
        TextView bulletinDescription;
        // Add imageView;

        public BulletinViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            bulletinHeader = (TextView) itemView.findViewById(R.id.ad_header);
            bulletinDescription = (TextView) itemView.findViewById(R.id.ad_description);
            // imageView;
        }
    }

    List<Bulletin> bulletins;
    RVAdapter(List<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }

    @NonNull
    @Override
    public BulletinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genfeeditem, parent, false);
        BulletinViewHolder bulletinViewHolder = new BulletinViewHolder(v);
        return bulletinViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BulletinViewHolder holder, int position) {
        holder.bulletinHeader.setText(bulletins.get(position).getName());
        Log.d("---BIND-V-HOLDER----", (String) holder.bulletinHeader.getText());
        holder.bulletinDescription.setText(bulletins.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return bulletins.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
