package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/3/30 15:21
 */
@Data
public class SessionProperties {
    /**
     * 同一个用户在系统中的最大session数默认为1
     */
    private int maximumSessions =1;
    /**
     * 达到最大session数时，是否阻止新的登录请求，默认不阻止，新的登录会将老的session踢掉
     */
    private boolean maxSessio0nPreventsLogin ;

    private String sessionInvalidUrl =SecurityConstants.DEFAULT_SESSION_INVALID_URL;

}
