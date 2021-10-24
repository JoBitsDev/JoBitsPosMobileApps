package com.services.web_connections;

import com.services.web_connections.interfaces.SiteInfoWCI;
import com.utils.EnvironmentVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jorge on 2/8/18.
 */

public class GeneralWCS extends RetrofitBaseConection {

    public HashMap<String, Object> readInfo() throws Exception {
        SiteInfoWCI infoService = retrofit.create(SiteInfoWCI.class);
        HashMap<String, Object> ret = infoService.getSiteVersion(TENNANT_TOKEN,HTTP_HEADER_BASIC+TOKEN).execute().body();
        ret.put("nombre","<>");
        ret.put("monedaPrincipal","MN");
        return ret;
    }

    public boolean uploadQueque() throws Exception {
        return true;//TODO: missing
        //return super.uploadQueque();
    }
}
