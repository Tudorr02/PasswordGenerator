package com.example.passowrdgenerator;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Passwords_Fragment extends Fragment  {

    FloatingActionButton floating_button;
    AlertDialog dialog;
    LinearLayout layout;


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_passwords_, container, false);


        floating_button=view.findViewById(R.id.floatingActionButton);
        layout=view.findViewById(R.id.container);

        buildDialog();

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        return view;
    }

    private void buildDialog(){


        View view = getLayoutInflater().inflate(R.layout.dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        EditText name_of_platform=view.findViewById(R.id.platform_name);
        EditText name_of_password=view.findViewById(R.id.password_name);

        builder.setView(view)
                .setTitle("Save a new password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name_of_platform.setText("");
                        name_of_password.setText("");
                    }
                })

                .setPositiveButton("Create", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(!name_of_password.getText().toString().isEmpty() && !name_of_password.getText().toString().isEmpty())
                        addItem(name_of_platform.getText().toString(),name_of_password.getText().toString());
                        else
                        if(name_of_password.getText().toString().isEmpty() && name_of_password.getText().toString().isEmpty())
                            Toast.makeText(getContext(), " Platform name and password required", Toast.LENGTH_SHORT).show();


                        name_of_platform.setText("");
                        name_of_password.setText("");


                    }
                });

         dialog=builder.create();

    }

    private void addItem(String name,String pass){

        View view = getLayoutInflater().inflate(R.layout.card,null);
        TextView nameView = view.findViewById(R.id.name);
        ImageButton delete=view.findViewById(R.id.delete);
        Button copy=view.findViewById(R.id.copy);

        nameView.setText(name);

        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("edit",pass);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(),"Password copied to Clipboard",Toast.LENGTH_LONG).show();
            }
        });


        layout.addView(view);

    }



}