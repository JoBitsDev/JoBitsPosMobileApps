package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.*;
import com.services.web_connections.*;
import com.utils.EnvironmentVariables;
import com.utils.adapters.ProductoVentaOrdenAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de OrdenActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller aplicacion.
 */
public class OrdenController extends BaseController {

    private OrdenWCS ordenWCService = null;

    private String user;

    public OrdenController(String user) {
        this.user = user;
    }

    public OrdenController starService(String codOrden, String codMesa) throws Exception {
        ordenWCService = new OrdenWCS(codOrden, codMesa);
        return this;
    }

    public OrdenController starService(String codMesa) throws Exception {
        ordenWCService = new OrdenWCS(codMesa);
        return this;
    }

    public List<SeccionModel> getSecciones() throws Exception {
        return new SeccionWCS().getSecciones();
    }

    public List<ProductoVentaModel> getProductos(String codArea) throws Exception {
        return new ProductoWCS().getProducts(codArea);
    }

    public List<ProductoVentaOrdenModel> getProductoVentaOrden(String codOrden) throws Exception {
        return ordenWCService.findOrden(codOrden).getProductoVentaOrdenList();
    }

    public List<ProductoVentaOrdenModel> getProductoVentaOrden() throws Exception {
        String codOrden = ordenWCService.getCodOrden();
        return getProductoVentaOrden(codOrden);
    }

    public String getCodOrden() {
        return ordenWCService.getCodOrden();
    }

    public boolean initOrden() throws Exception {
        OrdenModel orden = ordenWCService.initOrden();
        orden.setProductoVentaOrdenList(new ArrayList<ProductoVentaOrdenModel>());
        String mesa = ordenWCService.getCodMesa();
        new MesasController(user).initOrdenEnMesa(orden, mesa);
        ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        return true;
    }

    public boolean cederAUsuario(String usuario) throws Exception {
        return ordenWCService.cederAUsuario(usuario);
    }

    public String getNota(ProductoVentaOrdenModel pVO) throws Exception {
        return ordenWCService.getNota(pVO.getProductoVenta().getPCod());
    }

    public String getComensal(ProductoVentaOrdenModel pVO) throws Exception {
        return ordenWCService.getComensal(pVO.getProductoVenta().getPCod());
    }

    public boolean addComensal(ProductoVentaOrdenModel pVO, String comensal) throws Exception {
        return ordenWCService.addComensal(pVO.getProductoVenta().getPCod(), comensal);
    }

    public boolean moverAMesa(String codMesa) throws Exception {
        return ordenWCService.moverAMesa(codMesa);
    }

    public String[] getMesas() throws Exception {
        return new AreaWCS().findVacias(ordenWCService.getCodMesa());
    }

    public boolean addProducto(ProductoVentaModel lastClickedMenu, float cantidad) throws Exception {
        boolean resp = ordenWCService.addProducto(lastClickedMenu.getPCod(), cantidad);
        if (!EnvironmentVariables.ONLINE) {
            OrdenModel orden = ordenWCService.findOrden(ordenWCService.getCodOrden());
            boolean exitProduct = false;
            for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
                if (prod.getProductoVenta().getPCod().matches(lastClickedMenu.getPCod())) {
                    exitProduct = true;
                    prod.setCantidad(prod.getCantidad() + cantidad);
                    break;
                }
            }
            if (!exitProduct) {
                ProductoVentaOrdenModel p = new ProductoVentaOrdenModel();
                p.setCantidad(cantidad);
                p.setEnviadosacocina(0f);
                p.setOrdenModel(orden);
                p.setProductoVenta(lastClickedMenu);
                p.setProductovOrdenPKModel(null);
                orden.getProductoVentaOrdenList().add(p);
            }
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return resp;
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter, float cantidad) {
        adapter.increase(lastClickedMenu, ordenWCService, cantidad);
    }

    public boolean removeProducto(ProductoVentaModel productoVentaModel, float cant) throws Exception {
        boolean resp = ordenWCService.removeProducto(productoVentaModel.getPCod(), cant);
        if (!EnvironmentVariables.ONLINE) {
            OrdenModel orden = ordenWCService.findOrden(ordenWCService.getCodOrden());
            for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
                if (prod.getProductoVenta().getPCod().matches(productoVentaModel.getPCod())) {
                    if (cant > prod.getCantidad()) {
                        throw new NullPointerException("No se puede eliminar mas de lo que tiene la orden.");
                    }
                    prod.setCantidad(prod.getCantidad() - cant);
                    break;
                }
            }
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return resp;
    }

    public boolean finishOrden() throws Exception {
        boolean resp = false;
        if (puedoCerrar(ordenWCService.getCodOrden())) {
            resp = ordenWCService.finishOrden();
            if (!EnvironmentVariables.ONLINE) {
                new MesasController(user).terminarOrdenEnMesa(ordenWCService.getCodMesa());
            }
        }
        return resp;
    }

    private boolean puedoCerrar(String codOrden) throws Exception {
        OrdenModel orden = new OrdenWCS(user).findOrden(codOrden);
        for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
            if (prod.getEnviadosacocina() < prod.getCantidad()) {
                return false;
            }
        }
        return true;
    }

    public boolean sendToKitchen() throws Exception {
        boolean resp = ordenWCService.sendToKitchen();
        if (!EnvironmentVariables.ONLINE) {
            OrdenModel orden = ordenWCService.findOrden(ordenWCService.getCodOrden());
            for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
                prod.setEnviadosacocina(prod.getCantidad());
            }
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return resp;
    }

    public boolean addNota(ProductoVentaModel productoVentaModel, String nota) throws Exception {
        return ordenWCService.addNota(productoVentaModel.getPCod(), nota);
    }

    public String[] getUsuariosActivos() throws Exception {
        return new PersonalWCS().getPersonalTrabajando();
    }

    public boolean isDeLaCasa() {
        return ordenWCService.isDeLaCasa();
    }

    public int getRestantes(String codProd) throws Exception {
        return new ProductoWCS().getRestantes(codProd);
    }

    public boolean setDeLaCasa(boolean casa) throws Exception {
        boolean resp = ordenWCService.setDeLaCasa(casa);
        if (!EnvironmentVariables.ONLINE) {
            OrdenModel orden = ordenWCService.findOrden(ordenWCService.getCodOrden());
            orden.setDeLaCasa(casa);
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return resp;
    }
}
