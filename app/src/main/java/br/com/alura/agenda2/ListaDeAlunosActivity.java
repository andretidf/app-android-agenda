package br.com.alura.agenda2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda2.dao.AlunoDAO;
import br.com.alura.agenda2.model.Aluno;

import static android.content.Intent.ACTION_CALL;

public class ListaDeAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_alunos);

        listaAlunos = findViewById(R.id.lista_de_alunos);
        registerForContextMenu(listaAlunos);
        Button botaoAdicionar = findViewById(R.id.novo_aluno);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirFormularioActivity = new Intent(ListaDeAlunosActivity.this, CadastroDeAlunosActivity.class);
                startActivity(abrirFormularioActivity);
            }
        });

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);

                Intent intent = new Intent(ListaDeAlunosActivity.this, CadastroDeAlunosActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaDeAlunos();
    }

    private void carregaListaDeAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
        MenuItem itemLigar = menu.add("Ligar");
            itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(ActivityCompat.checkSelfPermission(ListaDeAlunosActivity.this, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ListaDeAlunosActivity.this, new String[] {
                                Manifest.permission.CALL_PHONE}, 1);
                    }else {
                        Intent intentLigar = new Intent(ACTION_CALL);
                        intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));

                        startActivity(intentLigar);
                    }
                    return false;
                }
            });

        MenuItem enviarSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        intentSMS.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        enviarSMS.setIntent(intentSMS);




        MenuItem visitarSite = menu.add("Abrir o Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse("http://" + aluno.getSite()));
        intentSite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        visitarSite.setIntent(intentSite);


        MenuItem vaiParaMapa = menu.add("Abrir no Mapa");
        Intent vaiMapa = new Intent(Intent.ACTION_VIEW);
        vaiMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        vaiMapa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        vaiParaMapa.setIntent(vaiMapa);


        MenuItem deletar = menu.add("Deletar");




        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaDeAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregaListaDeAlunos();
                return false;
            }
        });
    }


}