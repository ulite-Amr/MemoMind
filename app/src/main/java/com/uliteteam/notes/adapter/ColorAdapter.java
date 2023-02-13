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

  private List<Integer> colors;
  private int position;
  private Context context;
  private int selectedColor = -1;
  public final ColersCallBack callback;  

  public int getPosition() {
    return this.position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public ColorAdapter(Context context, List<Integer> colors, int selectedColor , ColersCallBack callback) {
    this.colors = colors;
    this.context = context;
    this.selectedColor = selectedColor;
    this.callback = callback;    
  }

  @Override
  public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        ColorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public int getItemCount() {
    return (colors == null) ? 0 : colors.size();
  }

  @Override
  public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {

    if (position != 0) {
      GradientDrawable gd = new GradientDrawable();
      gd.setColor(context.getColor(colors.get(position)));
      gd.setCornerRadius(100);
      holder.viewColor.setBackground(gd);
    } else {
      GradientDrawable gd = new GradientDrawable();
      gd.setColor(colors.get(getPosition()));
      gd.setCornerRadius(100);
      gd.setStroke(4, 4);
      holder.viewColor.setBackground(gd);
    }

    if (position == selectedColor) {
      holder.imageColor.setImageResource(R.drawable.done_circle);
    } else {
      holder.imageColor.setImageResource(0);
    }
        
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ColorItemBinding binding;
    View viewColor;
    ImageView imageColor;

    public ViewHolder(ColorItemBinding binding) {

      super(binding.getRoot());
      this.binding = binding;
      imageColor = binding.imageColor1;
      viewColor = binding.color1;
            
       binding.getRoot().setOnClickListener( v ->{
                int previousSelectedColor = selectedColor;
                selectedColor = (getBindingAdapterPosition());
                notifyItemChanged(previousSelectedColor);
                notifyItemChanged(selectedColor);
                if (callback != null) callback.onChanged(String.valueOf(selectedColor));    
       });   
            

    }
  }

  public int getSelectedColor() {
    return this.selectedColor;
  }

  public void setSelectedColor(int selectedColor) {
    this.selectedColor = selectedColor;
  }
}
