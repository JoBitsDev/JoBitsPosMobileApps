package com.activities;

import com.services.models.DetallesVentasModel;

public class TestingOrderBy {

    /*public MesaAdapter orderBy(String orden) {
        if (orden.matches(String.valueOf(R.string.orden))) {
            orderByOrden();
        } else if (orden.matches(String.valueOf(R.string.mesa))) {
            orderByMesas();
        }
        return this;
    }

    private void orderByNombre() {
        Collections.sort(objects);
    }

    private void orderByCantidad() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                return (int) (first.getCantidad() - second.getCantidad());
            }
        });
    }

    private void orderByPrecio() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                float f = Float.parseFloat(first.getPrecioVenta().split(" ")[0]);
                float s = Float.parseFloat(second.getPrecioVenta().split(" ")[0]);
                return (int) (f - s);
            }
        });
    }

    private void orderByMonto() {
        Collections.sort(objects, new Comparator<DetallesVentasModel>() {
            @Override
            public int compare(DetallesVentasModel first, DetallesVentasModel second) {
                float f = Float.parseFloat(first.getMontoVenta().split(" ")[0]);
                float s = Float.parseFloat(second.getMontoVenta().split(" ")[0]);
                return (int) (f - s);
            }
        });
    }*/
}
