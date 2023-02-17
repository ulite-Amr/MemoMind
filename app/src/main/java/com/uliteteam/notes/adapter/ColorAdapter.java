package com.uliteteam.notes.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.uliteteam.notes.callback.ColersCallBack;
import com.uliteteam.notes.databinding.ColorItemBinding;
import java.util.List;
import com.uliteteam.notes.R;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

  private List<Integer> colors; // List of available colors
  private int position; // Current position
  private Context context; // Context of the activity
  private int selectedColor = -1; // The selected color
  public final ColersCallBack callback; // Callback to be invoked when a color is selected

  // Getter for position
  public int getPosition() {
    return this.position;
  }

  // Setter for position
  public void setPosition(int position) {
    this.position = position;
  }

  // Constructor
  public ColorAdapter(
      Context context, List<Integer> colors, int selectedColor, ColersCallBack callback) {
    this.colors = colors;
    this.context = context;
    this.selectedColor = selectedColor;
    this.callback = callback;
  }

  // Creates a new ViewHolder instance by inflating the layout
  @Override
  public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        ColorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  // Returns the size of the colors list
  @Override
  public int getItemCount() {
    return (colors == null) ? 0 : colors.size();
  }

  // Called by RecyclerView to display the data at a specified position
  @Override
  public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {

    // If the position is not 0, set the background color of the view
    if (position != 0) {
      GradientDrawable gd = new GradientDrawable();
      gd.setColor(context.getColor(colors.get(position)));
      gd.setCornerRadius(100);
      holder.viewColor.setBackground(gd);
    } else { // If the position is 0, set the background color and a stroke
      GradientDrawable gd = new GradientDrawable();
      gd.setColor(colors.get(getPosition()));
      gd.setCornerRadius(100);
      gd.setStroke(4, 4);
      holder.viewColor.setBackground(gd);
    }

    // If the current position is the selected color, set the "done_circle" image as the image
    // resource
    if (position == selectedColor) {
      holder.imageColor.setImageResource(R.drawable.done_circle);
    } else { // If not, set the image resource to 0
      holder.imageColor.setImageResource(0);
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ColorItemBinding binding;
    View viewColor;
    ImageView imageColor;

    public ViewHolder(ColorItemBinding binding) {
      super(binding.getRoot()); // Set the root view of the item as the itemView of the ViewHolder
      this.binding = binding;
      imageColor =
          binding.imageColor1; // Find and assign the imageColor view from the item s layout
      viewColor = binding.color1; // Find and assign the color view from the item s layout

      // Add a click listener to the root view of the item
      binding
          .getRoot()
          .setOnClickListener(
              v -> {
                int previousSelectedColor = selectedColor; // Save the previous selected color index
                selectedColor =
                    getBindingAdapterPosition(); // Set the selected color index to the current item
                // s position
                notifyItemChanged(
                    previousSelectedColor); // Notify the adapter that the previously selected item
                // has changed
                notifyItemChanged(
                    selectedColor); // Notify the adapter that the currently selected item has
                // changed
                if (callback != null)
                  callback.onChanged(
                      String.valueOf(
                          selectedColor)); // Call the onChanged() method of the ColersCallBack
                // interface to pass the selected color index to the
                // parent activity/fragment
              });
    }
  }

  // Getter method for the selected color index
  public int getSelectedColor() {
    return this.selectedColor;
  }

  // Setter method for the selected color index
  public void setSelectedColor(int selectedColor) {
    this.selectedColor = selectedColor;
  }
}
