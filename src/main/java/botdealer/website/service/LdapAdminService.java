package botdealer.website.service;

import botdealer.website.ldap.client.LdapAdminProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapName;
import java.util.Properties;

@Service
public class LdapAdminService implements BaseLdapNameAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapAdminService.class);

    @Autowired
    private LdapAdminProperties ldapAdminProperties;

    public boolean verifyAccount(String username, String password) {
        String url = "ldap://NORTEST.UAT.COM.VN:389";
        String[] urlArr = new String[]{url};

        if (url != null && url.indexOf("&") > 0) {
            urlArr = url.split("&");
        }
        if (urlArr == null || urlArr.length <= 0) return false;

        for (int i = 0; i < urlArr.length; i++) {
            try {
                //Use the service user to authenticate
//                String url = urlArr[i];
                String domain = url.substring(url.indexOf("ldap://") + 7, url.indexOf(":389"));
                DirContext context = getEnvContext(url, username.trim().toUpperCase() + "@" + domain, password);
                if (context != null) return true;

            } catch (Exception e) {
                LOGGER.warn("Verify account Ldap admin FALSE userName:{} ", username);
                return false;
            }
        }
        return false;
    }

    public static DirContext getEnvContext(String url, String userName, String password) {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, url);
            props.put(Context.SECURITY_PRINCIPAL, userName);
            props.put(Context.SECURITY_CREDENTIALS, password);

            return new InitialDirContext(props);
        } catch (Exception ex) {
            LOGGER.error("Connect Ldap admin FALSE url:{} userName:{} ", url, userName);
            return null;
        }
    }

    @Override
    public void setBaseLdapPath(LdapName ldapName) {

    }
}
