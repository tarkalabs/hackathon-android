package in.asid.daybook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudhakar on 28/12/15.
 */
public class TagDetailsActivity extends AppCompatActivity {
    @Bind(R.id.heading)
    TextView heading;

    @Bind(R.id.details)
    TextView details;

    @Bind(R.id.calendarView)
    android.widget.DatePicker calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_details);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String tagName = getIntent().getStringExtra("tagName");
        this.heading.setText(tagName);
    }
}