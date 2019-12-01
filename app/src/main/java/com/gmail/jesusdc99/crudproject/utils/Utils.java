package com.gmail.jesusdc99.crudproject.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.jesusdc99.crudproject.views.FormularioActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    private static final String TAG = "APPCRUD/Utils";

    public static void getDatePicker(View v, final TextView inputDate, final Context context){
        // https://youtu.be/5qdnoRHfAYU
        Calendar fechaActual = Calendar.getInstance();
        final int dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        final int mes = fechaActual.get(Calendar.MONTH);
        final int ano = fechaActual.get(Calendar.YEAR);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Abriendo calendario...");
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1; // Se le suma +1 porque aparece el mes anterior
                        inputDate.setText(dayOfMonth+"/"+month+"/"+year);
                        Log.d(TAG, "Fecha seleccionada...");
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    public static boolean validateDate(CharSequence date){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        // Input to be parsed should strictly follow the defined date format above.
        format.setLenient(false);

        try {
            format.parse(date.toString());
        } catch (ParseException e) {
            Log.d(TAG, "Fecha " + date + " no valida ");
            return false;
        }
        return true;
    }

    public static boolean validateNota(CharSequence nota) {
        int numValue;

        try {
            numValue = Integer.parseInt(nota.toString());
            if(nota.length() == 0 || nota.length() > 2 || numValue < 0 || numValue > 10){ // Vacio o con mas de 2 caracteres
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }

        return true;
    }
}
