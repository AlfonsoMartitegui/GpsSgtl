package com.example.gpssgtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.EditText;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class DayView extends AppCompatActivity {
    TextView Fpuer,Servi,Embal,Reti,Fepack,Baster,Pedir,Pendis;
    DatePickerDialog picker;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        Fpuer= findViewById(R.id.TxtForPuer);
        Servi= findViewById(R.id.TxtSer);
        Embal= findViewById(R.id.TxtEmbal);
        Reti= findViewById(R.id.TxtReti);
        Fepack=findViewById(R.id.TxtFepack);
        Baster=findViewById(R.id.TxtBastis);
        Pedir=findViewById(R.id.TxtPuertasPedidas);
        Pendis=findViewById(R.id.TxtPEndientes);

        Button BtLoad = findViewById(R.id.BtLoad);
        BtLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fpuer.setText("");
                Servi.setText("");
                Embal.setText("");
                Reti.setText("");
                Fepack.setText("");
                Baster.setText("");
                Pedir.setText("");
                Pendis.setText("");
               new Task().execute();

            }
        });
       eText=(EditText)  findViewById(R.id.TxtFecha);
       eText.setInputType(InputType.TYPE_NULL);
       eText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Calendar cldr=Calendar.getInstance();
               int day=cldr.get(Calendar.DAY_OF_MONTH);
               int month=cldr.get(Calendar.MONTH);
               int year=cldr.get(Calendar.YEAR);
               //date pick
               picker=new DatePickerDialog(getApplicationContext(),
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                           }
                       },year,month,day);
               picker.show();
           }
       });
    }
    class Task extends AsyncTask<Void, Void, Void> {
        String puertasForradas="",error="",servidas ="",embaladas="",retira="",fepac="",basti="",pedidas="",pendientes="";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://185.70.172.47:53005/g.p.s. 09", "amartitegui",  "2dm1n1str2d0r");
                Statement statement= connection.createStatement();
                ResultSet resultSet= statement.executeQuery("SELECT count(*) as numero FROM `g.p.s. 09`.numeros_serie WHERE fecha_fabricacion= CURDATE() ");
                while (resultSet.next()){
                    puertasForradas+=resultSet.getString(1);
                }

                resultSet= statement.executeQuery("SELECT count(*) as numero FROM `g.p.s. 09`.numeros_serie WHERE fecha_entrega= CURDATE() ");
                while (resultSet.next()){
                    servidas+=resultSet.getString(1);
                }


                resultSet= statement.executeQuery("SELECT count(*) as numero FROM `g.p.s. 09`.numeros_serie WHERE embalado= CURDATE() ");
                while (resultSet.next()){
                    embaladas+=resultSet.getString(1);
                }

                resultSet= statement.executeQuery("SELECT count(*) as numero FROM `g.p.s. 09`.numeros_serie WHERE fecha_entrega= CURDATE() and embalado is null ");
                while (resultSet.next()){
                    retira+=resultSet.getString(1);
                }
               // puertasForradas=resultSet.getString(1);
                resultSet= statement.executeQuery("select count(*) as recuento FROM reg_fab_fepack where date(fecha_hora)=CURDATE() AND cantidad_ejecutada=1  ");
                while (resultSet.next()){
                    fepac+=resultSet.getString(1);
                }

                resultSet= statement.executeQuery("select count(*) as recuento FROM reg_fab_bastidor where date(fecha_hora)=CURDATE() AND cantidad_ejecutada=1  ");
                while (resultSet.next()){
                    basti+=resultSet.getString(1);
                }

                resultSet= statement.executeQuery("select `g.p.s. 09`.NumeroPuertasPedidasDia(CURDATE())  ");
                while (resultSet.next()){
                    pedidas+=resultSet.getString(1);
                }
                resultSet= statement.executeQuery("select `g.p.s. 09`.NumeroPuertasPendientes() ");
                while (resultSet.next()){
                    pendientes+=resultSet.getString(1);
                }
            }
            catch (Exception e){
                error=e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Fpuer.setText(puertasForradas);
            Servi.setText(servidas);
            Embal.setText(embaladas);
            Reti.setText(retira);
            Fepack.setText(fepac);
            Baster.setText(basti);
            Pedir.setText(pedidas);
            Pendis.setText(pendientes);
            super.onPostExecute(aVoid);
        }
    }
}