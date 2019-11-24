package com.gmail.jesusdc99.crudproject.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import com.gmail.jesusdc99.crudproject.interfaces.AboutInterface;
import com.gmail.jesusdc99.crudproject.presenters.AboutPresenter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.jesusdc99.crudproject.R;

public class AboutActivity extends AppCompatActivity implements AboutInterface.View {

    private static final String TAG = "APPCRUD/About";
    private Context myContext;
    private AboutPresenter presenter;
    private TextView comentariosTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myContext = this;

        // Flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos el presentador
        presenter = new AboutPresenter(this);

        initializeWidgets();
        initializeWidgetsListeners();

    }

    @Override
    public void initializeWidgets() {
        comentariosTextView = (TextView) findViewById(R.id.comentariosTextView);
        loadFeedbackLink();
    }

    @Override
    public void initializeWidgetsListeners() {

    }

    @Override
    public void loadFeedbackLink() {
        // Para copiar el correo al portapapeles, no funciona bien
        /*CharSequence label = myContext.getText(R.string.email_copied_clipboard);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label , "ajchicano@iesfranciscodelosrios.es");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(myContext, "Copiado", Toast.LENGTH_SHORT).show();*/
        // https://stackoverflow.com/a/4689397/10387022
        String feedback = getResources().getString(R.string.send_feedback);
        String html = "<a href=\"mailto:ajchicano@iesfranciscodelosrios.es\">"+feedback+"</a>";
        comentariosTextView.setText(Html.fromHtml(html));
        comentariosTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /*******************************************/
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"Ejecutando onStart...");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"Ejecutando onRestart...");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Ejecutando onResume...");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Ejecutando onPause...");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"Ejecutando onStop...");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"Ejecutando onDestroy...");
    }
}
