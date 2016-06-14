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
    String uri = "/api/position/deletePosition";
    String json = "[{  \"title\": \"qweqw\",  \"id\": \"123\"}]";
    positionDTORequest.setId("123");
    positionDTORequest.setTitle("title");
    positionDTORequests.add(positionDTORequest);
    Mockito.doNothing().when(this.positionService).markForDeletePosition(Mockito.anyList());
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).param("clientId", CLIENT_ID)
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
  }

  // @RequestParam String clientId,
  // @RequestParam String storeId,
  // @RequestParam String requestId,
  // @RequestParam String channelId,
  // @RequestParam String username

  @Test
  public void testGetAllPosition() throws Exception {
    String uri = "/api/position/getAllPosition";
    this.position.setId(ID);
    this.position.setTitle(TITLE);
    this.positions.add(this.position);
    Mockito.when(this.positionService.getAllPosition()).thenReturn(this.positions);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getAllPosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME);
    Mockito.verify(this.positionService, Mockito.times(2)).getAllPosition();
  }


}
