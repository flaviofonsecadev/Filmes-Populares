package com.example.meusfilmes.ui.listafilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meusfilmes.R;
import com.example.meusfilmes.data.mapper.FilmeMapper;
import com.example.meusfilmes.data.model.Filme;
import com.example.meusfilmes.data.network.ApiService;
import com.example.meusfilmes.data.network.response.FilmesResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaFilmesAdapter filmesAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        configuraToolbar();

        configuraAdapter();

        obtemFilmes();
    }

    private void configuraToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void configuraAdapter(){
        recyclerView = findViewById(R.id.recycler_filmes);

        filmesAdapter = new ListaFilmesAdapter();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(filmesAdapter);

    }

    private void obtemFilmes(){
        ApiService.getInstance()
                .obterFilmesPopulares("54b79920963253e0b34a289edf72f0f6")
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()){
                            final List<Filme> listaFilmes = FilmeMapper
                                    .deResponseParaDominio(response.body().getResultadoFilmes());

                            filmesAdapter.setFilmes(listaFilmes);
                        } else {
                            mostraErro();
                        }

                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {
                        mostraErro();
                    }
                });
    }

    private void mostraErro(){
        Toast.makeText(this, "Erro ao obter lista de filmes.", Toast.LENGTH_LONG).show();
    }


}

