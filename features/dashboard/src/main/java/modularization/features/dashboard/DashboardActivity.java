package modularization.features.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import modularization.features.dashboard.interfaces.OnNavigate;
import modularization.features.dashboard.join.JoinFrg;
import modularization.features.dashboard.meet.MeetFrg;
import modularization.features.dashboard.settings.SettingsFrg;

public class DashboardActivity extends AppCompatActivity implements OnNavigate {

    private int page = 0;

    @Override
    public void onBackPressed() {
        if (page == 2) {
            onNavigated(1);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        onNavigated(1);
    }

    private void onNavigate(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

    @Override
    public void onNavigated(int page) {
        this.page = page;
        switch (page) {
            case 1:
                onNavigate(Objects.requireNonNull(JoinFrg.Companion.getInstance()));
                break;
            case 2:
                onNavigate(Objects.requireNonNull(SettingsFrg.Companion.getInstance()));
                break;
            case 3:
                onNavigate(Objects.requireNonNull(MeetFrg.Companion.getInstance()));
                break;
            case 4:
                onNavigate(Objects.requireNonNull(JoinFrg.Companion.getInstance()));
                break;
        }
    }
}