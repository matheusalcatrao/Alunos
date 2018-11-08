package com.example.math.listaalunos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.math.modelo.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity Activity) {
         campoNome = Activity.findViewById(R.id.formulario_nome);
         campoEndereco = Activity.findViewById(R.id.formulario_endereco);
         campoTelefone = Activity.findViewById(R.id.formulario_telefone);
         campoSite = Activity.findViewById(R.id.formulario_site);
         campoNota = Activity.findViewById(R.id.formulario_ratingbar);
         campoFoto = Activity.findViewById(R.id.formulario_foto);
         aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheCampo(Aluno aluno) {
      campoNome.setText(aluno.getNome());
      campoEndereco.setText(aluno.getEndereco());
      campoSite.setText(aluno.getSite());
      campoTelefone.setText(aluno.getTelefone());
      campoNota.setProgress(aluno.getNota().intValue());
      carregaImagem(aluno.getCaminhoFoto());
      this.aluno = aluno;

    }

    public void carregaImagem(String caminho) {
        if (caminho != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminho);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminho);
        }

    }
}
