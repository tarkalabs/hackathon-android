package in.asid.daybook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.asid.daybook.adapters.TagAdapter;
import in.asid.daybook.models.Tag;
import io.realm.Realm;
import io.realm.RealmObject;

public class TagActivity extends AppCompatActivity {

    @Bind(R.id.coord_layout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.recyclerView)
    RecyclerView tagItemsView;

    private TagAdapter tagAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_layout);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Context context = view.getContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                final EditText editText = new EditText(context);
                alertDialogBuilder.setView(editText);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagAdapter.addTag(editText.getText().toString());

                        Snackbar.make(view, "Added a new tag", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                alertDialogBuilder.create().show();

            }
        });

        tagAdapter = new TagAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        tagItemsView.setHasFixedSize(true);
        tagItemsView.setLayoutManager(layoutManager);
        tagItemsView.setAdapter(tagAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}
