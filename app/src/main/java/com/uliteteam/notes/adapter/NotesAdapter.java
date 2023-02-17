package com.uliteteam.notes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.activity.Details;
import com.uliteteam.notes.activity.MainActivity;
import com.uliteteam.notes.util.NoteDataBase;
import java.util.List;
import java.util.Locale;
import com.uliteteam.notes.R;

/** Adapter for displaying notes in a RecyclerView. */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

  private LayoutInflater inflater;
  private List<Note> notes;
  private NoteDataBase db;

  private SharedPreferences preferences;

  /**
   * Constructor for the NotesAdapter.
   *
   * @param notes the list of notes to display
   */
  public NotesAdapter(List<Note> notes) {
    this.notes = notes;
  }

  /**
   * Returns the number of notes in the list.
   *
   * @return the number of notes in the list
   */
  @Override
  public int getItemCount() {
    return notes.size();
  }

  //  This Is Used like "Sketchware Application"
  @Override
  public void onBindViewHolder(NotesAdapter.ViewHolder holder, int i) {

    // Get the position of the current note
    int position = i;

    // Get the context of the current view holder
    Context context = holder.itemView.getContext();

    // Set animation for the current item view
    holder.itemView.setAnimation(
        AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.list_animation));

    // Get the title, content, date and color of the current note
    String title = notes.get(i).getTitle();
    String content = notes.get(i).getContent();
    String date = notes.get(i).getDate();
    String color = notes.get(i).getColor();

    // Set the title and date of the current item view
    holder.title.setText(title);
    holder.noteDate.setText(date);

    // Set the first letter of the title as the icon of the current item view
    holder.icon.setText(title.substring(0, 1).toUpperCase(Locale.US));

    // Define arrays of colors and second colors for the item views
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

    // If the note has a specific color, set the background color and second color of the current
    // item view
    if (!"0".equals(color) && Integer.parseInt(color) < colors.length) {
      int colorCode = Integer.parseInt(color);
      holder.card.setCardBackgroundColor(context.getColor(colors[colorCode]));
      holder.icon.setBackground(context.getResources().getDrawable(secendColor[colorCode]));
    }
  }

  //  OnCreate Holder
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    // Initialize the layoutId variable
    int layoutId;

    // Get the shared preferences of the parent context
    preferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());

    // Get the key value from the shared preferences
    String key = preferences.getString("rows_style", "default");

    // Set the layoutId based on the key value
    if (key.equals("style1")) {
      layoutId = R.layout.row_list_1;
    } else if (key.equals("style2")) {
      layoutId = R.layout.row_list_2;
    } else if (key.equals("style3")) {
      layoutId = R.layout.row_list_3;

    } else {
      layoutId = R.layout.row_list_item;
    }

    // Inflate the view using the layoutId
    View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

    // Return a new ViewHolder object using the inflated view
    return new ViewHolder(view);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    // The UI elements of the RecyclerView item
    TextView title; // Display the title of the note
    TextView noteDate; // Display the date of the note
    TextView icon; // Display the icon of the note
    MaterialCardView card; // The MaterialCardView that wraps the item s UI elements

    // The constructor of the ViewHolder, which receives the view item as a parameter
    public ViewHolder(View itemView) {
      super(itemView);

      // Initialize the UI elements of the view item
      title = itemView.findViewById(R.id.title);
      noteDate = itemView.findViewById(R.id.NoteDate);
      icon = itemView.findViewById(R.id.icon);
      card = itemView.findViewById(R.id.card_continar);

      // Initialize the database instance
      db = new NoteDataBase(itemView.getContext());

      // Set a click listener for the view item
      itemView.setOnClickListener(
          v -> {
            // Create an intent to open the details activity
            Intent in = new Intent(v.getContext(), Details.class);

            // Pass the ID of the selected note to the details activity
            in.putExtra("ID", notes.get(getBindingAdapterPosition()).getID());

            // Add a transition animation to the activity
            ((Activity) v.getContext())
                .overridePendingTransition(
                    android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            // Use ActivityOptionsCompat to create a shared element transition
            ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) v.getContext(), (View) icon, "appcard");

            // Start the details activity with the shared element transition
            v.getContext().startActivity(in, options.toBundle());
          });

      // Get a reference to the MainActivity instance
      final MainActivity mainActivity = (MainActivity) itemView.getContext();

      // Set a long click listener for the view item
      itemView.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              // Create a MaterialAlertDialogBuilder for the delete dialog
              MaterialAlertDialogBuilder delete = new MaterialAlertDialogBuilder(mainActivity);

              // Set the title and message of the dialog
              delete.setTitle("Delete Note");
              delete.setMessage("Do you want to delete this note?");

              // Set the negative button of the dialog
              delete.setNegativeButton("No", (d, w) -> d.dismiss());

              // Set the positive button of the dialog
              delete.setPositiveButton(
                  "Yes",
                  (d, w) -> {
                    // Delete the selected note from the database
                    db.deleteNote(notes.get(getBindingAdapterPosition()).getID());

                    // Notify the adapter that the item is removed
                    notifyItemRemoved(getBindingAdapterPosition());

                    // Reload the data in the MainActivity
                    mainActivity.inflateData();

                    // If there are no more notes, show the "not found" message
                    if (notes.size() > 0) {
                      mainActivity.notFoundFound();
                    }
                  });

              // Show the delete dialog
              delete.create().show();

              // Indicate that the long click event has been handled
              return true;
            }
          });
    }
  }
}
