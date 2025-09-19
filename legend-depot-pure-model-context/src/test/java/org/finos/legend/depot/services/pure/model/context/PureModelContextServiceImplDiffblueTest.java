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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.engine.protocol.Protocol;
import org.finos.legend.engine.protocol.bigqueryFunction.metamodel.BigQueryFunction;
import org.finos.legend.engine.protocol.pure.m3.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.context.AlloySDLC;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData.Builder;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextPointer;
import org.finos.legend.engine.protocol.pure.v1.model.context.SDLC;
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
class PureModelContextServiceImplDiffblueTest {
  @Mock private EntitiesService entitiesService;

  @Mock private ProjectsService projectsService;

  @InjectMocks private PureModelContextServiceImpl pureModelContextServiceImpl;

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                "42", "42", "42", "1.0.2", true, true));
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol2() {
    // Arrange
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, false, false);

    // Assert
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol3() {
    // Arrange
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                "42", "42", "42", null, false, false));
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol4() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, false, false);

    // Assert
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    assertEquals(allElements, actualPureModelContextData.getElements());
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol5() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    EntityDefinition entityDefinition2 = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition2);
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, false, false);

    // Assert
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(2, allElements.size());
    PackageableElement getResult = allElements.get(1);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(2, actualPureModelContextData.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol6() {
    // Arrange
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                "42", "42", "42", null, false, false));
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol7() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol8() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenThrow(new IllegalArgumentException());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                "42", "42", "42", null, true, false));
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol9() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol10() {
    // Arrange
    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", new ArrayList<>());
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(projectVersionEntitiesList);
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol11() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol12() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, true);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol13() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition =
        new EntityDefinition("Path", "Classifier Path", new HashMap<>());
    entityList.add(entityDefinition);
    entityList.add(mock(Entity.class));
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(1, actualPureModelContextData.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(String, String, String, String,
   * boolean, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(String,
   * String, String, String, boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(String, String, String, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocol14() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    EntityDefinition entityDefinition2 =
        new EntityDefinition("Path", "Classifier Path", new HashMap<>());
    entityList.add(entityDefinition2);
    entityList.add(mock(Entity.class));
    when(entitiesService.getDependenciesEntities(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(entitiesService.getEntities(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(entityList);
    when(projectsService.resolveAliasesAndCheckVersionExists(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn("1.0.2");

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData("42", "42", "42", null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities("42", "42", "1.0.2", true, false);
    verify(entitiesService).getEntities("42", "42", "1.0.2");
    verify(projectsService).resolveAliasesAndCheckVersionExists("42", "42", "42");
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(1, actualPureModelContextData.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                new ArrayList<>(), "1.0.2", true, true));
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol2() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(new ArrayList<>());

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, false);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol3() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                new ArrayList<>(), null, true, false));
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol4() {
    // Arrange
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(new ArrayList<>());

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, true);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol5() {
    // Arrange
    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", new ArrayList<>());
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, false);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol6() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entities.add(entityDefinition);
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, false);

    // Assert
    verify(entitiesService).getDependenciesEntities(isA(List.class), eq(true), eq(true));
    List<PackageableElement> allElements = actualPureModelContextData.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(1, actualPureModelContextData.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol7() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entities.add(entityDefinition);
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, true);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol8() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(null);
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", entities);

    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    projectVersionEntitiesList.add(projectVersionEntities);
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, true);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol9() {
    // Arrange
    ArrayList<ProjectVersionEntities> projectVersionEntitiesList = new ArrayList<>();
    ProjectVersionEntities projectVersionEntities =
        new ProjectVersionEntities("42", "42", "42", new ArrayList<>());
    projectVersionEntitiesList.add(projectVersionEntities);
    ProjectVersionEntities projectVersionEntities2 =
        new ProjectVersionEntities("42", "42", "42", new ArrayList<>());
    projectVersionEntitiesList.add(projectVersionEntities2);
    when(entitiesService.getDependenciesEntities(
            Mockito.<List<ProjectVersion>>any(), anyBoolean(), anyBoolean()))
        .thenReturn(projectVersionEntitiesList);

    // Act
    PureModelContextData actualPureModelContextData =
        pureModelContextServiceImpl.getPureModelContextData(new ArrayList<>(), null, true, false);

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
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol10() {
    // Arrange
    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");
    projectDependencies.add(projectVersion);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                projectDependencies, "1.0.2", true, true));
  }

  /**
   * Test {@link PureModelContextServiceImpl#getPureModelContextData(List, String, boolean,
   * boolean)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#getPureModelContextData(List, String,
   * boolean, boolean)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(List, String, boolean, boolean) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.getPureModelContextData(List, String, boolean, boolean)"
  })
  void
      testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocol11() {
    // Arrange
    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");
    projectDependencies.add(projectVersion);
    ProjectVersion projectVersion2 = new ProjectVersion("42", "42", "42");
    projectDependencies.add(projectVersion2);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            pureModelContextServiceImpl.getPureModelContextData(
                projectDependencies, "1.0.2", true, true));
  }

  /**
   * Test {@link PureModelContextServiceImpl#resolveAndValidateClientVersion(String)}.
   *
   * <ul>
   *   <li>When {@code 1.0.2}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#resolveAndValidateClientVersion(String)}
   */
  @Test
  @DisplayName(
      "Test resolveAndValidateClientVersion(String); when '1.0.2'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PureModelContextServiceImpl.resolveAndValidateClientVersion(String)"})
  void testResolveAndValidateClientVersion_when102_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> pureModelContextServiceImpl.resolveAndValidateClientVersion("1.0.2"));
  }

  /**
   * Test {@link PureModelContextServiceImpl#resolveAndValidateClientVersion(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code v1_33_0}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#resolveAndValidateClientVersion(String)}
   */
  @Test
  @DisplayName("Test resolveAndValidateClientVersion(String); when 'null'; then return 'v1_33_0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PureModelContextServiceImpl.resolveAndValidateClientVersion(String)"})
  void testResolveAndValidateClientVersion_whenNull_thenReturnV1330() {
    // Arrange, Act and Assert
    assertEquals("v1_33_0", pureModelContextServiceImpl.resolveAndValidateClientVersion(null));
  }

  /**
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol2() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(entities, alloySDLC, "1.0.2", false);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol3() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("pure", "pure", new HashMap<>());
    entityList.add(entityDefinition);
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol4() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("pure", "pure", new HashMap<>());
    entityList.add(entityDefinition);
    EntityDefinition entityDefinition2 = new EntityDefinition("pure", "pure", new HashMap<>());
    entityList.add(entityDefinition2);
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol5() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    Stream<Entity> entities = entityList.stream();
    AlloySDLC alloySDLC = new AlloySDLC();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(entities, alloySDLC, "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, AlloySDLC, String,
   * boolean)} with {@code entities}, {@code alloySDLC}, {@code clientVersion}, {@code
   * convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * AlloySDLC, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, AlloySDLC, String, boolean) with 'entities', 'alloySDLC', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, AlloySDLC, String, boolean)"
  })
  void testBuildPureModelContextDataWithEntitiesAlloySDLCClientVersionConvertToNewProtocol6() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(mock(Entity.class));
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, new AlloySDLC(), "1.0.2", false);

    // Assert
    List<PackageableElement> allElements = actualBuildPureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(1, actualBuildPureModelContextDataResult.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol2() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", false);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol3() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol4() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    EntityDefinition entityDefinition = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition);
    EntityDefinition entityDefinition2 = new EntityDefinition("none", "none", new HashMap<>());
    entityList.add(entityDefinition2);
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol5() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(null);
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", true);

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
   * Test {@link PureModelContextServiceImpl#buildPureModelContextData(Stream, String, String,
   * String, String, boolean)} with {@code entities}, {@code groupId}, {@code artifactId}, {@code
   * versionId}, {@code clientVersion}, {@code convertToNewProtocol}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildPureModelContextData(Stream,
   * String, String, String, String, boolean)}
   */
  @Test
  @DisplayName(
      "Test buildPureModelContextData(Stream, String, String, String, String, boolean) with 'entities', 'groupId', 'artifactId', 'versionId', 'clientVersion', 'convertToNewProtocol'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.buildPureModelContextData(Stream, String, String, String, String, boolean)"
  })
  void
      testBuildPureModelContextDataWithEntitiesGroupIdArtifactIdVersionIdClientVersionConvertToNewProtocol6() {
    // Arrange
    ArrayList<Entity> entityList = new ArrayList<>();
    entityList.add(mock(Entity.class));
    Stream<Entity> entities = entityList.stream();

    // Act
    PureModelContextData actualBuildPureModelContextDataResult =
        pureModelContextServiceImpl.buildPureModelContextData(
            entities, "42", "42", "42", "1.0.2", false);

    // Assert
    List<PackageableElement> allElements = actualBuildPureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    PackageableElement getResult = allElements.get(0);
    assertNull(getResult.getPath());
    assertNull(getResult._package);
    assertNull(getResult.name);
    assertNull(getResult.sourceInformation);
    assertEquals(1, actualBuildPureModelContextDataResult.getElements().size());
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName("Test combinePureModelContextData(PureModelContextData, PureModelContextData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData() {
    // Arrange
    Builder newBuilderResult = PureModelContextData.newBuilder();
    Protocol serializer = new Protocol("Name", "1.0.2");
    newBuilderResult.setSerializer(serializer);
    PureModelContextData rootPMCD = newBuilderResult.build();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult =
        pureModelContextServiceImpl.combinePureModelContextData(
            rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    assertNull(actualCombinePureModelContextDataResult.getOrigin());
    assertTrue(actualCombinePureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualCombinePureModelContextDataResult.getElements().isEmpty());
    assertSame(serializer, actualCombinePureModelContextDataResult.getSerializer());
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <ul>
   *   <li>Then return AllElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName(
      "Test combinePureModelContextData(PureModelContextData, PureModelContextData); then return AllElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData_thenReturnAllElementsSizeIsOne() {
    // Arrange
    Builder newBuilderResult = PureModelContextData.newBuilder();
    BigQueryFunction element = new BigQueryFunction();
    newBuilderResult.addElement(element);
    newBuilderResult.addElement(new BigQueryFunction());
    newBuilderResult.setSerializer(new Protocol("Name", "1.0.2"));
    PureModelContextData rootPMCD = newBuilderResult.build();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult =
        pureModelContextServiceImpl.combinePureModelContextData(
            rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    List<PackageableElement> allElements = actualCombinePureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    List<PackageableElement> elements = actualCombinePureModelContextDataResult.getElements();
    assertEquals(1, elements.size());
    assertSame(element, allElements.get(0));
    assertSame(element, elements.get(0));
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <ul>
   *   <li>Then return AllElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName(
      "Test combinePureModelContextData(PureModelContextData, PureModelContextData); then return AllElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData_thenReturnAllElementsSizeIsOne2() {
    // Arrange
    Builder newBuilderResult = PureModelContextData.newBuilder();
    BigQueryFunction element = new BigQueryFunction();
    newBuilderResult.addElement(element);
    newBuilderResult.addElement(new BigQueryFunction());
    newBuilderResult.addElement(new BigQueryFunction());
    newBuilderResult.setSerializer(new Protocol("Name", "1.0.2"));
    PureModelContextData rootPMCD = newBuilderResult.build();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult =
        pureModelContextServiceImpl.combinePureModelContextData(
            rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    List<PackageableElement> allElements = actualCombinePureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    List<PackageableElement> elements = actualCombinePureModelContextDataResult.getElements();
    assertEquals(1, elements.size());
    assertSame(element, allElements.get(0));
    assertSame(element, elements.get(0));
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <ul>
   *   <li>Then return Origin is {@link PureModelContextPointer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName(
      "Test combinePureModelContextData(PureModelContextData, PureModelContextData); then return Origin is PureModelContextPointer (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData_thenReturnOriginIsPureModelContextPointer() {
    // Arrange
    Builder newBuilderResult = PureModelContextData.newBuilder();
    PureModelContextPointer origin = new PureModelContextPointer();
    newBuilderResult.setOrigin(origin);
    newBuilderResult.setSerializer(new Protocol("Name", "1.0.2"));
    PureModelContextData rootPMCD = newBuilderResult.build();

    // Act and Assert
    assertSame(
        origin,
        pureModelContextServiceImpl
            .combinePureModelContextData(rootPMCD, PureModelContextData.newPureModelContextData())
            .getOrigin());
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <ul>
   *   <li>Then return Serializer is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName(
      "Test combinePureModelContextData(PureModelContextData, PureModelContextData); then return Serializer is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData_thenReturnSerializerIsNull() {
    // Arrange
    PureModelContextData rootPMCD = PureModelContextData.newPureModelContextData();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult =
        pureModelContextServiceImpl.combinePureModelContextData(
            rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    assertNull(actualCombinePureModelContextDataResult.getSerializer());
    assertNull(actualCombinePureModelContextDataResult.getOrigin());
    assertTrue(actualCombinePureModelContextDataResult.getAllElements().isEmpty());
    assertTrue(actualCombinePureModelContextDataResult.getElements().isEmpty());
  }

  /**
   * Test {@link PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}.
   *
   * <ul>
   *   <li>When {@link Builder} (default constructor) addElement {@link BigQueryFunction} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * PureModelContextServiceImpl#combinePureModelContextData(PureModelContextData,
   * PureModelContextData)}
   */
  @Test
  @DisplayName(
      "Test combinePureModelContextData(PureModelContextData, PureModelContextData); when Builder (default constructor) addElement BigQueryFunction (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PureModelContextData PureModelContextServiceImpl.combinePureModelContextData(PureModelContextData, PureModelContextData)"
  })
  void testCombinePureModelContextData_whenBuilderAddElementBigQueryFunction() {
    // Arrange
    Builder builder = new Builder();
    BigQueryFunction element = new BigQueryFunction();
    builder.addElement(element);
    builder.addElement(new BigQueryFunction());
    builder.setSerializer(new Protocol("Name", "1.0.2"));
    PureModelContextData rootPMCD = builder.build();

    // Act
    PureModelContextData actualCombinePureModelContextDataResult =
        pureModelContextServiceImpl.combinePureModelContextData(
            rootPMCD, PureModelContextData.newPureModelContextData());

    // Assert
    List<PackageableElement> allElements = actualCombinePureModelContextDataResult.getAllElements();
    assertEquals(1, allElements.size());
    List<PackageableElement> elements = actualCombinePureModelContextDataResult.getElements();
    assertEquals(1, elements.size());
    assertSame(element, allElements.get(0));
    assertSame(element, elements.get(0));
  }

  /**
   * Test {@link PureModelContextServiceImpl#buildAlloySDLC(String, String, String)}.
   *
   * <p>Method under test: {@link PureModelContextServiceImpl#buildAlloySDLC(String, String,
   * String)}
   */
  @Test
  @DisplayName("Test buildAlloySDLC(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AlloySDLC PureModelContextServiceImpl.buildAlloySDLC(String, String, String)"
  })
  void testBuildAlloySDLC() {
    // Arrange and Act
    AlloySDLC actualBuildAlloySDLCResult =
        pureModelContextServiceImpl.buildAlloySDLC("42", "42", "42");

    // Assert
    assertEquals("42", actualBuildAlloySDLCResult.baseVersion);
    assertEquals("42:42", actualBuildAlloySDLCResult.project);
    assertEquals("none", actualBuildAlloySDLCResult.version);
    assertNull(actualBuildAlloySDLCResult.artifactId);
    assertNull(actualBuildAlloySDLCResult.groupId);
    assertTrue(actualBuildAlloySDLCResult.packageableElementPointers.isEmpty());
  }
}
