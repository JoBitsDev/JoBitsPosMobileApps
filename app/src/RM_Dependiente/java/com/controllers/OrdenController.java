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

    public static final String urlListProducts_ = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.orden/LISTPRODUCTS_";

    private OrdenWCS ordenWCService = null;

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

    public List<ProductoVentaModel> getProductos(String codMesa) throws Exception {
        return new ProductoWCS().getProducts(codMesa);
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
        new MesasController().initOrdenEnMesa(orden, mesa);
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
                    prod.setCantidad(prod.getCantidad() + cantidad);
                    break;
                }
            }
            if (!exitProduct) {
                ProductoVentaOrdenModel p = new ProductoVentaOrdenModel();
                p.setCantidad(cantidad);
                p.setEnviadosacocina(0);
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

    public boolean removeProducto(ProductoVentaModel productoVentaModel) throws Exception {
        return ordenWCService.removeProducto(productoVentaModel.getPCod());
    }

    public boolean finishOrden() throws Exception {
        boolean resp = ordenWCService.finishOrden();
        if (!EnvironmentVariables.ONLINE) {
            new MesasController().terminarOrdenEnMesa(ordenWCService.getCodMesa());
        }
        return resp;
    }

    public boolean sendToKitchen() throws Exception {
        boolean resp =  ordenWCService.sendToKitchen();
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
