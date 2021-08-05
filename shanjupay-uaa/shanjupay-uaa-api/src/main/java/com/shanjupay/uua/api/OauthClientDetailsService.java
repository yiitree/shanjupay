package com.shanjupay.uua.api;

import java.util.Map;

public interface OauthClientDetailsService {

    void createClientDetails(Map map);

    /**
     * 根据appId查询 client信息  appId也就是client_id
     * @param appId
     * @return
     */
    Map getClientDetailsByClientId(String appId);

}
