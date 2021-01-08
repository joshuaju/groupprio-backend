package de.ccd.groupprio.api;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class ResteasyServer extends Application {
    public static final int PORT = 8080;
    public static final String HOST = "0.0.0.0";
    private Set<Object> singletons = new HashSet<>();

    public ResteasyServer() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
