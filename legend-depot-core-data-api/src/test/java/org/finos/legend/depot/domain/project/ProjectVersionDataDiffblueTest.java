package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProjectVersionDataDiffblueTest {
  /**
   * Method under test: {@link ProjectVersionData#addDependencies(List)}
   */
  @Test
  void testAddDependencies() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    // Act
    projectVersionData.addDependencies(new ArrayList<>());

    // Assert
    assertTrue(projectVersionData.getDependencies().isEmpty());
  }

  /**
   * Method under test: {@link ProjectVersionData#addDependencies(List)}
   */
  @Test
  void testAddDependencies2() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    projectVersionData.addDependencies(dependencies);

    // Assert
    assertEquals(dependencies, projectVersionData.getDependencies());
  }

  /**
   * Method under test: {@link ProjectVersionData#addDependencies(List)}
   */
  @Test
  void testAddDependencies3() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));
    dependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    projectVersionData.addDependencies(dependencies);

    // Assert
    assertEquals(dependencies, projectVersionData.getDependencies());
  }

  /**
   * Method under test: {@link ProjectVersionData#addDependency(ProjectVersion)}
   */
  @Test
  void testAddDependency() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    // Act
    projectVersionData.addDependency(dependency);

    // Assert
    List<ProjectVersion> dependencies = projectVersionData.getDependencies();
    assertEquals(1, dependencies.size());
    assertSame(dependency, dependencies.get(0));
  }

  /**
   * Method under test: {@link ProjectVersionData#addDependency(ProjectVersion)}
   */
  @Test
  void testAddDependency2() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    projectVersionData.addDependency(dependency);

    // Act
    projectVersionData.addDependency(new ProjectVersion("42", "42", "42"));

    // Assert
    List<ProjectVersion> dependencies = projectVersionData.getDependencies();
    assertEquals(1, dependencies.size());
    assertSame(dependency, dependencies.get(0));
  }

  /**
   * Method under test: {@link ProjectVersionData#addProperties(List)}
   */
  @Test
  void testAddProperties() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    // Act
    projectVersionData.addProperties(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(projectVersionData.getProperties().isEmpty());
  }

  /**
   * Method under test: {@link ProjectVersionData#addProperties(List)}
   */
  @Test
  void testAddProperties2() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    ArrayList<Property> propertyList = new ArrayList<>();
    propertyList.add(new Property("Property Name", "42"));

    // Act
    projectVersionData.addProperties(propertyList);

    // Assert
    assertEquals(propertyList, projectVersionData.getProperties());
  }

  /**
   * Method under test: {@link ProjectVersionData#addProperties(List)}
   */
  @Test
  void testAddProperties3() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();

    ArrayList<Property> propertyList = new ArrayList<>();
    Property property = new Property("Property Name", "42");

    propertyList.add(property);
    propertyList.add(new Property("Property Name", "42"));

    // Act
    projectVersionData.addProperties(propertyList);

    // Assert
    List<Property> properties = projectVersionData.getProperties();
    assertEquals(1, properties.size());
    assertSame(property, properties.get(0));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersionData#ProjectVersionData()}
   *   <li>{@link ProjectVersionData#setDependencies(List)}
   *   <li>{@link ProjectVersionData#setDeprecated(boolean)}
   *   <li>{@link ProjectVersionData#setExcluded(boolean)}
   *   <li>{@link ProjectVersionData#setExclusionReason(String)}
   *   <li>{@link ProjectVersionData#setManifestProperties(Map)}
   *   <li>{@link ProjectVersionData#setProperties(List)}
   *   <li>{@link ProjectVersionData#getDependencies()}
   *   <li>{@link ProjectVersionData#getExclusionReason()}
   *   <li>{@link ProjectVersionData#getManifestProperties()}
   *   <li>{@link ProjectVersionData#getProperties()}
   *   <li>{@link ProjectVersionData#isDeprecated()}
   *   <li>{@link ProjectVersionData#isExcluded()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ProjectVersionData actualProjectVersionData = new ProjectVersionData();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    actualProjectVersionData.setDependencies(dependencies);
    actualProjectVersionData.setDeprecated(true);
    actualProjectVersionData.setExcluded(true);
    actualProjectVersionData.setExclusionReason("Just cause");
    HashMap<String, String> manifestProperties = new HashMap<>();
    actualProjectVersionData.setManifestProperties(manifestProperties);
    ArrayList<Property> properties = new ArrayList<>();
    actualProjectVersionData.setProperties(properties);
    List<ProjectVersion> actualDependencies = actualProjectVersionData.getDependencies();
    String actualExclusionReason = actualProjectVersionData.getExclusionReason();
    Map<String, String> actualManifestProperties = actualProjectVersionData.getManifestProperties();
    List<Property> actualProperties = actualProjectVersionData.getProperties();
    boolean actualIsDeprecatedResult = actualProjectVersionData.isDeprecated();
    boolean actualIsExcludedResult = actualProjectVersionData.isExcluded();

    // Assert that nothing has changed
    assertEquals("Just cause", actualExclusionReason);
    assertTrue(actualDependencies.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertTrue(actualManifestProperties.isEmpty());
    assertTrue(actualIsDeprecatedResult);
    assertTrue(actualIsExcludedResult);
    assertSame(dependencies, actualDependencies);
    assertSame(properties, actualProperties);
    assertSame(manifestProperties, actualManifestProperties);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersionData#ProjectVersionData(List, List)}
   *   <li>{@link ProjectVersionData#setDependencies(List)}
   *   <li>{@link ProjectVersionData#setDeprecated(boolean)}
   *   <li>{@link ProjectVersionData#setExcluded(boolean)}
   *   <li>{@link ProjectVersionData#setExclusionReason(String)}
   *   <li>{@link ProjectVersionData#setManifestProperties(Map)}
   *   <li>{@link ProjectVersionData#setProperties(List)}
   *   <li>{@link ProjectVersionData#getDependencies()}
   *   <li>{@link ProjectVersionData#getExclusionReason()}
   *   <li>{@link ProjectVersionData#getManifestProperties()}
   *   <li>{@link ProjectVersionData#getProperties()}
   *   <li>{@link ProjectVersionData#isDeprecated()}
   *   <li>{@link ProjectVersionData#isExcluded()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    // Act
    ProjectVersionData actualProjectVersionData = new ProjectVersionData(dependencies, new ArrayList<>());
    ArrayList<ProjectVersion> dependencies2 = new ArrayList<>();
    actualProjectVersionData.setDependencies(dependencies2);
    actualProjectVersionData.setDeprecated(true);
    actualProjectVersionData.setExcluded(true);
    actualProjectVersionData.setExclusionReason("Just cause");
    HashMap<String, String> manifestProperties = new HashMap<>();
    actualProjectVersionData.setManifestProperties(manifestProperties);
    ArrayList<Property> properties = new ArrayList<>();
    actualProjectVersionData.setProperties(properties);
    List<ProjectVersion> actualDependencies = actualProjectVersionData.getDependencies();
    String actualExclusionReason = actualProjectVersionData.getExclusionReason();
    Map<String, String> actualManifestProperties = actualProjectVersionData.getManifestProperties();
    List<Property> actualProperties = actualProjectVersionData.getProperties();
    boolean actualIsDeprecatedResult = actualProjectVersionData.isDeprecated();
    boolean actualIsExcludedResult = actualProjectVersionData.isExcluded();

    // Assert that nothing has changed
    assertEquals("Just cause", actualExclusionReason);
    assertTrue(actualDependencies.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertTrue(actualManifestProperties.isEmpty());
    assertTrue(actualIsDeprecatedResult);
    assertTrue(actualIsExcludedResult);
    assertSame(dependencies2, actualDependencies);
    assertSame(properties, actualProperties);
    assertSame(manifestProperties, actualManifestProperties);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProjectVersionData#ProjectVersionData(List, List, boolean, boolean)}
   *   <li>{@link ProjectVersionData#setDependencies(List)}
   *   <li>{@link ProjectVersionData#setDeprecated(boolean)}
   *   <li>{@link ProjectVersionData#setExcluded(boolean)}
   *   <li>{@link ProjectVersionData#setExclusionReason(String)}
   *   <li>{@link ProjectVersionData#setManifestProperties(Map)}
   *   <li>{@link ProjectVersionData#setProperties(List)}
   *   <li>{@link ProjectVersionData#getDependencies()}
   *   <li>{@link ProjectVersionData#getExclusionReason()}
   *   <li>{@link ProjectVersionData#getManifestProperties()}
   *   <li>{@link ProjectVersionData#getProperties()}
   *   <li>{@link ProjectVersionData#isDeprecated()}
   *   <li>{@link ProjectVersionData#isExcluded()}
   * </ul>
   */
  @Test
  void testGettersAndSetters3() {
    // Arrange
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    // Act
    ProjectVersionData actualProjectVersionData = new ProjectVersionData(dependencies, new ArrayList<>(), true, true);
    ArrayList<ProjectVersion> dependencies2 = new ArrayList<>();
    actualProjectVersionData.setDependencies(dependencies2);
    actualProjectVersionData.setDeprecated(true);
    actualProjectVersionData.setExcluded(true);
    actualProjectVersionData.setExclusionReason("Just cause");
    HashMap<String, String> manifestProperties = new HashMap<>();
    actualProjectVersionData.setManifestProperties(manifestProperties);
    ArrayList<Property> properties = new ArrayList<>();
    actualProjectVersionData.setProperties(properties);
    List<ProjectVersion> actualDependencies = actualProjectVersionData.getDependencies();
    String actualExclusionReason = actualProjectVersionData.getExclusionReason();
    Map<String, String> actualManifestProperties = actualProjectVersionData.getManifestProperties();
    List<Property> actualProperties = actualProjectVersionData.getProperties();
    boolean actualIsDeprecatedResult = actualProjectVersionData.isDeprecated();
    boolean actualIsExcludedResult = actualProjectVersionData.isExcluded();

    // Assert that nothing has changed
    assertEquals("Just cause", actualExclusionReason);
    assertTrue(actualDependencies.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertTrue(actualManifestProperties.isEmpty());
    assertTrue(actualIsDeprecatedResult);
    assertTrue(actualIsExcludedResult);
    assertSame(dependencies2, actualDependencies);
    assertSame(properties, actualProperties);
    assertSame(manifestProperties, actualManifestProperties);
  }
}
