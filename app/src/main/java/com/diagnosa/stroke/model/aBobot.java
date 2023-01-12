package com.diagnosa.stroke.model;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.kelas.cBobot;

import java.util.List;

public class aBobot extends RecyclerView.Adapter {

    private List<cBobot> dataList;

    public aBobot(List<cBobot> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tabel_bobot, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtBobot.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtKriteria.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtBobot.setTypeface(null, Typeface.BOLD);

            rowViewHolder.txtKriteria.setText("Kriteria");
            rowViewHolder.txtBobot.setText("Bobot");
        } else {
            cBobot data = dataList.get(rowPos - 1);

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.border_left);
            rowViewHolder.txtBobot.setBackgroundResource(R.drawable.border_right_bottom);

            rowViewHolder.txtKriteria.setText(data.getKriteria() + "");
            rowViewHolder.txtBobot.setText(data.getBobot() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtKriteria;
        TextView txtBobot;

        RowViewHolder(View itemView) {
            super(itemView);
            txtKriteria = itemView.findViewById(R.id.tv_kriteria);
            txtBobot = itemView.findViewById(R.id.tv_bobot);
        }
    }
}
