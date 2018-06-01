package rhernandes.whatsappmessenger;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    private String number;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        number = (((EditText) findViewById(R.id.number)).getText().toString());
        message = (((EditText) findViewById(R.id.message)).getText().toString());
        if (valideUserData()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+506" + number + "&text=" + message));
                startActivity(intent);
            } catch (Exception e) {
                if (e instanceof ActivityNotFoundException) {
                    confirmDialog();
                } else {
                    Toast.makeText(this, "No se puede abrir whatsapp", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean valideUserData() {
        if (number.isEmpty()) {
            Toast.makeText(this, "Campo teléfono es requerido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (number.length() < 8) {
            Toast.makeText(this, "Por favor ingrese un número de teléfono valido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (message.isEmpty()) {
            Toast.makeText(this, "Campo mensaje es requerido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void confirmDialog() {
        new MaterialDialog.Builder(this)
                .title("WhatsApp Messenger")
                .titleColorRes(R.color.colorAccent)
                .content("¿Desea instalar WhatsApp?")
                .contentColorRes(R.color.colorPrimary)
                .positiveText("Instalar")
                .positiveColorRes(R.color.colorAccent)
                .negativeText("Ahora no")
                .negativeColorRes(R.color.secondaryText)
                .backgroundColorRes(R.color.white)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        try {
                            String url = "https://play.google.com/store/apps/details?id=com.whatsapp";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                })
                .show();
    }

}
