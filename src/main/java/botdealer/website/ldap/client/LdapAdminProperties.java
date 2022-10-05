package botdealer.website.ldap.client;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LdapAdminProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
