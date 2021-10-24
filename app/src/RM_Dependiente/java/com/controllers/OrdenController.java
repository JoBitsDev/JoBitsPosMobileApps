package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.orden.OrdenModel;
import com.services.models.orden.ProductoVentaModel;
import com.services.models.orden.ProductoVentaOrdenModel;
import com.services.models.orden.SeccionModel;
import com.services.web_connections.AreaWCS;
import com.services.web_connections.OrdenWCS;
import com.services.web_connections.PersonalWCS;
import com.services.web_connections.ProductoWCS;
import com.services.web_connections.SeccionWCS;
import com.utils.EnvironmentVariables;
import com.utils.adapters.ProductoVentaOrdenAdapter;
import com.utils.exception.ServerErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de OrdenActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller aplicacion.
 */
public class OrdenController extends BaseController {

    private final String user;
    private OrdenWCS ordenWCService = null;

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

    public List<SeccionModel> getSecciones(String codMesa) throws Exception {
        return new SeccionWCS().getSeccionesOf(codMesa);
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
        return true;
    }

    public boolean cederAUsuario(String usuario) throws Exception {
        return ordenWCService.cederAUsuario(usuario);
    }

    public String getNota(ProductoVentaOrdenModel pVO) throws Exception {
        return ordenWCService.getNota(pVO.getId());
    }

    public String getComensal(ProductoVentaOrdenModel pVO) throws Exception {
        throw new ServerErrorException("Funcionalidad deshabilitada", 500);
    }


    public boolean addComensal(ProductoVentaOrdenModel pVO, String comensal) throws Exception {
        throw new ServerErrorException("Funcionalidad deshabilitada", 500);

    }

    public boolean moverAMesa(String codMesa) throws Exception {
        return ordenWCService.moverAMesa(codMesa);
    }

    public String[] getMesas() throws Exception {
        return new AreaWCS().findVacias(ordenWCService.getCodMesa());
    }

    public OrdenModel addProducto(ProductoVentaModel lastClickedMenu, float cantidad) throws Exception {
        return ordenWCService.addProducto(lastClickedMenu.getPCod(), cantidad);
//        if (!EnvironmentVariables.ONLINE) {
//            boolean exitProduct = false;
//            for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
//                if (prod.getProductoVenta().getPCod().matches(lastClickedMenu.getPCod())) {
//                    exitProduct = true;
//                    prod.setCantidad(prod.getCantidad() + cantidad);
//                    break;
//                }
//            }
//            if (!exitProduct) {
//                ProductoVentaOrdenModel p = new ProductoVentaOrdenModel();
//                p.setCantidad(cantidad);
//                p.setEnviadosACocina(0f);
//                p.setProductoVenta(lastClickedMenu);
//                orden.getProductoVentaOrdenList().add(p);
//            }
//            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
//        }
//        return true;
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter, float cantidad) {
        adapter.increase(lastClickedMenu, ordenWCService, cantidad);
    }

    public boolean removeProducto(ProductoVentaOrdenModel productoVentaModel, float cant) throws Exception {
        OrdenModel orden = ordenWCService.removeProducto(productoVentaModel.getId(), cant);
        if (!EnvironmentVariables.ONLINE) {
            for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
                if (prod.getId() == productoVentaModel.getId()) {
                    if (cant > prod.getCantidad()) {
                        throw new NullPointerException("No se puede eliminar mas de lo que tiene la orden.");
                    }
                    prod.setCantidad(prod.getCantidad() - cant);
                    break;
                }
            }
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return true;
    }

    public boolean finishOrden() throws Exception {
        boolean resp = false;
        if (puedoCerrar(ordenWCService.getCodOrden())) {
            resp = ordenWCService.finishOrden();
        }
        return resp;
    }

    private boolean puedoCerrar(String codOrden) throws Exception {
        OrdenModel orden = new OrdenWCS(user).findOrden(codOrden);
        for (ProductoVentaOrdenModel prod : orden.getProductoVentaOrdenList()) {
            if (prod.getEnviadosACocina() < prod.getCantidad()) {
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
                prod.setEnviadosACocina(prod.getCantidad());
            }
            ordenWCService.saveOrdenToCache(new ObjectMapper().writeValueAsString(orden));
        }
        return resp;
    }

    public boolean addNota(ProductoVentaOrdenModel productoVentaModel, String nota) throws Exception {
        ordenWCService.addNota(productoVentaModel.getId(), nota);
        return true;
    }

    public String[] getUsuariosActivos() throws Exception {
        return new PersonalWCS().getPersonalOnline();
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
