package com.example.pcapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CarList extends ArrayAdapter<Car> {

    private Activity context;
    private List<Car> carList;

    public CarList(Activity context, List<Car> carList) {
        super(context, R.layout.list_layout, carList);
        this.context = context;
        this.carList = carList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null);
        TextView txtBrand = listViewItem.findViewById(R.id.txtBrand);
        TextView txtModel = listViewItem.findViewById(R.id.txtModel);
        TextView txtFuel = listViewItem.findViewById(R.id.txtFuel);

        Car car = carList.get(position);

        txtBrand.setText(car.getBrand());
        txtModel.setText(car.getModel());
        txtFuel.setText(car.getFuel());

        return listViewItem;
    }
}
