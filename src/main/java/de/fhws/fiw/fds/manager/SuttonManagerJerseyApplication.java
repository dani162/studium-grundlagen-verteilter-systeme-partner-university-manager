package de.fhws.fiw.fds.manager;

import de.fhws.fiw.fds.manager.server.api.services.DispatcherJerseyService;
import de.fhws.fiw.fds.manager.server.api.services.PartnerJerseyService;
import de.fhws.fiw.fds.sutton.server.api.AbstractJerseyApplication;
import jakarta.ws.rs.ApplicationPath;
import org.apache.catalina.loader.ParallelWebappClassLoader;

import java.util.HashSet;
import java.util.Set;

import static de.fhws.fiw.fds.manager.Constants.APPLICATION_PATH;

@ApplicationPath(APPLICATION_PATH)
public class SuttonManagerJerseyApplication extends AbstractJerseyApplication {

    @Override
    protected Set<Class<?>> getServiceClasses() {
        ParallelWebappClassLoader classloader = (ParallelWebappClassLoader) this.getClass().getClassLoader();
        classloader.setDelegate(true);

        final Set<Class<?>> returnValue = new HashSet<>();

        returnValue.add(DispatcherJerseyService.class);
        returnValue.add(PartnerJerseyService.class);

        return returnValue;
    }
}
