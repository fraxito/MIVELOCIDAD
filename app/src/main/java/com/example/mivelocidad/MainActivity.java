package com.example.mivelocidad;
/**
@author Jorge Cisneros
 */
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity  {
    private TextView mTextEstado;  //la etiqueta que muestra el estado del dispositivo
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextEstado = findViewById(R.id.text_estado);
            //manejo del sensor. Como el dispositivo tiene varios, nos centraremos sólo en el de aceleración
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Este método se encarga de escuchar el sensor. Para la demo sólo leo los
     * desplazamientos en el eje X
     */
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = Math.abs(event.values[0]);
            //float yValue = Math.abs(event.values[1]);
            //float zValue = Math.abs(event.values[2]);
                //Sólo tenemos en cuenta el desplazamiento en X para el ejercicio
            mTextEstado.setText("Estado actual: " + leerVelocidad(xValue));
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    /**
     *
     * @param velocidad contiene el valor detectado por el sensor para el desp en X
     * @return
     */
    public String leerVelocidad(float velocidad) {
        //dependiendo del valor del sensor, saldrá por uno u otro
        if (velocidad >= 0 && velocidad <= 1) {
            return "ESTADO PARADO";
        } else if (velocidad > 1 && velocidad <= 4) {
            return "ESTADO CAMINANDO";
        } else if (velocidad > 4 && velocidad <= 6) {
            return "ESTADO MARCHANDO";
        } else if (velocidad > 6 && velocidad <= 12) {
            return "ESTADO CORRIENDO";
        } else if (velocidad > 12 && velocidad <= 25) {
            return "ESTADO SPRINT";
        } else if (velocidad > 25 && velocidad <= 170) {
            return "ESTADO VEH. MOTOR TERRESTRE";
        } else if (velocidad > 170) {
            return "ESTADO VEH. MOTOR AÉREO";
        }

        return "Estado desconocido";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}