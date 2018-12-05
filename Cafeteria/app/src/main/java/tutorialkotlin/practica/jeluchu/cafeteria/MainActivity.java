package tutorialkotlin.practica.jeluchu.cafeteria;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // VARIABLES
    int numero = 0;
    String texto = "";
    Boolean campo = false;
    private Intent seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // SUMA CANTIDAD
    public void btnSuma(View view)
    {
        numero++;
        TextView cantidad = findViewById(R.id.tvTotal);
        cantidad.setText("" + numero);
    }

    // RESTA CANTIDAD
    public void btnResta(View view)
    {
        // CONTROL DE CANTIDADES
        if (numero > 0)
        {
            numero--;
            TextView cantidades = findViewById(R.id.tvTotal);
            cantidades.setText("" + numero);
        }
        else
        {
            Toast toast = Toast.makeText(this, "No pueden ser menor a 0", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // LLAMADA TELEFÓNICA
    public void btnLlamada(View view)
    {
        Intent telef = new Intent(Intent.ACTION_DIAL);
        telef.setData(Uri.parse("tel:646835828"));
        startActivity(telef);
    }

    // PEDIDO DE CAFÉ
    public void btnOrder(View viev)
    {
        campo = false;

        EditText nombre = findViewById(R.id.etName);
        CheckBox checkNube = findViewById(R.id.cbNubes);
        CheckBox checkCaramel = findViewById(R.id.cbCaramelo);
        TextView total = findViewById(R.id.tvTotal);

        // COMPROBANDO SI LOS CAMPOS ESTÁN VACIOS / SELECCIONADOS
        if(!nombre.getText().toString().isEmpty())
        {
            texto = "Nombre: " + nombre.getText().toString();
            campo = true;
        }
        else
        {
            Toast toast = Toast.makeText(this, "Introduce un nombre", Toast.LENGTH_SHORT);
            toast.show();
            campo = false;
        }

        if(total.getText().toString().trim().equalsIgnoreCase("0"))
        {
            Toast toast = Toast.makeText(this, "No puedes hacer un pedido vacío", Toast.LENGTH_SHORT);
            toast.show();
            campo = false;
        }

        if(checkNube.isChecked() || checkCaramel.isChecked())
        {
            texto += "\n" + getString(R.string.complementos) + " ";
            campo = true;
        }
        if(checkNube.isChecked())
        {
            texto += checkNube.getText().toString();
        }
        if(checkCaramel.isChecked())
        {
            texto += checkNube.getText().toString();
        }

        double precioTotal;
        double precCafe = 1.25;
        double preNube = 0.50;
        double preCaramel = 0.25;

        if (!checkNube.isChecked() && !checkCaramel.isChecked())
        {
            precioTotal = precCafe * Integer.valueOf(total.getText().toString());
            texto += "\n El precio total es:  " + precioTotal + "€";
        }
        if (checkNube.isChecked() && checkCaramel.isChecked())
        {
            precioTotal = (precCafe * Integer.valueOf(total.getText().toString())) + preNube + preCaramel;
            texto += "\n El precio total es:  " + precioTotal + "€";
        }
        if (checkNube.isChecked())
        {
            precioTotal = (precCafe * Integer.valueOf(total.getText().toString()))+ preNube;
            texto += "\n El precio total es:  " + precioTotal + "€";
        }
        if(checkCaramel.isChecked())
        {
            precioTotal = (precCafe * Integer.valueOf(total.getText().toString())) + preCaramel;
            texto += "\n El precio total es:  " + precioTotal + "€";
        }

         if (!nombre.getText().toString().isEmpty() && !total.getText().toString().trim().equalsIgnoreCase("0"))
         {
             Intent mail = new Intent(Intent.ACTION_SEND);
             mail.setData(Uri.parse("mailto:"));
             String to[] = {"jesus.calderon@unisys.com"};
             mail.putExtra(mail.EXTRA_EMAIL, to);
             mail.putExtra(mail.EXTRA_SUBJECT, "CONFIRMACIÓN DE PEDIDO");
             mail.putExtra(Intent.EXTRA_TEXT, texto);
             mail.setType("PED/01234");
             seleccion = Intent.createChooser(mail, "Enviar Email");
             startActivity(seleccion);

         }

    }

    // UBICACIÓN
    public void btnMaps(View view)
    {
        Intent map = new Intent(Intent.ACTION_VIEW);

        // SE ENVÍA LA LATITUD Y LA LONGITUD DE LA POSICIÓN
        map.setData(Uri.parse("geo:40.4233174,-3.6580868"));
        seleccion = Intent.createChooser(map, "Abriendo Maps");
        startActivity(map);
    }

}
