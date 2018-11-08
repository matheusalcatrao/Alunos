package com.example.math.Adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.math.listaalunos.ListaAlunosActivity;
import com.example.math.listaalunos.R;
import com.example.math.modelo.Aluno;

import java.util.List;
import java.util.zip.Inflater;

public class AlunoAdpter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunoAdpter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        //if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        //}
        TextView campo_nome = (TextView) view.findViewById(R.id.item_nome);
        TextView campo_telefone = view.findViewById(R.id.item_telefone);
        ImageView campoFoto = view.findViewById(R.id.item_foto);
        campo_nome.setText(aluno.getNome());
        campo_telefone.setText(aluno.getTelefone());

        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return view;
    }
}
