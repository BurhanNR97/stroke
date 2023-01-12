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
import com.diagnosa.stroke.kelas.cNormalisasi;

import java.util.List;

public class aNormalisasi extends RecyclerView.Adapter {

    private List<cNormalisasi> dataList;

    public aNormalisasi(List<cNormalisasi> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tabel_normalisasi, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtS1.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtS2.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtS3.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtS4.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtKriteria.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtS1.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtS2.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtS3.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtS4.setTypeface(null, Typeface.BOLD);

            rowViewHolder.txtKriteria.setText("Kriteria");
            rowViewHolder.txtS1.setText("S1");
            rowViewHolder.txtS2.setText("S2");
            rowViewHolder.txtS3.setText("S3");
            rowViewHolder.txtS4.setText("S4");
        } else {
            cNormalisasi data = dataList.get(rowPos - 1);

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.border_left);
            rowViewHolder.txtS1.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtS2.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtS3.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtS4.setBackgroundResource(R.drawable.border_right_bottom);

            rowViewHolder.txtKriteria.setText(data.getKriteria() + "");
            rowViewHolder.txtS1.setText(data.getS1() + "");
            rowViewHolder.txtS2.setText(data.getS2() + "");
            rowViewHolder.txtS3.setText(data.getS3() + "");
            rowViewHolder.txtS4.setText(data.getS4() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtKriteria;
        TextView txtS1;
        TextView txtS2;
        TextView txtS3;
        TextView txtS4;

        RowViewHolder(View itemView) {
            super(itemView);
            txtKriteria = itemView.findViewById(R.id.tv_normalisasiKriteria);
            txtS1 = itemView.findViewById(R.id.tv_normalisasiS1);
            txtS2 = itemView.findViewById(R.id.tv_normalisasiS2);
            txtS3 = itemView.findViewById(R.id.tv_normalisasiS3);
            txtS4 = itemView.findViewById(R.id.tv_normalisasiS4);
        }
    }
}
