package com.example.sorteadordenmeros;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Declarando variáveis
    public int numeroMax = 100;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isShaking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização do SensorManager e obtenção do Sensor de acelerômetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registro do listener para receber as atualizações do acelerômetro
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remoção do listener quando a atividade está em pausa para economizar recursos
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Captura dos valores do acelerômetro nas direções X, Y e Z
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Cálculo da aceleração total utilizando a fórmula da magnitude do vetor
        double acceleration = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

        // Verificação se o dispositivo foi chacoalhado com base na aceleração
        if (acceleration > 20 && !isShaking) { // Valor de aceleração para considerar como "chacoalhada"
            isShaking = true;
            // Geração de um número aleatório
            generateRandomNumber();
        } else if (acceleration < 10) { // Valor de aceleração para considerar como fim da "chacoalhada"
            isShaking = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não é necessário implementar nada aqui
    }

    public static boolean isStringNotNumber(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e2) {
                return true;
            }
        }
    }

    int x = 1;
    String print = "Lista de números vazia...";

    private void generateRandomNumber() {
        int randomNumber = 0;
        Random random;
        TextView textView = findViewById(R.id.text);
        EditText campoNumero = findViewById(R.id.numMax);

        if (x == 1){
            print = "";
        }

        if (!isStringNotNumber(campoNumero.getText().toString())){
            numeroMax = Integer.parseInt(campoNumero.getText().toString());
        }
        else {
            numeroMax = 100;
        }

        random = new Random();
        randomNumber = random.nextInt(numeroMax + 1);

        print += "N"+x+": " + randomNumber + "\n";
        x+=1;

        textView.setText(print);
    }

    public void limparNumeros (View view){
        x = 1;
        TextView textView = findViewById(R.id.text);
        textView.setText("Lista de números vazia...");
    }

}
