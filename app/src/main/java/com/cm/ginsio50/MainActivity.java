package com.cm.ginsio50;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] idades = {"15-19","20-24","25-29","30-34","35-39",">40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Caixas de texto
        EditText dia_data_nascimento = (EditText)findViewById(R.id.dia_data_nascimento);
        EditText mes_data_nascimento = (EditText)findViewById(R.id.mes_data_nascimento);
        EditText ano_data_nascimento = (EditText)findViewById(R.id.ano_data_nascimento);
        EditText ano_curso = (EditText)findViewById(R.id.ano_curso_edit);
        EditText nif = (EditText)findViewById(R.id.nif_edit);
        EditText telemovel = (EditText)findViewById(R.id.telemovel_edit);

        //RadioGroup
        RadioGroup grupo_radio = (RadioGroup)findViewById(R.id.escolas_radio_group);
        //Faixas etárias
        Spinner spinner_idade = (Spinner)findViewById(R.id.spinner_idade);

        Button submeter_butao = (Button)findViewById(R.id.butao_submeter);

        //EditTexts
        dia_data_nascimento.setFilters(new InputFilter[]{new InputFilterMinMax("1","31")});
        mes_data_nascimento.setFilters(new InputFilter[]{new InputFilterMinMax("1","12")});
        ano_curso.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});




        spinner_idade.setOnItemSelectedListener(this);

        //ArrayAdapter do Spinner acima declarado
        ArrayAdapter idade_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, idades);

        idade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_idade.setAdapter(idade_adapter);

        //Ao submeter dados usando o butão SUBMETER
        submeter_butao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmar Dados")
                        .setMessage("Confirma os dados inseridos?")

                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                    if(grupo_radio.getCheckedRadioButtonId() == -1){
                                        Toast.makeText(MainActivity.this, "Por favor seleccione uma escola!", Toast.LENGTH_SHORT).show();
                                    }

                                //Ano da data de nascimento
                                    if(dia_data_nascimento.getText().toString().matches("")){
                                        Toast.makeText(MainActivity.this, "Dia: Valor não válido", Toast.LENGTH_SHORT).show();
                                        dia_data_nascimento.setText("");
                                    }
                                    if(mes_data_nascimento.getText().toString().matches("")){
                                        Toast.makeText(MainActivity.this, "Mês: Valor não válido", Toast.LENGTH_SHORT).show();
                                        mes_data_nascimento.setText("");
                                    }

                                    if(ano_data_nascimento.getText().toString().matches("")){
                                        Toast.makeText(MainActivity.this, "Ano: Valor não válido", Toast.LENGTH_SHORT).show();
                                        ano_data_nascimento.setText("");
                                    } else {

                                        if (1945 > Integer.parseInt(ano_data_nascimento.getText().toString()) || Integer.parseInt(ano_data_nascimento.getText().toString()) > 2005) {
                                            Toast.makeText(MainActivity.this, "Ano demasiado baixo para admissão", Toast.LENGTH_SHORT).show();
                                            ano_data_nascimento.setText("");
                                        }
                                    }
                            }
                        })
                        .setNegativeButton("Recuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Confirme os seus dados", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });


    }

    //Filtro para o dia da data de nascimento
    public class InputFilterMinMax implements InputFilter {
        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Creating the MenuInflater for my main activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_main_menu, menu);
        return true;
    }

    //Creating the method for click events for the main activity menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.about_menu_option:
                AlertDialog.Builder about_builder = new AlertDialog.Builder(MainActivity.this);
                about_builder.setTitle("About this project...")
                        .setMessage("\nCreated by bloom \n\nGithub: Bloombug")
                        .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
};