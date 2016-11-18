package com.pes.enfaixapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pes.enfaixapp.AsyncResult;
import com.pes.enfaixapp.HTTPHandler;
import com.pes.enfaixapp.JSONConverter;
import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.pes.enfaixapp.JSONConverter.toNoticies;
/**
 * Created by carlos on 15/11/2016.
 */


/**
 * A ListadoNoticiasFragment fragment containing a simple view.
 */
public class ListadoNoticiasFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static RecyclerView recycler;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager lManager;
    private static View rootView;
    public ListadoNoticiasFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListadoNoticiasFragment newInstance(int sectionNumber) {

        ListadoNoticiasFragment fragment = new ListadoNoticiasFragment();
        Bundle args = new Bundle();

        List<Noticia> noticias;
        if(sectionNumber == 1) {
            args.putBoolean("TODASNOTICIAS", true);;
        }else {
            noticias = getNoticiasBackend(false);
            args.putBoolean("TODASNOTICIAS", false);;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_noticia, container, false);

        MyAsync async = new MyAsync(rootView.getContext());
        async.callWall(rootView.getContext());
        return rootView;
    }

    public void insertarNoticias(final List<Noticia> noticas) {
        // Obtener el Recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new NoticiasAdapter((List<Noticia>) noticas);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Uri uri = Uri.parse(noticas.get(position).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
        );
    }
    private static List<Noticia> getNoticiasBackend(boolean todasNoticies) {
        List noticias = new ArrayList();

        if(todasNoticies) {
            noticias.add(new Noticia("Vila 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vila 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vila 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilas 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilafd 1", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vi o la 2 VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Vilau 3", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
        }else {
            noticias.add(new Noticia("Castellers", "Atentado terrorista en", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Castellers VilaFranca", "25 years old", R.drawable.ic_menu_camera));
            noticias.add(new Noticia("Castellers vs Castellers", "Enfaixapp guanya la competicio de apps", R.drawable.ic_menu_camera));
        }

        return noticias;
    }

    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void callWall(Context context) {
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("GET", "http://10.4.41.165:5000/wall", null);
        }

        @Override
        public void processFinish(JSONObject output) {
            try {
                insertarNoticias(JSONConverter.toNoticies(output));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}