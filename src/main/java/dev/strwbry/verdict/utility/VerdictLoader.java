package dev.strwbry.verdict.utility;

import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class VerdictLoader implements PluginLoader
{
    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        
        // Add HikariCP dependency
        resolver.addDependency(new Dependency(
            new DefaultArtifact("com.zaxxer:HikariCP:5.1.0"),
            null
        ));
        
        // Add MySQL Connector dependency
        resolver.addDependency(new Dependency(
            new DefaultArtifact("com.mysql:mysql-connector-j:8.0.33"),
            null
        ));
        
        // Add Maven Central repository
        resolver.addRepository(new RemoteRepository.Builder(
            "central",
            "default",
            "https://repo.maven.apache.org/maven2"
        ).build());
        
        classpathBuilder.addLibrary(resolver);
    }
}
