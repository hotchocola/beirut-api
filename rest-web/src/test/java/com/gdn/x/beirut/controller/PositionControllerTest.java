package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.services.PositionService;

public class PositionControllerTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final String ID = "id";

  private static final String STORE_ID = "storeId";

  private static final String CLIENT_ID = "clientId";

  private static final String CHANNEL_ID = "channelId";

  private static final String REQUEST_ID = "requestId";

  private static final String USERNAME = "username";

  private static final String TITLE = "title";

  private final String UriBasePath = "/api/position/";

  @InjectMocks
  PositionController positionController;

  @Mock
  private PositionService positionService;

  private final Mapper dm = new DozerBeanMapper();

  private MockMvc mockMVC;

  private final Mapper dozerMapper = new DozerBeanMapper();

  private final PositionDTORequest positionDTORequest = new PositionDTORequest();

  private final List<PositionDTORequest> positionDTORequests = new ArrayList<>();

  private final List<Position> positions = new ArrayList<Position>();

  private final Position position = new Position();

  @Test
  public void deletePositionTest() throws Exception {
    String uri = "deletePosition";
    String json = "[{  \"title\": \"qweqw\",  \"id\": \"123\"}]";
    Mockito.doNothing().when(this.positionService).markForDeletePosition(Mockito.anyList());
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(UriBasePath + uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).content(json)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    this.positionController.deletePosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        positionDTORequests);
    Mockito.verify(this.positionService, Mockito.times(2)).markForDeletePosition(Mockito.anyList());
  }

  // (@RequestParam String clientId,
  // @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
  // @RequestParam String username)

  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.mockMVC = standaloneSetup(this.positionController).build();
    this.positionController.setDozerMapper(dm);
    this.position.setId(ID);
    this.position.setTitle(TITLE);
    this.positions.add(this.position);
    this.positionDTORequest.setId("id");
    this.positionDTORequest.setTitle("title");
    this.positionDTORequests.add(positionDTORequest);
  }

  // @RequestParam String clientId,
  // @RequestParam String storeId,
  // @RequestParam String requestId,
  // @RequestParam String channelId,
  // @RequestParam String username

  @Test
  public void testGetAllPosition() throws Exception {
    String uri = "getAllPosition";
    Mockito.when(this.positionService.getAllPosition()).thenReturn(this.positions);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getAllPosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME);
    Mockito.verify(this.positionService, Mockito.times(2)).getAllPosition();
  }

  @Test
  public void testGetPositionByTitle() throws Exception {
    String uri = "getPositionByTitle";
    Mockito.when(this.positionService.getPositionByTitle(TITLE, STORE_ID))
        .thenReturn(this.positions);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("title", TITLE))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getPositionByTitle(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, TITLE);
    Mockito.verify(this.positionService, Mockito.times(2)).getPositionByTitle(TITLE, STORE_ID);
  }

  @Test
  public void testInsertNewPosition() throws Exception {
    String uri = "insertNewPosition";
    Position temp = new Position();
    String positionDTORequestJson = "{\"title\":\"title\"}";
    dozerMapper.map(this.positionDTORequest, temp);
    Mockito.when(this.positionService.insertNewPosition(temp)).thenReturn(true);
    this.mockMVC.perform(MockMvcRequestBuilders.post(UriBasePath + uri)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .param("clientId", CLIENT_ID).param("storeId", STORE_ID).param("requestId", REQUEST_ID)
        .param("channelId", CHANNEL_ID).param("username", USERNAME).content(positionDTORequestJson))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.insertNewPosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        this.positionDTORequest);
    Mockito.verify(this.positionService, Mockito.times(1)).insertNewPosition(temp);
  }

  @Test
  public void testUpdatePosition() throws Exception {
    String uri = "updatePosition";
    String positionDTORequestJson = "{\"id\":\"id\",\"title\":\"title\"}";
    Mockito.when(this.positionService.updatePositionTitle(this.positionDTORequest.getId(),
        this.positionDTORequest.getTitle())).thenReturn(true);
    this.mockMVC.perform(MockMvcRequestBuilders.post(UriBasePath + uri)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .param("clientId", CLIENT_ID).param("storeId", STORE_ID).param("requestId", REQUEST_ID)
        .param("channelId", CHANNEL_ID).param("username", USERNAME).content(positionDTORequestJson))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.updatePosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        this.positionDTORequest);
    Mockito.verify(this.positionService, Mockito.times(2))
        .updatePositionTitle(this.positionDTORequest.getId(), this.positionDTORequest.getTitle());
  }

}
