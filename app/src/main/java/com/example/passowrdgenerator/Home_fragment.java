package com.example.passowrdgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Random;

public class Home_fragment  extends Fragment {

    int nr_letters;

    Button generate_button;
    ImageButton copy_button;

    EditText password_result;
    EditText number_of_letters;

    CheckBox upper_letters;
    CheckBox lower_letters;
    CheckBox numbers_included;
    CheckBox symbols_included;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_fragment, container, false);

        generate_button=view.findViewById(R.id.button_generate);
        copy_button=view.findViewById(R.id.copy_button);///Image Button

        password_result=view.findViewById(R.id.Password_result);
        number_of_letters=view.findViewById(R.id.input_number_letters);

        upper_letters=view.findViewById(R.id.checkBox_upper_letters);
        lower_letters=view.findViewById(R.id.checkBox_lower_letters);

        numbers_included=view.findViewById(R.id.checkBox_number);
        symbols_included=view.findViewById(R.id.checkBox_symbols);

        generate();

        copy_button.setOnClickListener(new View.OnClickListener() {

                public void onClick (View view){
                    if(!password_result.getText().toString().isEmpty()){
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence label;
                ClipData clip = ClipData.newPlainText("edit", password_result.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Password copied to Clipboard", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getContext(),"Empty Password",Toast.LENGTH_LONG).show();

            }

        });

        return view;

    }

    boolean check_for_requirements_checkboxes(){

        int checked_boxes=0;

        if(upper_letters.isChecked())
            checked_boxes++;

        if(lower_letters.isChecked())
            checked_boxes++;

        if(numbers_included.isChecked())
            checked_boxes++;

        if(symbols_included.isChecked())
            checked_boxes++;

        return !(checked_boxes==0);

    }

    boolean check_for_requirements() ///input number of characters
    {

        ///first case--- number of letters is not filled
        if(number_of_letters.getText().toString().isEmpty() )
        {
            Toast.makeText(getContext(), "Put a number between 1-16", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            nr_letters = Integer.parseInt(number_of_letters.getText().toString());
            if(nr_letters>16){
                Toast.makeText(getContext(), "Put a number between 1-16", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

                if(!check_for_requirements_checkboxes())
                {
                    Toast.makeText(getContext(), "Minimum one option is required", Toast.LENGTH_SHORT).show();
                    return false;
                }



        return true;


    }

    public void generate(){


        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status= check_for_requirements();

                if(status)
                    onButtonClick();
            }
        });



    }

    public void onButtonClick()

    {
        String uppercase_letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; /// case 1
        String lowercase_letters = "abcdefghijklmnopqrstuvwxyz"; /// case 2
        String numbers = "0123456789"; /// case 3
        String symbols = "!#$%&*/:;?@^_"; /// case 4

        int number_of_characters =Integer.parseInt(number_of_letters.getText().toString());
        boolean status = true;
        Random random = new Random();

        int[] checked_cases=new int[0];

        boolean block_indexing=false;

        checked_cases=create_cases_array(checked_cases);

        int[] helper=new int[checked_cases.length];
        int[] initial_checked_cases= Arrays.copyOf(checked_cases,checked_cases.length);

        StringBuilder generated_password = new StringBuilder();

        while (status) {

            int index = random.nextInt(27);
            int array_index=random.nextInt(checked_cases.length);
            int randomised_case = checked_cases[array_index];///!!!!!


            switch (randomised_case) {

                case 1: {
                    generated_password.append(uppercase_letters.charAt(index % 26));
                    break;
                }

                case 2: {
                    generated_password.append(lowercase_letters.charAt(index % 26));
                    break;
                }

                case 3: {
                    generated_password.append(numbers.charAt(index % 10));
                    break;
                }

                case 4: {
                    generated_password.append(symbols.charAt(index % 13));
                    break;
                }

                default:
                    System.out.println(" Randomised digit error");
                    break;
            }

            if(!block_indexing)
                helper[array_index]=1;

            number_of_characters--;

            if(checking_for_exception(helper)){

                if(checked_cases.length-1==number_of_characters){

                    checked_cases=delete_from_array(checked_cases,helper);
                    helper=new int[checked_cases.length];
                }
            } else {
                checked_cases=Arrays.copyOf(initial_checked_cases,initial_checked_cases.length);
                block_indexing=true;
            }


            if (number_of_characters == 0)
                status = false;

        }

        password_result.setText(generated_password.toString());
    }

    public int[] create_cases_array(int[] checked_cases)
    {
        /// case 1
        if(upper_letters.isChecked()) {
            checked_cases = Arrays.copyOf(checked_cases, checked_cases.length + 1);
            checked_cases[checked_cases.length-1]=1;
        }

        /// case 2
        if(lower_letters.isChecked()){
            checked_cases=Arrays.copyOf(checked_cases,checked_cases.length+1);
            checked_cases[checked_cases.length-1]=2;
        }

        /// case 3
        if(numbers_included.isChecked()){
            checked_cases=Arrays.copyOf(checked_cases,checked_cases.length+1);
            checked_cases[checked_cases.length-1]=3;
        }

        /// case 4
        if(symbols_included.isChecked()){

            checked_cases=Arrays.copyOf(checked_cases,checked_cases.length+1);
            checked_cases[checked_cases.length-1]=4;
        }

        return checked_cases;

    }
    public static int[] delete_from_array(int[] vector,int[] helper){

        int[] copy_vector= new int[vector.length-1];

        int k=0;

        for(int i=0;i<vector.length;i++)
        {
            if(helper[i]==0)
                copy_vector[k++]=vector[i];
        }

        vector=Arrays.copyOf(copy_vector,k);

        return vector;
    }

    public static boolean checking_for_exception(int[] helper)
    {
        int sum=0;
        for(int element: helper) sum+=element;

        return !(sum == helper.length);

    }




}