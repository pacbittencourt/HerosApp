package ufjf.heros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pedro Antonio on 26/11/2016.
 */

public class ItemHeroAdapter extends RecyclerView.Adapter<ItemHeroAdapter.ItensViewHolder> {

    private final Context context;
    private final List<Hero> heros;
    private static MyClickListener myClickListener;

    public ItemHeroAdapter(Context context, List<Hero> heros) {
        this.context = context;
        this.heros = heros;
    }


    @Override
    public ItensViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_heroi, parent, false);
        return new ItensViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItensViewHolder holder, int position) {
        Hero hero = heros.get(position);

        holder.txtName.setText(hero.getName());
        holder.txtSecretName.setText(hero.getSecret_id());
        holder.txtAge.setText(hero.getAge());


    }

    @Override
    public int getItemCount() {
        return this.heros != null ? this.heros.size() : 0;
    }

    public static class ItensViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtName;
        public TextView txtSecretName;
        public TextView txtAge;

        public ItensViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtSecretName = (TextView) itemView.findViewById(R.id.txtSecretName);
            txtAge = (TextView) itemView.findViewById(R.id.txtAge);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
