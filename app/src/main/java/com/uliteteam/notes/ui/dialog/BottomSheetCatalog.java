package com.uliteteam.notes.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.elevation.SurfaceColors;
import com.uliteteam.notes.R;
import com.uliteteam.notes.adapter.ColorAdapter;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;
import com.uliteteam.notes.callback.ColersCallBack;
import java.util.ArrayList;
import java.util.List;

/*
 * This class represents a BottomSheetDialog that contains:
 * - A color picker with a RecyclerView
 * - An "Add Image" option (not yet implemented)
 */
public class BottomSheetCatalog {

    // Fields
    private final Context context; // Context to use in Activity
    private final BottomSheetDialog bottomSheetDialog; // The BottomSheetDialog from the Material Library
    public String selectedNoteColor; // The selected note color, to be used with ids
    public AppBarLayout appbar; // The appbar to use in setMethod to statusColor()
    public View background; // The background view to use in setMethod to statusColor()
    private final LinearLayout menuImage; // The linear layout that contains the "Add image" option
    public BottomSheetCatalogCallBack callBack; // Callback to be used for changes in other classes
    public RecyclerView colorRecycler; // The RecyclerView for the color picker
    private final ColorAdapter adapter; // The adapter for the color RecyclerView

    /*
     * Constructor for BottomSheetCatalog
     * @param context The context to use in Activity
     * @param style The BottomSheet style
     * @param selectedNoteColor The selected note color
     * @param callBack The BottomSheet callback to be used for changes in other classes
     */
  public BottomSheetCatalog(
      Context context, int style, String selectedNoteColor, BottomSheetCatalogCallBack callBack) {
    this.context = context;
    this.selectedNoteColor = selectedNoteColor;
    this.bottomSheetDialog = new BottomSheetDialog(context, style);
        
        
    // Inflating the layout for the bottom sheet view
    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.color_pick_note, null);

    // Setting the content view of the bottom sheet dialog
    bottomSheetDialog.setContentView(bottomSheetView);

    // Setting the callback for the color selection
    this.callBack = callBack;

    // Finding the menu image view in the bottom sheet view
    this.menuImage = bottomSheetView.findViewById(R.id.menuImage);

    // Finding the color recycler view in the bottom sheet view
    this.colorRecycler = bottomSheetView.findViewById(R.id.ColorsList);

    // Creating a horizontal layout manager for the color recycler view
    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

    // Creating a list of colors to display in the color recycler view
    List<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(R.color.color1);
        colors.add(R.color.color2);
        colors.add(R.color.color3);
        colors.add(R.color.color4);
        colors.add(R.color.color5);
        colors.add(R.color.color6);
        colors.add(R.color.color7);
        colors.add(R.color.color8);
        colors.add(R.color.color9);
        colors.add(R.color.color10);

     // Setting the layout manager for the color recycler view
    colorRecycler.setLayoutManager(horizontalLayoutManager);

    // Creating an adapter for the color recycler view
        adapter = new ColorAdapter(
        context,
        colors,
        Integer.parseInt(selectedNoteColor),
        new ColersCallBack() {
        @Override
        public void onChanged(String color) {
        setSelectedNoteColor(color);
        statusColors();
   }
    });

    // Setting the adapter for the color recycler view
        colorRecycler.setAdapter(adapter);

       // Making the menu image clickable and setting a click listener to launch the photo picker and cancel the bottom sheet dialog
        this.menuImage.setClickable(true);
        this.menuImage.setOnClickListener(
        v -> {
//        launchPhotoPicker();
				Toast.makeText(context,"Not available yet",Toast.LENGTH_SHORT).show();
        bottomSheetDialog.cancel();
      });

   }

       /* Method to update the status bar and navigation bar colors based on the selected note color */
    public void statusColors() {
        if (callBack != null) callBack.onChanged(selectedNoteColor);

        Window w = ((Activity) context).getWindow();

        int[] colorIds = {
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
        
   if (Integer.parseInt(selectedNoteColor) > 0) {
      int selectedColorId = colorIds[Integer.parseInt(selectedNoteColor)];
      background.setBackgroundColor(context.getColor(selectedColorId));
      w.setStatusBarColor(context.getColor(selectedColorId));
      w.setNavigationBarColor(context.getColor(selectedColorId));
      appbar.setBackgroundColor(context.getColor(selectedColorId));
   } else {
      int colorBase = SurfaceColors.SURFACE_2.getColor(context);
      background.setBackgroundColor(colorBase);
      w.setStatusBarColor(colorBase);
      w.setNavigationBarColor(colorBase);
      appbar.setBackgroundColor(colorBase);
   }
  }

        // Method to show the bottom sheet dialog
    
    public void show(){
        this.bottomSheetDialog.show();
    }

  public String getSelectedNoteColor() {
    return String.valueOf(adapter.getSelectedColor());
  }
        /* This method to set setSelectedNoteColor in ather classes */
  public void setSelectedNoteColor(String selectedNoteColor) {
    this.selectedNoteColor = selectedNoteColor;
  }

  public AppBarLayout getAppbar() {
    return this.appbar;
  }
    /* This method to set Appbar color in ather classes */
  public void setAppbar(AppBarLayout appbar) {
    this.appbar = appbar;
  }

  public View getBackground() {
    return this.background;
  }
    
    
    /* This method to set Background color in ather classes */
  public void setBackground(View background) {
    this.background = background;
  }
    
       // Method to launch the photo picker
    private void launchPhotoPicker() {
        // Add your implementation here
    }
 }