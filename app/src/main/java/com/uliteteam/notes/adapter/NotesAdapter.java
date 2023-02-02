package com.uliteteam.notes.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.MaterialContainerTransform;
import com.uliteteam.notes.Note;
import com.uliteteam.notes.activity.Details;
import com.uliteteam.notes.activity.MainActivity;
import com.uliteteam.notes.adapter.NotesAdapter;
import com.uliteteam.notes.util.NoteDataBase;
import java.util.List;
import java.util.Locale;
import com.uliteteam.notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

  LayoutInflater inflater;
  List<Note> notes;
  NoteDataBase db;

  private SharedPreferences preferences;

  public NotesAdapter(List<Note> notes) {
    this.notes = notes;
  }


  @Override
  public int getItemCount() {
    return notes.size();
  }

  //  This Is Used like "Sketchware Application"
  @Override
  public void onBindViewHolder(NotesAdapter.ViewHolder holder, int i) {

    int position = i;

    Context context = holder.itemView.getContext();

    holder.itemView.setAnimation(
        AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.list_animation));
    String title = notes.get(i).getTitle();
    String content = notes.get(i).getContent();
    String date = notes.get(i).getDate();
    String color = notes.get(i).getColor();

    holder.title.setText(title);
    holder.noteDate.setText(date);
    holder.icon.setText(title.substring(0, 1).toUpperCase(Locale.US));

    int[] colors = {
      0,
      R.color.color1,
      R.color.color2,
      R.color.color3,
      R.color.color4,
      R.color.color5,
      R.color.color6,
      R.color.color7,
      R.color.color8,
      R.color.color9,
      R.color.color10
    };

    int[] secendColor = {
      0,
      R.drawable.rounded_rect1,
      R.drawable.rounded_rect2,
      R.drawable.rounded_rect3,
      R.drawable.rounded_rect4,
      R.drawable.rounded_rect5,
      R.drawable.rounded_rect6,
      R.drawable.rounded_rect7,
      R.drawable.rounded_rect8,
      R.drawable.rounded_rect9,
      R.drawable.rounded_rect10
    };

    if (!"0".equals(color) && Integer.parseInt(color) < colors.length) {
      int colorCode = Integer.parseInt(color);
      holder.card.setCardBackgroundColor(context.getColor(colors[colorCode]));
      holder.icon.setBackground(context.getResources().getDrawable(secendColor[colorCode]));
    }
  }
  //  OnCreate Holder
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    int layoutId;
    preferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
    String key = preferences.getString("rows_style", "default");
    if (key.equals("style1")) {
      layoutId = R.layout.row_list_1;
    } else if (key.equals("style2")) {
      layoutId = R.layout.row_list_2;
    } else if (key.equals("style3")) {
      layoutId = R.layout.row_list_3;

    } else {
      layoutId = R.layout.row_list_item;
    }
    View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    return new ViewHolder(view);
  }

  // Inner Class For Views

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView noteDate;
    TextView icon;
    MaterialCardView card;

    public ViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.title);
      noteDate = itemView.findViewById(R.id.NoteDate);
      icon = itemView.findViewById(R.id.icon);
      card = itemView.findViewById(R.id.card_continar);

      db = new NoteDataBase(itemView.getContext());

      itemView.setOnClickListener(
          v -> {
            Intent in = new Intent(v.getContext(), Details.class);
            in.putExtra("ID", notes.get(getBindingAdapterPosition()).getID());
            v.getContext().startActivity(in);
          });

      final MainActivity mainActivity = (MainActivity) itemView.getContext();

      itemView.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              MaterialAlertDialogBuilder delete = new MaterialAlertDialogBuilder(mainActivity);

              delete.setTitle("Delete Note");
              delete.setMessage("Do you want delete this note");
              delete.setNegativeButton("No", (d, w) -> d.dismiss());
              delete.setPositiveButton(
                  "Yes",
                  (d, w) -> {
                       db.deleteNote(notes.get(getBindingAdapterPosition()).getID());
                      notifyItemRemoved(getBindingAdapterPosition());
                      mainActivity.inflateData();         
                      if (notes.size() > 0) {
                       mainActivity.notFoundFound();
                                    
                      }              
                  });
              delete.create().show();
              return true;
            }
          });
    }
  }
}
