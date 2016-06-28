package com.gdn.x.beirut.clientsdk;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gdn.client_sdk.shade.org.apache.http.client.utils.URIBuilder;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnHttpClientHelper;
import com.gdn.common.util.GdnMandatoryRequestParameterUtil;
import com.gdn.common.web.param.MandatoryRequestParam;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.x.beirut.dto.request.PositionDTORequest;

public class BeirutApiClientTest {

  public static final String HOST = "localhost";

  public static final Integer PORT = 1234;

  public static final String ID = "myId";

  public static final String CONTEXT_PATH = "/beirut/api";

  public static final String STORE_ID = "myStoreId";

  public static final String CHANNEL_ID = "myChannelId";

  public static final String CLIENT_ID = "myClientId";

  public static final String REQUEST_ID = "myRequestId";

  public static final String USERNAME = "myUsername";

  public static final String PASSWORD = "myPassword";

  public static final Integer MAX_CONN_PER_ROUTE = 10;

  public static final Integer MAX_CONN_TOTAL = 50;

  public static final Integer CONNECTION_TIMEOUT_IN_MS = 2000;

  public static final String TITLE = "myTitle";

  public static final String STRING_URI = "myUri";


  @Mock
  private GdnRestClientConfiguration clientConfig;

  @Mock
  private GdnHttpClientHelper clientHelper;

  @InjectMocks
  private BeirutApiClient beirutApiClient;

  @Before
  public void initialize() {
    initMocks(this);
    this.beirutApiClient.setContextPath("/beirut/api");
    when(this.clientConfig.getStoreId()).thenReturn(STORE_ID);
    when(this.clientConfig.getClientId()).thenReturn(CLIENT_ID);
    when(this.clientConfig.getHost()).thenReturn(HOST);
    when(this.clientConfig.getConnectionTimeoutInMs()).thenReturn(CONNECTION_TIMEOUT_IN_MS);
    when(this.clientConfig.getChannelId()).thenReturn(CHANNEL_ID);
    when(this.clientConfig.getPort()).thenReturn(PORT);
    when(this.clientConfig.getUsername()).thenReturn(USERNAME);
    when(this.clientConfig.getPassword()).thenReturn(PASSWORD);
    when(this.clientConfig.getMaxConnPerRoute()).thenReturn(MAX_CONN_PER_ROUTE);
    when(this.clientConfig.getMaxConnTotal()).thenReturn(MAX_CONN_TOTAL);
  }

  @After
  public void noMoreTransaction() {
    // verifyNoMoreInteractions(this.clientHelper);
  }

  @Test
  public void testUpdatePosition() throws Exception {
    PositionDTORequest positionDTORequest = new PositionDTORequest();
    positionDTORequest.setTitle(TITLE);
    GdnBaseRestResponse gdnBaseRestUpdatePosition = new GdnBaseRestResponse(REQUEST_ID);

    String path = "/position/updatePosition";
    Map<String, String> map = new HashMap<String, String>();
    map.put("id", ID);

    URIBuilder builder = new URIBuilder().setScheme("http")
        .setHost(HOST.replace("http" + "://", "")).setPath(CONTEXT_PATH + path).setPort(PORT)
        .addParameter(GdnMandatoryRequestParameterUtil.STORE_ID_KEY_PARAMETER, STORE_ID)
        .addParameter(GdnMandatoryRequestParameterUtil.CHANNEL_ID_KEY_PARAMETER, CHANNEL_ID)
        .addParameter(GdnMandatoryRequestParameterUtil.REQUEST_ID_KEY_PARAMETER, REQUEST_ID)
        .addParameter(GdnMandatoryRequestParameterUtil.CLIENT_ID_KEY_PARAMETER, CLIENT_ID)
        .addParameter(GdnMandatoryRequestParameterUtil.USERNAME_KEY_PARAMETER, USERNAME)
        .addParameter(GdnMandatoryRequestParameterUtil.AUTHENTICATOR_KEY, USERNAME)
        .addParameter("id", ID);
    URI uri = builder.build();
    System.out.println(uri.toString());

    when(this.clientHelper.getURI(HOST, PORT, CONTEXT_PATH + path, MandatoryRequestParam
        .generateMandatoryRequestParam(STORE_ID, CHANNEL_ID, CLIENT_ID, REQUEST_ID), map))
            .thenReturn(uri);
    // when(this.clientHelper.invokePost(uri, positionDTORequest, PositionDTOResponse.class,
    // CONNECTION_TIMEOUT_IN_MS)).thenReturn(gdnBaseRestUpdatePosition);
    // this.beirutApiClient.updatePosition(REQUEST_ID, USERNAME, ID, positionDTORequest);

  }

}
