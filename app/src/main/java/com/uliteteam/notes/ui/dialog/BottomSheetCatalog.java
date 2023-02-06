package com.uliteteam.notes.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import com.uliteteam.notes.R;
import android.content.Context;
import android.app.Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.uliteteam.notes.callback.BottomSheetColorsCallBack;
import com.uliteteam.notes.databinding.ColorPickNoteBinding;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;

public class BottomSheetCatalog {

  private ColorPickNoteBinding binding;
  private BottomSheetDialog bottomSheet;
  private BottomSheetColorsCallBack callBack;

  public BottomSheetCatalog(Context context, String selectedNoteColor ) {

    BottomSheetDialog bottomSheetColors =
        new BottomSheetDialog(context, R.style.ModalBottomSheetDialog);
    // Creating a new instance of the BottomSheetDialog with the context of the Details activity
    // and the specified style
    binding = ColorPickNoteBinding.inflate(((Activity) context).getLayoutInflater());
    // Inflating the color_pick_note layout file and assigning it to the bottomSheetView variable

    bottomSheetColors.setContentView(binding.getRoot());
    /* Inflate View from (R.layout.color_pick_note) to the bottom sheet */

    binding.color1.setOnClickListener(
        v -> {
          
        });

    bottomSheetColors.show();
  }
}
