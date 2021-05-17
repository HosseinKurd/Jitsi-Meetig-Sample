package modularization.features.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import modularization.features.dashboard.preview.CameraFrg;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, CameraFrg.Companion.getInstance())
                .commit();
    }
}