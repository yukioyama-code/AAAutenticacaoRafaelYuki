package ads.pdm.aaautenticacaorafaelyuki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaActivity extends AppCompatActivity {

    private TextView lblUsuario;
    private EditText txtNome;
    private EditText txtNota1;
    private EditText txtNota2;
    private Button btnInserir;
    private Button btnSair;
    private ListView lista;

    private DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth usuarios = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lblUsuario = findViewById(R.id.lblUsuario);
        txtNome = findViewById(R.id.txtNome);
        txtNota1 = findViewById(R.id.txtNota1);
        txtNota2 = findViewById(R.id.txtNota2);
        btnInserir = findViewById(R.id.btnInserir);
        btnSair = findViewById(R.id.btnSair);


        lblUsuario.setText(usuarios.getCurrentUser().getEmail());


    }
    @Override
    public void onBackPressed() {
        // Vazio pra desabilitar o back
    }

    private class EscutadorSair implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // Aviso de início de processo:
            Toast.makeText(ListaActivity.this, "Tentando deslogar do firebase..." , Toast.LENGTH_SHORT).show();


            // Verifica se existe um usuário logado:
            if ( usuarios.getCurrentUser() == null ) {

                // Exibe mensagem que não tem usuário logado, em lblEstado:
                Toast.makeText(ListaActivity.this, "Não tem usuário logado." , Toast.LENGTH_SHORT).show();
            }
            else {

                // Existe usuário logado.

                // Deslogando...
                usuarios.signOut();

                // Exibe mensagem de usuário deslogado:
                Toast.makeText(ListaActivity.this, "Usuário deslogado." , Toast.LENGTH_SHORT).show();
            }
        }
    }


}





