package org.finos.legend.depot.store.resources.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageSchedulesResourceDiffblueTest {
  @Mock
  private AuthorisationProvider authorisationProvider;

  @InjectMocks
  private ManageSchedulesResource manageSchedulesResource;

  @Mock
  private Provider<Principal> provider;

  @Mock
  private ScheduleInstancesStore scheduleInstancesStore;

  @Mock
  private SchedulesFactory schedulesFactory;

  @Mock
  private SchedulesStore schedulesStore;

  /**
   * Method under test:
   * {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  void testGetSchedulerStatus() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    when(schedulesStore.getAll()).thenReturn(new ArrayList<>());

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesStore).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  void testGetSchedulerStatus2() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    when(schedulesStore.getAll()).thenReturn(scheduleInfoList);

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesStore).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  void testGetSchedulerStatus3() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo());
    when(schedulesStore.getAll()).thenReturn(scheduleInfoList);

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesStore).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  void testGetSchedulerStatus4() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    when(schedulesStore.getAll()).thenReturn(scheduleInfoList);

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(false);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesStore).getAll();
    assertEquals(scheduleInfoList, actualSchedulerStatus);
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  void testGetSchedulerStatus5() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    when(schedulesStore.getAll()).thenReturn(scheduleInfoList);

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(null);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesStore).getAll();
    assertEquals(scheduleInfoList, actualSchedulerStatus);
  }

  /**
   * Method under test: {@link ManageSchedulesResource#getSchedulerInstances()}
   */
  @Test
  void testGetSchedulerInstances() {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    when(scheduleInstancesStore.getAll()).thenReturn(scheduleInstanceList);

    // Act
    List<ScheduleInstance> actualSchedulerInstances = manageSchedulesResource.getSchedulerInstances();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(scheduleInstancesStore).getAll();
    assertTrue(actualSchedulerInstances.isEmpty());
    assertSame(scheduleInstanceList, actualSchedulerInstances);
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#forceScheduler(String, boolean)}
   */
  @Test
  void testForceScheduler() throws MissingResourceException {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    doNothing().when(schedulesFactory).trigger(Mockito.<String>any(), anyBoolean());

    // Act
    Response actualForceSchedulerResult = manageSchedulesResource.forceScheduler("Schedule Name", true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesFactory).trigger(eq("Schedule Name"), eq(true));
    Response.StatusType statusInfo = actualForceSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualForceSchedulerResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualForceSchedulerResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualForceSchedulerResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualForceSchedulerResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualForceSchedulerResult.getDate());
    assertNull(actualForceSchedulerResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualForceSchedulerResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualForceSchedulerResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualForceSchedulerResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualForceSchedulerResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualForceSchedulerResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualForceSchedulerResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualForceSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualForceSchedulerResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualForceSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualForceSchedulerResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualForceSchedulerResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test: {@link ManageSchedulesResource#deleteScheduler(String)}
   */
  @Test
  void testDeleteScheduler() throws MissingResourceException {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    doNothing().when(schedulesFactory).deRegister(Mockito.<String>any());

    // Act
    Response actualDeleteSchedulerResult = manageSchedulesResource.deleteScheduler("Schedule Name");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesFactory).deRegister(eq("Schedule Name"));
    Response.StatusType statusInfo = actualDeleteSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualDeleteSchedulerResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualDeleteSchedulerResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualDeleteSchedulerResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualDeleteSchedulerResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualDeleteSchedulerResult.getDate());
    assertNull(actualDeleteSchedulerResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualDeleteSchedulerResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualDeleteSchedulerResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualDeleteSchedulerResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualDeleteSchedulerResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualDeleteSchedulerResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualDeleteSchedulerResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualDeleteSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualDeleteSchedulerResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualDeleteSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualDeleteSchedulerResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualDeleteSchedulerResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test: {@link ManageSchedulesResource#deleteSchedules()}
   */
  @Test
  void testDeleteSchedules() throws MissingResourceException {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    doNothing().when(schedulesFactory).deRegisterAll();

    // Act
    Response actualDeleteSchedulesResult = manageSchedulesResource.deleteSchedules();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesFactory).deRegisterAll();
    Response.StatusType statusInfo = actualDeleteSchedulesResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualDeleteSchedulesResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualDeleteSchedulesResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualDeleteSchedulesResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualDeleteSchedulesResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualDeleteSchedulesResult.getDate());
    assertNull(actualDeleteSchedulesResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualDeleteSchedulesResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualDeleteSchedulesResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualDeleteSchedulesResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualDeleteSchedulesResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualDeleteSchedulesResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualDeleteSchedulesResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualDeleteSchedulesResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualDeleteSchedulesResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualDeleteSchedulesResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualDeleteSchedulesResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualDeleteSchedulesResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link ManageSchedulesResource#toggleScheduler(String, boolean)}
   */
  @Test
  void testToggleScheduler() throws MissingResourceException {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    doNothing().when(schedulesFactory).toggleDisable(Mockito.<String>any(), anyBoolean());

    // Act
    Response actualToggleSchedulerResult = manageSchedulesResource.toggleScheduler("Schedule Name", true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesFactory).toggleDisable(eq("Schedule Name"), eq(true));
    Response.StatusType statusInfo = actualToggleSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualToggleSchedulerResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualToggleSchedulerResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualToggleSchedulerResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualToggleSchedulerResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualToggleSchedulerResult.getDate());
    assertNull(actualToggleSchedulerResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualToggleSchedulerResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualToggleSchedulerResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualToggleSchedulerResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualToggleSchedulerResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualToggleSchedulerResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualToggleSchedulerResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualToggleSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualToggleSchedulerResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualToggleSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualToggleSchedulerResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualToggleSchedulerResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test: {@link ManageSchedulesResource#toggleScheduler(boolean)}
   */
  @Test
  void testToggleScheduler2() throws MissingResourceException {
    // Arrange
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    doNothing().when(schedulesFactory).toggleDisableAll(anyBoolean());

    // Act
    Response actualToggleSchedulerResult = manageSchedulesResource.toggleScheduler(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(schedulesFactory).toggleDisableAll(eq(true));
    Response.StatusType statusInfo = actualToggleSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualToggleSchedulerResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualToggleSchedulerResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualToggleSchedulerResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualToggleSchedulerResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualToggleSchedulerResult.getDate());
    assertNull(actualToggleSchedulerResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualToggleSchedulerResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualToggleSchedulerResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualToggleSchedulerResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualToggleSchedulerResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualToggleSchedulerResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualToggleSchedulerResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualToggleSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualToggleSchedulerResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualToggleSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualToggleSchedulerResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualToggleSchedulerResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ManageSchedulesResource#ManageSchedulesResource(AuthorisationProvider, Provider, SchedulesFactory, SchedulesStore, ScheduleInstancesStore)}
   *   <li>{@link ManageSchedulesResource#getResourceName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);

    // Act and Assert
    assertEquals(ManageSchedulesResource.SCHEDULES_RESOURCE,
        (new ManageSchedulesResource(authorisationProvider, principalProvider,
            new SchedulesFactoryImpl(mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true),
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class))).getResourceName());
  }
}
