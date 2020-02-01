package com.controllers;

import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.SeccionModel;
import com.services.web_connections.AreaWCS;
import com.services.web_connections.PersonalWCS;
import com.services.web_connections.ProductoWCS;
import com.services.web_connections.SeccionWCS;
import com.utils.EnvironmentVariables;
import com.utils.adapters.ProductoVentaOrdenAdapter;

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
        return ordenWCService.initOrden();
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

    public boolean addProducto(ProductoVentaModel lastClickedMenu) throws Exception {
        return ordenWCService.addProducto(lastClickedMenu.getPCod());
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter) {
        adapter.increase(lastClickedMenu, ordenWCService);//TODO capas mezcladas actulizando el adaptdor desde el controlador
    }

    public boolean addProducto(ProductoVentaModel lastClickedMenu, float cantidad) throws Exception {
        return ordenWCService.addProducto(lastClickedMenu.getPCod(), cantidad);//TODO capas mezcladas actulizando el adaptdor desde el controlador
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter, float cantidad) {
        adapter.increase(lastClickedMenu, ordenWCService, cantidad);
    }

    public boolean removeProducto(ProductoVentaModel productoVentaModel) throws Exception {
        return ordenWCService.removeProducto(productoVentaModel.getPCod());
    }

    public boolean finishOrden() throws Exception {
        return ordenWCService.finishOrden();
    }

    public boolean sendToKitchen() throws Exception {
        return ordenWCService.sendToKitchen();
    }


    public boolean addNota(ProductoVentaModel productoVentaModel, String nota) throws Exception {
        return ordenWCService.addNota(productoVentaModel.getPCod(), nota);
    }

    public void setCodOrden(String codOrden) {
        ordenWCService.setCodOrden(codOrden);
    }

    public String[] getUsuariosActivos() throws Exception {
        return new PersonalWCS().getPersonalTrabajando();
    }

    public boolean setDeLaCasa(boolean resp) throws Exception {
        return ordenWCService.setDeLaCasa(resp);
    }

    public boolean isDeLaCasa() {
        return ordenWCService.isDeLaCasa();
    }
}
