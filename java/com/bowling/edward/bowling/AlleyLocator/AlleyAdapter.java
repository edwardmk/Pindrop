package com.bowling.edward.bowling.AlleyLocator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bowling.edward.bowling.Constructors.Alley;
import com.bowling.edward.bowling.R;

import java.util.List;

public class AlleyAdapter extends RecyclerView.Adapter<AlleyAdapter.MyHolder> {
    List<Alley> alleys;
    Button website, directions;
    private Context context;

    public AlleyAdapter(List<Alley> list, Context context) {
        this.alleys = list;
        this.context = context;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView name,location, rating;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameT);
            location = itemView.findViewById(R.id.locationT);
            rating = itemView.findViewById(R.id.ratingT);
            website = itemView.findViewById(R.id.websiteButton);
            directions = itemView.findViewById(R.id.directionsButton);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alley_item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        Alley dataModel = alleys.get(position);
        holder.name.setText(dataModel.getName());
        holder.location.setText(dataModel.getLocation());
        String ratingString = String.valueOf(dataModel.getRating());
        holder.rating.setText(ratingString + " / 5");
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < alleys.size(); i++)
                if(holder.name.getText().toString().equals(alleys.get(i).getName())){
                    String currLocation = alleys.get(i).getCurrLocation();
                    String url = "https://www.google.com/maps/dir/" + currLocation + "/" + holder.name.getText().toString() + "+" + holder.location.getText().toString();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(url));
                    context.startActivity(intent);
                }
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < alleys.size(); i++)
                    if(holder.name.getText().toString().equals(alleys.get(i).getName())){
                        String websiteName = alleys.get(i).getWebsite();
                        //String url = "https://www.google.com/maps/dir/" + currLocation + "/" + holder.name.getText().toString() + "+" + holder.location.getText().toString();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse(websiteName));
                        context.startActivity(intent);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alleys.size();
    }
}