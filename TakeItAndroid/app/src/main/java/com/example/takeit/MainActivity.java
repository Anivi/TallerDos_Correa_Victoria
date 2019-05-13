package com.example.takeit;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener, Observer {

    Button rojo, verde,amarillo;
    static public int activo = 0;

    static  public ConexionUDP conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rojo = findViewById(R.id.btn_rojo);
        verde = findViewById(R.id.btn_verde);
        amarillo = findViewById(R.id.btn_amarillo);

        rojo.setOnClickListener(this);
        verde.setOnClickListener(this);
        amarillo.setOnClickListener(this);

        ConexionUDP.setConexion("172.30.197.134", 5000);
        conexion = ConexionUDP.getInicializar();
        conexion.getObservador(this);


        /**Alarma --------------------------------------------------------------*/

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Despierta a las: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rojo:
                if(activo == 2){
                  //  Toast.makeText(this, "rojo", Toast.LENGTH_SHORT).show();
                    conexion.enviar("0");
                }

                break;
            case R.id.btn_verde:
                if(activo == 3){
                    conexion.enviar("0");
                }

                break;


            case R.id.btn_amarillo:
                if(activo == 1){
                    conexion.enviar("0");
                }

                break;
        }
    }

    @Override
    public void serialEvent(String datos) {

    }

    @Override
    public void recibirMensaje(String mensaje) {

    }
}