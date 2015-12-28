package in.asid.daybook;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;

public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_layout, null);
        this.setContentView(contentView);
    }
}
