package ads.pdm.aaautenticacaorafaelyuki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnLogin;
    private Button btnCriar;

    private FirebaseAuth usuarios = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnCriar = findViewById(R.id.btnCriar);
        btnLogin = findViewById(R.id.btnLogin);


        if ( usuarios.getCurrentUser() != null ) {
            Intent i = new Intent( getApplicationContext(), ListaActivity.class );
            startActivity(i);
        }

        btnLogin.setOnClickListener( new EscutadorBotaoEntrar() );
        btnCriar.setOnClickListener( new EscutadorBotaoCriar() );
    }


    private class EscutadorBotaoEntrar implements View.OnClickListener {

        @Override
        public void onClick(View view) {


            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();


            usuarios.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(Task<AuthResult> task) {


                            if ( task.isSuccessful() ) {
                                Toast.makeText(MainActivity.this, "Usu??rio logado com sucesso: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent( getApplicationContext(), ListaActivity.class );
                                startActivity(i);

                            }
                            else {


                                Toast.makeText(MainActivity.this, "Login falhou! ", Toast.LENGTH_SHORT).show();


                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(MainActivity.this, "ERRO: "  + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private class EscutadorBotaoCriar implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Toast.makeText(MainActivity.this, "Tentando criar novo usu??rio. ", Toast.LENGTH_SHORT).show();

            // Verifica se o usu??rio j?? est?? logado:
            if (usuarios.getCurrentUser() != null) {

                // Exibe mensagem de usu??rio j?? logado, em lblEstado:
                Toast.makeText(MainActivity.this, "Usu??rio j?? logado " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            } else {

                // Usu??rio n??o logado.

                // Pega email e senha na interface:
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                // Tenta criar o usu??rio:
                // ATENCAO!!!
                // O teste se foi sucesso ou n??o ?? ASSINCRONO,
                // isto ??, pode levar um tempo para o resultado voltar do Firebase.
                // N??o pense neste processo como uma programa????o tradicional!!!
                usuarios.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(Task<AuthResult> task) {

                                // Testa se criou o usu??rio com sucesso:
                                if (task.isSuccessful()) {

                                    // Criou e logou com sucesso.
                                    // Exibe mensagem em lblEstado:
                                    Toast.makeText(MainActivity.this, "Usu??rio criado e logado com sucesso " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                                } else {

                                    // N??o conseguiu criar o usu??rio.
                                    // Exibe mensagem em lblEstado:
                                    Toast.makeText(MainActivity.this, " Cria????o do usu??rio falhou ", Toast.LENGTH_SHORT).show();

                                    // Exibe a mensagem de erro do Firebase num Toast:
                                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                    Toast.makeText(MainActivity.this, "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }

    }
}