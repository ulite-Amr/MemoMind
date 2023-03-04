package com.uliteteam.notes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.view.View;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.navigation.NavigationView;
// import com.itsaky.androidide.logsender.LogSender;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.R;
import com.uliteteam.notes.adapter.NotesAdapter;
import com.uliteteam.notes.databinding.ActivityMainBinding;
import androidx.core.app.ShareCompat;
import com.uliteteam.notes.util.Constants;
import com.uliteteam.notes.util.NoteDataBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {

  private ActivityMainBinding binding;
  private DrawerLayout drawerLayout;
  private NavigationView navigationView;
  private ActionBarDrawerToggle actionBarDrawerToggle;
  NotesAdapter adapter;
  private String user_name;
  private SharedPreferences preferences;

  List<Note> notes = new ArrayList<>();

  protected void onCreate(Bundle savedInstanceState) {
    //    LogSender.startLogging(this);
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    // inflate
    drawerLayout = binding.drower;
    navigationView = binding.navigationView;
    // added Menu In Toolbar

    preferences = PreferenceManager.getDefaultSharedPreferences(this);

    String title = preferences.getString("add_name_home", "MemoMind");

    getSupportActionBar().setTitle(title);
    DrowerHandler();
    droweraction();

    binding.fab.setOnClickListener(n -> FabClick());
    binding.appBar.setLiftOnScrollTargetViewId(androidx.preference.R.id.recycler_view);
    swipRefresh();

    NoteDataBase db = new NoteDataBase(this);
    notes = db.getNote();
    MainActivity mainActivity = this;
    adapter = new NotesAdapter(notes);
    binding.notesRecycler.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    binding.notesRecycler.setAdapter(adapter);

    Collections.reverse(notes);

    notFoundFound();
  }

  // ------>Drower Handler<-------- //

  private void DrowerHandler() {
    navigationView.bringToFront();

    navigationView.setNavigationItemSelectedListener(
        item -> {
          var id = item.getItemId();

          switch (id) {
            case R.id.nav_settings:
              drawerLayout.closeDrawer(GravityCompat.START);
              startActivity(new Intent(this, SettingsActivity.class));
              return true;
            case R.id.nav_archive:
              // Favorite Action Here
              drawerLayout.closeDrawer(GravityCompat.START);
              return true;
            case R.id.nav_trash:
              // Trash Action Here
              drawerLayout.closeDrawer(GravityCompat.START);
              return true;
            case R.id.nav_licence:
              startActivity(new Intent(this, OssLicensesMenuActivity.class));
              drawerLayout.closeDrawer(GravityCompat.START);
              return true;
            case R.id.nav_share:
              var shareIntent = new ShareCompat.IntentBuilder(this);
              shareIntent.setType("text/plain");
              shareIntent.setChooserTitle(getString(R.string.app_name));
              shareIntent.setText(getString(R.string.share_description, Constants.GITHUB_URL));
              shareIntent.startChooser();
              drawerLayout.closeDrawer(GravityCompat.START);
              return true;
            default:
              return false;
          }
        });
  }

  public void droweraction() {

    actionBarDrawerToggle =
        new ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);

    drawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();
  }

  public void swipRefresh() {
    binding.refreshLayout.setOnRefreshListener(
        () -> {
          // refrsh action here
          inflateData();
          loadNotes();

          binding.refreshLayout.setRefreshing(false);
        });
  }

  public void FabClick() {

    startActivity(new Intent(this, NoteActivity.class));
  }

  public void loadNotes() {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onResume() {
    super.onResume();
    inflateData();
    loadNotes();
    notFoundFound();
    user_name = preferences.getString("add_name_home", "MemoMind");
    getSupportActionBar().setTitle(user_name);
  }

  @Override
  public void onStart() {
    super.onStart();
    inflateData();
    loadNotes();
    notFoundFound();
  }

  public void inflateData() {
    NoteDataBase db = new NoteDataBase(this);
    notes = db.getNote();
    adapter = new NotesAdapter(notes);
    binding.notesRecycler.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    binding.notesRecycler.setAdapter(adapter);

    Collections.reverse(notes);
  }

  public void notFoundFound() {
    if (notes.size() > 0) {
      binding.notesRecycler.setVisibility(View.VISIBLE);
      binding.emptyContainer.setVisibility(View.GONE);
    } else {
      binding.notesRecycler.setVisibility(View.GONE);
      binding.emptyContainer.setVisibility(View.VISIBLE);
    }
  }
}
