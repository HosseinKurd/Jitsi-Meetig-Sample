package modularization.features.dashboard;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeetActivityInterface;

import java.util.Objects;

import modularization.features.dashboard.join.JoinFrg;

public class DashboardActivity extends FragmentActivity implements JitsiMeetActivityInterface {// JitsiMeetActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, Objects.requireNonNull(JoinFrg.Companion.getInstance()))
                .commit();
    }

    @Override
    public void requestPermissions(String[] strings, int i, PermissionListener permissionListener) {

    }
}