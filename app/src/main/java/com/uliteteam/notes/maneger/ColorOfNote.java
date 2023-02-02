package com.uliteteam.notes.maneger;

import android.view.MenuItem;
import com.uliteteam.notes.maneger.ColorOfNote;

public class ColorOfNote {
    
    private MenuItem colorBtn;
    private boolean Editable;
    private String key;

  public ColorOfNote(MenuItem colorBtn, boolean Editable) {
      
        this.colorBtn = colorBtn;
        this.Editable = Editable;
        
  }
    
    public ColorOfNote(String key){
        
        this.key = key;
        
    }

  public void updateStatus(boolean u) {
    if (colorBtn == null) return;

    colorBtn.getIcon().setAlpha(u ? 255 : 130);
    colorBtn.setEnabled(u);
  }

}
