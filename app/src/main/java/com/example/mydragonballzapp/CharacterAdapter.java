package com.example.mydragonballzapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

    private List<Character> characters;
    Context context;

    public CharacterAdapter(List<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return characters.size();
    }
    @NonNull
    @Override
    public CharacterAdapter.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_character, null);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterViewHolder holder, int position) {
        holder.bind(characters.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Character character = characters.get(clickedPosition);
                    Gson gson = new Gson();
                    String characterJson = gson.toJson(character);

                    Intent intent = new Intent(context, CharacterDetailsActivity.class);
                    intent.putExtra("characterJson", characterJson);
                    context.startActivity(intent);
                }
            }
        });

    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCharacterName;
        private TextView textViewCahracterDescription;
        private ImageView imageViewCharacter;
        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCharacterName = itemView.findViewById(R.id.text_view_character_name);
            textViewCahracterDescription = itemView.findViewById(R.id.text_view_character_description);
            imageViewCharacter = itemView.findViewById(R.id.image_view_character_image);
        }

        public void bind(Character character) {
            textViewCharacterName.setText("Name: " + character.getName());
            textViewCahracterDescription.setText("Description: " + character.getDescription());
            Picasso.get().load(character.getImage()).into(imageViewCharacter);
        }
    }


}
