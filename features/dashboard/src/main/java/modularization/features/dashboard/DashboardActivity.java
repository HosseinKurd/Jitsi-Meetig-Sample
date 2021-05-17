package modularization.features.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;

import java.util.Objects;

import modularization.features.dashboard.preview.JoinFrg;

public class DashboardActivity extends JitsiMeetActivity {// JitsiMeetActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, Objects.requireNonNull(JoinFrg.Companion.getInstance()))
                .commit();
    }
}