package com.example.tp5jr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListadoJuegosAdapter extends BaseAdapter {
    private List<Juegos> listaJuegos;

    public ListadoJuegosAdapter(List<Juegos> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }

    @Override
    public int getCount() {
        return this.listaJuegos.size();
    }

    @Override
    public Juegos getItem(int position) {
        return this.listaJuegos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        if(convertView == null) {
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listado_juegos,null);
        }
        else {
            layout = convertView;
        }

        Juegos item = getItem(position);

        TextView txtListadoJuegosAdapterNombre = (TextView) layout.findViewById(R.id.txtListadoJuegosAdapterNombre);
        TextView txtListadoJuegosAdapterDescripcion = (TextView) layout.findViewById(R.id.txtListadoJuegosAdapterDescripcion);
        ImageView imgListadoJuegosAdapterJuego = (ImageView) layout.findViewById(R.id.imgListadoJuegosAdapterJuego);

        Picasso.get()
                .load(item.getImagen())
                .into(imgListadoJuegosAdapterJuego);

        txtListadoJuegosAdapterNombre.setText(item.getNombre());
        txtListadoJuegosAdapterDescripcion.setText(item.getDescripcion());
        return layout;
    }
}
