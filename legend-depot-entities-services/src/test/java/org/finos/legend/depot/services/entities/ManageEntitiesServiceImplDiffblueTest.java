package org.finos.legend.depot.services.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageEntitiesServiceImplDiffblueTest {
  @InjectMocks
  private ManageEntitiesServiceImpl<StoredEntity> manageEntitiesServiceImpl;

  @Mock
  private UpdateEntities updateEntities;

  /**
   * Test {@link ManageEntitiesServiceImpl#getStoredEntities(String, String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#getStoredEntities(String, String, String)}
   */
  @Test
  @DisplayName("Test getStoredEntities(String, String, String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ManageEntitiesServiceImpl.getStoredEntities(String, String, String)"})
  void testGetStoredEntities_thenReturnEmpty() {
    // Arrange
    VersionedEntitiesMongo entities = mock(VersionedEntitiesMongo.class);
    when(entities.getStoredEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    ManageEntitiesServiceImpl<StoredEntity> manageEntitiesServiceImpl = new ManageEntitiesServiceImpl<>(entities,
        mock(ProjectsService.class));

    // Act
    List<StoredEntity> actualStoredEntities = manageEntitiesServiceImpl.getStoredEntities("42", "42", "42");

    // Assert
    verify(entities).getStoredEntities(eq("42"), eq("42"), eq("42"));
    assertTrue(actualStoredEntities.isEmpty());
  }

  /**
   * Test {@link ManageEntitiesServiceImpl#delete(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ManageEntitiesServiceImpl.delete(String, String, String)"})
  void testDeleteWithGroupIdArtifactIdVersionId_thenReturnOne() throws IllegalArgumentException {
    // Arrange
    VersionedEntitiesMongo entities = mock(VersionedEntitiesMongo.class);
    when(entities.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    ProjectsService projects = mock(ProjectsService.class);
    doNothing().when(projects).checkExists(Mockito.<String>any(), Mockito.<String>any());
    ManageEntitiesServiceImpl<StoredEntity> manageEntitiesServiceImpl = new ManageEntitiesServiceImpl<>(entities,
        projects);

    // Act
    long actualDeleteResult = manageEntitiesServiceImpl.delete("42", "42", "42");

    // Assert
    verify(projects).checkExists(eq("42"), eq("42"));
    verify(entities).delete(eq("42"), eq("42"), eq("42"));
    assertEquals(1L, actualDeleteResult);
  }

  /**
   * Test {@link ManageEntitiesServiceImpl#delete(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#delete(String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String) with 'groupId', 'artifactId'; then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ManageEntitiesServiceImpl.delete(String, String)"})
  void testDeleteWithGroupIdArtifactId_thenReturnOne() throws IllegalArgumentException {
    // Arrange
    VersionedEntitiesMongo entities = mock(VersionedEntitiesMongo.class);
    when(entities.delete(Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    ProjectsService projects = mock(ProjectsService.class);
    doNothing().when(projects).checkExists(Mockito.<String>any(), Mockito.<String>any());
    ManageEntitiesServiceImpl<StoredEntity> manageEntitiesServiceImpl = new ManageEntitiesServiceImpl<>(entities,
        projects);

    // Act
    long actualDeleteResult = manageEntitiesServiceImpl.delete("42", "42");

    // Assert
    verify(projects).checkExists(eq("42"), eq("42"));
    verify(entities).delete(eq("42"), eq("42"));
    assertEquals(1L, actualDeleteResult);
  }

  /**
   * Test {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}.
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(String, String, String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ManageEntitiesServiceImpl.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdate() {
    // Arrange
    when(updateEntities.createOrUpdate(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<List<Entity>>any())).thenReturn(new ArrayList<>());

    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("Path", "Classifier Path", new HashMap<>()));

    // Act
    manageEntitiesServiceImpl.createOrUpdate("42", "42", "42", entityList);

    // Assert
    verify(updateEntities).createOrUpdate(eq("42"), eq("42"), eq("42"), isA(List.class));
  }

  /**
   * Test {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}.
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(String, String, String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ManageEntitiesServiceImpl.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdate2() {
    // Arrange
    when(updateEntities.createOrUpdate(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<List<Entity>>any())).thenReturn(new ArrayList<>());

    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("Path", "Classifier Path", new HashMap<>()));
    entityList.add(new EntityDefinition("Path", "Classifier Path", new HashMap<>()));

    // Act
    manageEntitiesServiceImpl.createOrUpdate("42", "42", "42", entityList);

    // Assert
    verify(updateEntities).createOrUpdate(eq("42"), eq("42"), eq("42"), isA(List.class));
  }

  /**
   * Test {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageEntitiesServiceImpl#createOrUpdate(String, String, String, List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(String, String, String, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ManageEntitiesServiceImpl.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdate_whenArrayList() {
    // Arrange
    when(updateEntities.createOrUpdate(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<List<Entity>>any())).thenReturn(new ArrayList<>());

    // Act
    manageEntitiesServiceImpl.createOrUpdate("42", "42", "42", new ArrayList<>());

    // Assert
    verify(updateEntities).createOrUpdate(eq("42"), eq("42"), eq("42"), isA(List.class));
  }
}
