package org.finos.legend.depot.store.model.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class StoreProjectDataDiffblueTest {
  /**
   * Method under test:
   * {@link StoreProjectData#evaluateLatestVersionAndUpdate(String)}
   */
  @Test
  void testEvaluateLatestVersionAndUpdate() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");

    // Act
    boolean actualEvaluateLatestVersionAndUpdateResult = storeProjectData.evaluateLatestVersionAndUpdate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", storeProjectData.getLatestVersion());
    assertTrue(actualEvaluateLatestVersionAndUpdateResult);
  }

  /**
   * Method under test:
   * {@link StoreProjectData#evaluateLatestVersionAndUpdate(String)}
   */
  @Test
  void testEvaluateLatestVersionAndUpdate2() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");
    storeProjectData.setLatestVersion(null);

    // Act
    boolean actualEvaluateLatestVersionAndUpdateResult = storeProjectData.evaluateLatestVersionAndUpdate("-SNAPSHOT");

    // Assert
    assertNull(storeProjectData.getLatestVersion());
    assertFalse(actualEvaluateLatestVersionAndUpdateResult);
  }

  /**
   * Method under test:
   * {@link StoreProjectData#evaluateLatestVersionAndUpdate(String)}
   */
  @Test
  void testEvaluateLatestVersionAndUpdate3() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");
    storeProjectData.setLatestVersion("1.0.2");

    // Act
    boolean actualEvaluateLatestVersionAndUpdateResult = storeProjectData.evaluateLatestVersionAndUpdate("1.0.2");

    // Assert
    assertEquals("1.0.2", storeProjectData.getLatestVersion());
    assertFalse(actualEvaluateLatestVersionAndUpdateResult);
  }

  /**
   * Method under test:
   * {@link StoreProjectData#evaluateLatestVersionAndUpdate(String)}
   */
  @Test
  void testEvaluateLatestVersionAndUpdate4() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");
    storeProjectData.setLatestVersion("1.0.242");

    // Act
    boolean actualEvaluateLatestVersionAndUpdateResult = storeProjectData.evaluateLatestVersionAndUpdate("1.0.2");

    // Assert
    assertEquals("1.0.242", storeProjectData.getLatestVersion());
    assertFalse(actualEvaluateLatestVersionAndUpdateResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoreProjectData#equals(Object)}
   *   <li>{@link StoreProjectData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");
    StoreProjectData storeProjectData2 = new StoreProjectData("myproject", "42", "42");

    // Act and Assert
    assertEquals(storeProjectData, storeProjectData2);
    int expectedHashCodeResult = storeProjectData.hashCode();
    assertEquals(expectedHashCodeResult, storeProjectData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoreProjectData#equals(Object)}
   *   <li>{@link StoreProjectData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");

    // Act and Assert
    assertEquals(storeProjectData, storeProjectData);
    int expectedHashCodeResult = storeProjectData.hashCode();
    assertEquals(expectedHashCodeResult, storeProjectData.hashCode());
  }

  /**
   * Method under test: {@link StoreProjectData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoreProjectData storeProjectData = new StoreProjectData("42", "42", "42");

    // Act and Assert
    assertNotEquals(storeProjectData, new StoreProjectData("myproject", "42", "42"));
  }

  /**
   * Method under test: {@link StoreProjectData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoreProjectData("myproject", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoreProjectData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoreProjectData("myproject", "42", "42"), "Different type to StoreProjectData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoreProjectData#StoreProjectData()}
   *   <li>{@link StoreProjectData#setDefaultBranch(String)}
   *   <li>{@link StoreProjectData#setLatestVersion(String)}
   *   <li>{@link StoreProjectData#getDefaultBranch()}
   *   <li>{@link StoreProjectData#getId()}
   *   <li>{@link StoreProjectData#getLatestVersion()}
   *   <li>{@link StoreProjectData#getProjectId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoreProjectData actualStoreProjectData = new StoreProjectData();
    actualStoreProjectData.setDefaultBranch("janedoe/featurebranch");
    actualStoreProjectData.setLatestVersion("1.0.2");
    String actualDefaultBranch = actualStoreProjectData.getDefaultBranch();
    String actualId = actualStoreProjectData.getId();
    String actualLatestVersion = actualStoreProjectData.getLatestVersion();
    actualStoreProjectData.getProjectId();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertEquals("1.0.2", actualLatestVersion);
    assertEquals("janedoe/featurebranch", actualDefaultBranch);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoreProjectData#StoreProjectData(String, String, String)}
   *   <li>{@link StoreProjectData#setDefaultBranch(String)}
   *   <li>{@link StoreProjectData#setLatestVersion(String)}
   *   <li>{@link StoreProjectData#getDefaultBranch()}
   *   <li>{@link StoreProjectData#getId()}
   *   <li>{@link StoreProjectData#getLatestVersion()}
   *   <li>{@link StoreProjectData#getProjectId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    StoreProjectData actualStoreProjectData = new StoreProjectData("myproject", "42", "42");
    actualStoreProjectData.setDefaultBranch("janedoe/featurebranch");
    actualStoreProjectData.setLatestVersion("1.0.2");
    String actualDefaultBranch = actualStoreProjectData.getDefaultBranch();
    String actualId = actualStoreProjectData.getId();
    String actualLatestVersion = actualStoreProjectData.getLatestVersion();
    String actualProjectId = actualStoreProjectData.getProjectId();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertEquals("1.0.2", actualLatestVersion);
    assertEquals("42", actualStoreProjectData.getArtifactId());
    assertEquals("42", actualStoreProjectData.getGroupId());
    assertEquals("janedoe/featurebranch", actualDefaultBranch);
    assertEquals("myproject", actualProjectId);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoreProjectData#StoreProjectData(String, String, String, String, String)}
   *   <li>{@link StoreProjectData#setDefaultBranch(String)}
   *   <li>{@link StoreProjectData#setLatestVersion(String)}
   *   <li>{@link StoreProjectData#getDefaultBranch()}
   *   <li>{@link StoreProjectData#getId()}
   *   <li>{@link StoreProjectData#getLatestVersion()}
   *   <li>{@link StoreProjectData#getProjectId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters3() {
    // Arrange and Act
    StoreProjectData actualStoreProjectData = new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch",
        "1.0.2");
    actualStoreProjectData.setDefaultBranch("janedoe/featurebranch");
    actualStoreProjectData.setLatestVersion("1.0.2");
    String actualDefaultBranch = actualStoreProjectData.getDefaultBranch();
    String actualId = actualStoreProjectData.getId();
    String actualLatestVersion = actualStoreProjectData.getLatestVersion();
    String actualProjectId = actualStoreProjectData.getProjectId();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertEquals("1.0.2", actualLatestVersion);
    assertEquals("42", actualStoreProjectData.getArtifactId());
    assertEquals("42", actualStoreProjectData.getGroupId());
    assertEquals("janedoe/featurebranch", actualDefaultBranch);
    assertEquals("myproject", actualProjectId);
  }
}
