package org.finos.legend.depot.services.pure.model.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.engine.protocol.Protocol;
import org.finos.legend.engine.protocol.pure.m3.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.context.AlloySDLC;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextPointer;
import org.finos.legend.engine.protocol.pure.v1.model.context.SDLC;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PureModelContextServiceImplDiffblueTest {
  @Mock
  private EntitiesService entitiesService;

  @Mock
  private ProjectsService projectsService;

  @InjectMocks
  private PureModelContextServiceImpl pureModelContextServiceImpl;

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", "1.0.2", true, true));
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData(
            "Client version provided is invalid, following are the valid client versions: %s", "42", "42", "1.0.2",
            true, true));
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), "1.0.2", true, true));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData2() {
    // Arrange
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData3() {
    // Arrange
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new IllegalArgumentException("none"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, true));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData4() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData5() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData6() {
    // Arrange
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, false, true);

    // Assert
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData7() {
    // Arrange
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData8() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData9() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(mock(Entity.class));
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualPureModelContextData.getElements();
    assertEquals(1, elements.size());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData10() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    entityList.add(mock(Entity.class));
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualPureModelContextData.getElements();
    assertEquals(1, elements.size());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData11() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    entityList.add(mock(Entity.class));
    when(entitiesService.getEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(entitiesService.getDependenciesEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl.getPureModelContextData("42", "42",
        "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(eq("42"), eq("42"), eq("1.0.2"), eq(true), eq(false));
    verify(entitiesService).getEntities(eq("42"), eq("42"), eq("1.0.2"));
    verify(projectsService).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("1.0.2", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualPureModelContextData.getElements();
    assertEquals(1, elements.size());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData12() {
    // Arrange
    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData(projectDependencies, "1.0.2", true, true));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData13() {
    // Arrange
    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData(projectDependencies, "1.0.2", true, true));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData14() {
    // Arrange
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(new ArrayList<>());

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData15() {
    // Arrange
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(new ArrayList<>());

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData16() {
    // Arrange
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenThrow(new IllegalArgumentException("none"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, true));
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData17() {
    // Arrange
    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(new ProjectVersionEntities("42", "42", "42", new ArrayList<>()));
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData18() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(new EntityDefinition("none", "none", new HashMap<>()));
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData19() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(null);
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertTrue(actualPureModelContextData.getAllElements().isEmpty());
    assertTrue(actualPureModelContextData.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean, boolean)}
   */
  @Test
  void testGetPureModelContextData20() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(new EntityDefinition("none", "none", new HashMap<>()));
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData = pureModelContextServiceImpl
        .getPureModelContextData(new ArrayList<>(), null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    PureModelContextPointer origin = actualPureModelContextData.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    Protocol serializer = actualPureModelContextData.getSerializer();
    assertEquals("pure", serializer.name);
    assertEquals("v1_33_0", serializer.version);
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(((AlloySDLC) sdlc).project);
    assertNull(((AlloySDLC) sdlc).baseVersion);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualPureModelContextData.getElements();
    assertEquals(1, elements.size());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#resolveAndValidateClientVersion(String)}
   */
  @Test
  void testResolveAndValidateClientVersion() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.resolveAndValidateClientVersion("1.0.2"));
    assertEquals("v1_33_0", pureModelContextServiceImpl.resolveAndValidateClientVersion(null));
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", true);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData2() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", true);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData3() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    entityList.add(new EntityDefinition("none", "none", new HashMap<>()));
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", true);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData4() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", false);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData5() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", true);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData6() {
    // Arrange
    Entity entity = mock(Entity.class);
    Mockito.<Map<String, ?>>when(entity.getContent()).thenReturn(new HashMap<>());

    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(entity);
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", true);

    // Assert
    verify(entity).getContent();
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String, String, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData7() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(mock(Entity.class));
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, "42", "42", "42", "1.0.2", false);

    // Assert
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    SDLC sdlc = origin.sdlcInfo;
    assertTrue(sdlc instanceof AlloySDLC);
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("42", ((AlloySDLC) sdlc).baseVersion);
    assertEquals("42:42", ((AlloySDLC) sdlc).project);
    assertEquals("none", ((AlloySDLC) sdlc).version);
    assertEquals("pure", serializer.name);
    List<PackageableElement> allElements = actualBuildPureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(((AlloySDLC) sdlc).artifactId);
    assertNull(((AlloySDLC) sdlc).groupId);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualBuildPureModelContextDataResult.getElements();
    assertEquals(1, elements.size());
    assertTrue(((AlloySDLC) sdlc).packageableElementPointers.isEmpty());
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData8() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData9() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("pure", "pure", new HashMap<>()));
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData10() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(new EntityDefinition("pure", "pure", new HashMap<>()));
    entityList.add(new EntityDefinition("pure", "pure", new HashMap<>()));
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData11() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = mock(AlloySDLC.class);

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(serializer, origin.serializer);
    assertSame(alloySDLC, origin.sdlcInfo);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData12() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", false);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData13() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    assertTrue(actualBuildPureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualBuildPureModelContextDataResult.getElements().isEmpty());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String, boolean)}
   */
  @Test
  void testBuildPureModelContextData14() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(mock(Entity.class));
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult = pureModelContextServiceImpl
        .buildPureModelContextData(entities, alloySDLC, "1.0.2", false);

    // Assert
    Protocol serializer = actualBuildPureModelContextDataResult.getSerializer();
    assertEquals("1.0.2", serializer.version);
    assertEquals("pure", serializer.name);
    List<PackageableElement> allElements = actualBuildPureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    List<PackageableElement> elements = actualBuildPureModelContextDataResult.getElements();
    assertEquals(1, elements.size());
    PureModelContextPointer origin = actualBuildPureModelContextDataResult.getOrigin();
    assertSame(alloySDLC, origin.sdlcInfo);
    assertSame(getResult, elements.get(0));
    assertSame(serializer, origin.serializer);
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData, PureModelContextData)}
   */
  @Test
  void testCombinePureModelContextData() {
    // Arrange
    PureModelContextData rootPMCD = PureModelContextData.newPureModelContextData();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult = pureModelContextServiceImpl
        .combinePureModelContextData(rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    assertNull(actualCombinePureModelContextDataResult.getSerializer());
    assertNull(actualCombinePureModelContextDataResult.getOrigin());
    assertTrue(actualCombinePureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualCombinePureModelContextDataResult.getElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link PureModelContextServiceImpl#buildAlloySDLC(String, String, String)}
   */
  @Test
  void testBuildAlloySDLC() {
    // Arrange and Act
    AlloySDLC actualBuildAlloySDLCResult = pureModelContextServiceImpl.buildAlloySDLC("42", "42", "42");

    // Assert
    assertEquals("42", actualBuildAlloySDLCResult.baseVersion);
    assertEquals("42:42", actualBuildAlloySDLCResult.project);
    assertEquals("none", actualBuildAlloySDLCResult.version);
    assertNull(actualBuildAlloySDLCResult.artifactId);
    assertNull(actualBuildAlloySDLCResult.groupId);
    assertTrue(actualBuildAlloySDLCResult.packageableElementPointers.isEmpty());
  }
}
