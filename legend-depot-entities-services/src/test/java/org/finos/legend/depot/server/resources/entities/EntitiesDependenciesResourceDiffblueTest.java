package org.finos.legend.depot.server.resources.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.services.versionedEntities.ManageVersionedEntitiesServiceImpl;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.store.api.versionedEntities.UpdateVersionedEntities;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntitiesDependenciesResourceDiffblueTest {
  /**
   * Test {@link EntitiesDependenciesResource#getAllEntitiesFromDependencies(List, boolean,
   * boolean)}.
   *
   * <ul>
   *   <li>Then return Entity size is one.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesDependenciesResource#getAllEntitiesFromDependencies(List,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getAllEntitiesFromDependencies(List, boolean, boolean); then return Entity size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response EntitiesDependenciesResource.getAllEntitiesFromDependencies(List, boolean, boolean)"
  })
  void testGetAllEntitiesFromDependencies_thenReturnEntitySizeIsOne() {
    // Arrange
    UpdateVersionedEntities entities = mock(UpdateVersionedEntities.class);
    when(entities.getAllEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projects2 =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageVersionedEntitiesServiceImpl entitiesService =
        new ManageVersionedEntitiesServiceImpl(entities, projects2);
    EntitiesDependenciesResource entitiesDependenciesResource =
        new EntitiesDependenciesResource(entitiesService);

    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion());

    // Act
    Response actualAllEntitiesFromDependencies =
        entitiesDependenciesResource.getAllEntitiesFromDependencies(
            projectDependencies, true, true);

    // Assert
    verify(entities).getAllEntities(null, null, null);
    verify(projectsVersions, atLeast(1)).find(null, null, null);
    Object entity = actualAllEntitiesFromDependencies.getEntity();
    assertTrue(entity instanceof List);
    assertTrue(actualAllEntitiesFromDependencies instanceof OutboundJaxrsResponse);
    assertEquals(1, ((List<ProjectVersionEntities>) entity).size());
    assertSame(
        entity,
        ((OutboundJaxrsResponse) actualAllEntitiesFromDependencies).getContext().getEntity());
  }

  /**
   * Test {@link EntitiesDependenciesResource#getAllEntitiesFromDependencies(List, boolean,
   * boolean)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Entity Empty.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesDependenciesResource#getAllEntitiesFromDependencies(List,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getAllEntitiesFromDependencies(List, boolean, boolean); when ArrayList(); then return Entity Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response EntitiesDependenciesResource.getAllEntitiesFromDependencies(List, boolean, boolean)"
  })
  void testGetAllEntitiesFromDependencies_whenArrayList_thenReturnEntityEmpty() {
    // Arrange
    VersionedEntitiesMongo entities = new VersionedEntitiesMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projects2 =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageVersionedEntitiesServiceImpl entitiesService =
        new ManageVersionedEntitiesServiceImpl(entities, projects2);
    EntitiesDependenciesResource entitiesDependenciesResource =
        new EntitiesDependenciesResource(entitiesService);

    // Act
    Response actualAllEntitiesFromDependencies =
        entitiesDependenciesResource.getAllEntitiesFromDependencies(new ArrayList<>(), true, true);

    // Assert
    Object entity = actualAllEntitiesFromDependencies.getEntity();
    assertTrue(entity instanceof List);
    assertTrue(actualAllEntitiesFromDependencies instanceof OutboundJaxrsResponse);
    assertTrue(((List<Object>) entity).isEmpty());
    assertSame(
        entity,
        ((OutboundJaxrsResponse) actualAllEntitiesFromDependencies).getContext().getEntity());
  }
}
