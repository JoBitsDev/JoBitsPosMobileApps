package com.controllers;

import com.services.models.MesaModel;
import com.services.models.OrdenModel;
import com.services.models.ProductoVentaModel;
import com.services.models.ProductoVentaOrdenModel;
import com.services.models.SeccionModel;
import com.services.parsers.MesaXMlParser;
import com.services.parsers.ProductoVentaXMlParser;
import com.services.parsers.ProductoVentaOrdenXMLParser;
import com.services.parsers.SeccionXMlParser;
import com.services.web_connections.OrdenWebConnectionService;
import com.utils.EnvironmentVariables;
import com.utils.adapters.ProductoVentaOrdenAdapter;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.Collections;
import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de OrdenActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller aplicacion.
 */
public class OrdenController extends BaseController {

    public static final String urlSeccion = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.seccion",
            urlProducts_ = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.productoventa/PRODUCTS_",
            urlListProducts_ = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.orden/LISTPRODUCTS_";

    private OrdenWebConnectionService ordenWCService = null;

    public OrdenController starService(String codOrden, String codMesa, String usuarioTrabajador) {
        ordenWCService = new OrdenWebConnectionService(codOrden, codMesa, usuarioTrabajador);
        return this;
    }

    public OrdenController starService(String codMesa, String usuarioTrabajador) {
        ordenWCService = new OrdenWebConnectionService(codMesa, usuarioTrabajador);
        return this;
    }

    public List<SeccionModel> getSecciones() {//TODO: Ordenamiento en el server
        List<SeccionModel> list = new SeccionXMlParser().fetch(urlSeccion);
        Collections.sort(list);
        return list;
    }

    public List<ProductoVentaModel> getProductos(String codMesa) {//TODO: Ordenamiento en el server
        List<ProductoVentaModel> list = new ProductoVentaXMlParser().fetch(urlProducts_ + codMesa);
        Collections.sort(list);
        return list;
    }

    public List<ProductoVentaOrdenModel> getProductoVentaOrden(String codOrden) {
        return new ProductoVentaOrdenXMLParser(codOrden).fetch(urlListProducts_ + codOrden);
    }

    public List<ProductoVentaOrdenModel> getProductoVentaOrden() {
        String codOrden = ordenWCService.getCodOrden();
        return getProductoVentaOrden(codOrden);
    }

    public String getCodOrden() {
        return ordenWCService.getCodOrden();
    }

    public boolean initOrden() throws ServerErrorException, NoConnectionException {
        return ordenWCService.initOrden();
    }

    public boolean cederAUsuario(String usuario) throws ServerErrorException, NoConnectionException {
        return ordenWCService.cederAUsuario(usuario);
    }

    public String getNota(ProductoVentaOrdenModel pVO) throws ServerErrorException, NoConnectionException {
        return ordenWCService.getNota(pVO.getProductoVentaModel().getPCod());
    }

    public String getComensal(ProductoVentaOrdenModel pVO) throws ServerErrorException, NoConnectionException {
        return ordenWCService.getComensal(pVO.getProductoVentaModel().getPCod());
    }

    public boolean addComensal(ProductoVentaOrdenModel pVO, String comensal) throws ServerErrorException, NoConnectionException {
        return ordenWCService.addComensal(pVO.getProductoVentaModel().getPCod(), comensal);
    }

    public boolean moverAMesa(String codMesa) throws ServerErrorException, NoConnectionException {
        return ordenWCService.moverAMesa(codMesa);
    }

    public String[] getMesas() {
        String url = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/" + EnvironmentVariables.STARTPATH + "com.restmanager.mesaNoLabel/MOSTRARVACIAS";

        final List<MesaModel> mesaModels = new MesaXMlParser().fetch(url);

        Collections.sort(mesaModels);//TODO: Ordenamiento en el servidor

        String[] codMesas = new String[mesaModels.size()];
        for (int i = 0; i < mesaModels.size(); i++) {
            codMesas[i] = mesaModels.get(i).getCodMesa();
        }

        return codMesas;
    }

    public boolean addProducto(ProductoVentaModel lastClickedMenu) throws ServerErrorException, NoConnectionException {
        return ordenWCService.addProducto(lastClickedMenu.getPCod());
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter) {
        adapter.increase(lastClickedMenu, ordenWCService);
    }

    public boolean addProducto(ProductoVentaModel lastClickedMenu, float cantidad) throws ServerErrorException, NoConnectionException {
        return ordenWCService.addProducto(lastClickedMenu.getPCod(), cantidad);
    }

    public void increasePoducto(ProductoVentaModel lastClickedMenu, ProductoVentaOrdenAdapter adapter, int cantidad) {
        adapter.increase(lastClickedMenu, ordenWCService, cantidad);
    }

    public boolean removeProducto(ProductoVentaModel productoVentaModel) throws ServerErrorException, NoConnectionException {
        return ordenWCService.removeProducto(productoVentaModel.getPCod());
    }

    public boolean finishOrden(boolean deLaCasa) throws ServerErrorException, NoConnectionException {
        return ordenWCService.finishOrden(deLaCasa);
    }

    public boolean sendToKitchen() throws ServerErrorException, NoConnectionException {
        return ordenWCService.sendToKitchen();
    }


    public boolean addNota(ProductoVentaModel productoVentaModel, String nota) throws ServerErrorException, NoConnectionException {
        return ordenWCService.addNota(productoVentaModel.getPCod(), nota);
    }

    public void setCodOrden(String codOrden) {
        ordenWCService.setCodOrden(codOrden);
    }
}
