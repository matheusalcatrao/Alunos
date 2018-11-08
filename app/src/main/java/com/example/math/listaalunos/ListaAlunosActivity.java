package com.example.math.listaalunos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.math.Adpter.AlunoAdpter;
import com.example.math.dao.AlunoDAO;
import com.example.math.modelo.Aluno;

import java.net.URI;
import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAluno = (ListView) findViewById(R.id.lista_alunos);
        listaAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAluno.getItemAtPosition(position);
                Intent vaiFomaulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                vaiFomaulario.putExtra("aluno", aluno);
                startActivity(vaiFomaulario);

            }
        });


        Button btnAdiciona = (Button) findViewById(R.id.lista_alunos_adiciona);
        btnAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(formulario);
            }
        });
        registerForContextMenu(listaAluno);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscaAlunos();
        alunoDAO.close();
        AlunoAdpter adpter = new AlunoAdpter(this,alunos);
       // ArrayAdapter<Aluno> Adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_expandable_list_item_1, alunos);
        listaAluno.setAdapter(adpter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAluno.getItemAtPosition(info.position);

        MenuItem itemTel = menu.add("Ligar");

        itemTel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String [] {Manifest.permission.CALL_PHONE}, 123);
                }else {
                    Intent intentTel = new Intent(Intent.ACTION_CALL);
                    intentTel.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentTel);
                }
                return false;
            }
        });



        MenuItem itemSms = menu.add("Enviar Sms");
        Intent IntentSms = new Intent(Intent.ACTION_VIEW);
        IntentSms.setData(Uri.parse("sms:"+ aluno.getTelefone()));
        itemSms.setIntent(IntentSms);

        MenuItem itemWhats = menu.add("WhatsApp");
        Intent intentWhats = new Intent(Intent.ACTION_SEND);
        intentWhats.putExtra(Intent.EXTRA_TEXT, "Aluno: " + aluno.getNome() + "\nTelefone " + aluno.getTelefone());
        intentWhats.setType("text/plain");
        itemWhats.setIntent(intentWhats);

        MenuItem itemMap = menu.add("Mapa");
        Intent intentMap  = new Intent(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMap.setIntent(intentMap);

        MenuItem site = menu.add("Site");
        Intent intent = new Intent(Intent.ACTION_VIEW );
        String siteAluno = aluno.getSite();

        if (!siteAluno.startsWith("http://") ) {

            siteAluno = "http://" + siteAluno;
        }

        intent.setData(Uri.parse(siteAluno));
        site.setIntent(intent);

        //Deletar
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.delete(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }
}
