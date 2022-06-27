package br.com.alura.agenda2;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.alura.agenda2.model.Aluno;

import static android.content.ContentValues.TAG;

public class FormularioHelper {
    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText site;
    private final RatingBar nota;
    private Aluno aluno;

    public FormularioHelper(CadastroDeAlunosActivity activity) {
        nome = activity.findViewById(R.id.formulario_nome);
        endereco = activity.findViewById(R.id.formulario_endereco);
        telefone = activity.findViewById(R.id.formulario_telefone);
        site = activity.findViewById(R.id.formulario_site);
        nota = activity.findViewById(R.id.formulario_nota);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {

        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;

    }

    public void preencheAluno(Aluno aluno) {
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        nota.setRating(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}
