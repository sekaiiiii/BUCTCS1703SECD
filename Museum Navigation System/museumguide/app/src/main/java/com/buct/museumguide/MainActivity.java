package com.buct.museumguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.buct.museumguide.ui.FragmentForUsers.Upload.UploadAudio;
import com.buct.museumguide.ui.map.MapGuide;
import com.buct.museumguide.util.WebHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
/*主界面，貌似不需要动它，基本所有页面都在ui里，如果有看不懂的再问
*  除了能直接上模板的，剩下的全使用fragment了
* 记得在nav里注册
* */
public class MainActivity extends AppCompatActivity {
    //public static String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Intent intent=getIntent();
        String info=intent.getStringExtra("info");
        SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
        Infos.edit().putString("cookie","").apply();
        Infos.edit().putString("info",info).apply();
        AppBarConfiguration appBarConfiguration;
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }
}
