package com.example.passowrdgenerator;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation_bar=findViewById(R.id.bottomNavigationView);

        Fragment home_fragment=new Home_fragment();
        Fragment passwords_fragment=new Passwords_Fragment();


        add_fragment(passwords_fragment);
        add_fragment(home_fragment);

        replace_fragment(home_fragment);

        navigation_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()== R.id.home_fragment) {

                    replace_fragment(home_fragment);
                }

                if(item.getItemId()==R.id.passwords_fragment){

                    replace_fragment(passwords_fragment);

                }

                //fragmentTransaction.commit();
                return true;
            }
        });


    }

    void replace_fragment(Fragment fragment){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);

        fragmentTransaction.commit();

    }


    public void add_fragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView,fragment,"fragment");
        fragmentTransaction.commit();

    }


}