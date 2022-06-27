package br.com.alura.agenda2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.alura.agenda2.dao.AlunoDAO;
import br.com.alura.agenda2.model.Aluno;

public class CadastroDeAlunosActivity extends AppCompatActivity {


    private FormularioHelper helper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                AlunoDAO dao = new AlunoDAO(this);
                Aluno aluno = helper.pegaAluno();

                if(aluno.getId() != null){
                    dao.altera(aluno);
                }else {
                    dao.insere(aluno);
                }
                dao.close();

                Toast.makeText(CadastroDeAlunosActivity.this, "Aluno salvo com sucesso"
                        , Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_alunos);
        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null){

            helper.preencheAluno(aluno);
        }



    }


}