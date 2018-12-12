package android.jeluchu.com.escogerfoto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView = null;
    Button button = null;

    private Intent chooser;
    private final int REQUEST_CODE_IMAGE = 1;
    private final int REQUEST_CODE_EMAIL = 2;

    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OCULTAR ACTION BAR
        getSupportActionBar().hide();

        // IGUALAR AL ID QUE QUERAMOS
        imageView = findViewById(R.id.ivSelect);
        button = findViewById(R.id.btnGuardar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/jpg");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String to[] = {"jesus.calderon@unisys.com"};
                intent.putExtra(intent.EXTRA_EMAIL, to);
                intent.putExtra(intent.EXTRA_SUBJECT, "Fotopp");
                intent.putExtra(intent.EXTRA_TEXT, "Te adjunto esta fotografía seleccionada desde mi app");
                intent.setType("message/rfc822");
                chooser = Intent.createChooser(intent, "Enviar E-mail");
                intent.putExtra(intent.EXTRA_STREAM, uri);

                startActivity(chooser);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CODE_IMAGE) {
                uri = data.getData();
                imageView.setImageURI(uri);
            } else {
                Toast.makeText(MainActivity.this, "Comprueba la imagen", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == REQUEST_CODE_EMAIL) {
                Toast.makeText(MainActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
