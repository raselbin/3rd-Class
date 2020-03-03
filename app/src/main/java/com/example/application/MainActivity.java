package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private Toolbar toolbar;
    private NavigationView nav;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private InboxFragment inboxFragment;
    private LocationFragment locationFragment;
    
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    List<MenuModel> menuModellist=new ArrayList<>();
    HashMap<MenuModel,List<MenuModel>> childList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment=new HomeFragment();          //fragment object
        inboxFragment=new InboxFragment();
        locationFragment=new LocationFragment();

        drawerlayout = findViewById(R.id.deawerlayout);  //this is findview section

        toolbar=findViewById(R.id.toolbaar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);

        nav=findViewById(R.id.navid);

        frameLayout=findViewById(R.id.fram_id);

        bottomNavigationView=findViewById(R.id.bottom_id);

        expandableListView=findViewById(R.id.expendedmenu_id);
        prePareMenu();
        populateExpandableList();
        setFrahment(homeFragment);                                   //set default home fragment
        bottomNavigationView.setSelectedItemId(R.id.home_id);        //select home fragment





        ActionBarDrawerToggle actiondrawabletoggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.navigationopen,R.string.navigationclose);
        drawerlayout.addDrawerListener(actiondrawabletoggle);
        actiondrawabletoggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        actiondrawabletoggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {         //navigation menu bar onclick listener method
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.settingid) {
                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.shareid) {
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                    Snackbar  snakbar = Snackbar.make(drawerlayout,"Share",Snackbar.LENGTH_SHORT);
                    snakbar.show();
                    snakbar.setAction("Okay", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                } else if (id == R.id.logoutid) {
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {     //bottom navigation  view onclick listener method
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox_id:
                        setFrahment(inboxFragment);
                        return true;
                    case R.id.home_id:
                        setFrahment(homeFragment);
                        return true;
                    case R.id.location_id:
                        setFrahment(locationFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void populateExpandableList() {
        expandableListAdapter = new CustomExpandableListAdapter(this,menuModellist,childList) ;
        expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//
//                if(menuModellist.get(groupPosition).isGroup){
//                    if(!menuModellist.get(groupPosition).hasChild){
//
//                    }
//                }
//                return false;
//            }
//        });

    }

    private void prePareMenu() {
        MenuModel menuModel = new MenuModel("test",true,true);
        menuModellist.add(menuModel);
        List<MenuModel> chiModelList = new ArrayList<>();
        MenuModel childitem =new MenuModel("item1",false,false);
        chiModelList.add(childitem);

        childitem=new MenuModel("item2",false,false);
        chiModelList.add(childitem);
        if(menuModel.hasChild){
            childList.put(menuModel,chiModelList);
        }
    }

    private void setFrahment(Fragment frahment){
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fram_id,frahment);
        fragmentTransaction.commit();
    }

}
