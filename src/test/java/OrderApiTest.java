import base.BaseServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import helper.RandomDataProvider;
import models.orderApi.ErrorResponse;
import models.orderApi.OrderRequest;
import models.orderApi.OrderResponse;
import models.swapi.PeopleResponse;
import org.jeasy.random.EasyRandom;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static helper.ResponseSpec.checkStatusCodeBadRequest;
import static helper.ResponseSpec.checkStatusCodeOk;
import static org.testng.AssertJUnit.assertEquals;

public class OrderApiTest extends BaseServiceTest {

    EasyRandom easyRandom = new EasyRandom();
    private  ObjectMapper objectMapper= new ObjectMapper();


    @BeforeClass
    public void setUp(){
        configureFor("localhost",8083);
    }

    public PeopleResponse mockGetPeople(OrderRequest orderRequest,Boolean isVarthVader) throws JsonProcessingException {
        PeopleResponse peopleResponse = RandomDataProvider.random().nextObject(PeopleResponse.class);
        if(isVarthVader) peopleResponse.setName("Darth Vader");
        String response = objectMapper.writeValueAsString(peopleResponse);
         stubFor(get("/people/"+orderRequest.getPeopleId())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.valueOf(response))));
         return  peopleResponse;
    }

    @Test
    public void shouldCreateOrder() throws JsonProcessingException {
        //given
        OrderRequest orderRequest = RandomDataProvider.random().nextObject(OrderRequest.class);
        mockGetPeople(orderRequest,false);

        //when
        OrderResponse orderResponse = orderApiService.createOrder(orderRequest,checkStatusCodeOk()).as(OrderResponse.class);

        //then
        assertEquals(orderResponse.getPeopleResponse().getName(),"Luke Skywalker");
        assertEquals(orderResponse.getPeopleId(),orderRequest.getPeopleId());
        assertEquals(orderResponse.getOrderName(),orderRequest.getOrderName());
        assertEquals(orderResponse.getOrderDescription(),orderRequest.getOrderDescription());
    }

    @Test
    public void shouldNotCreateOrderWhenPeopleIsDarthVader() throws JsonProcessingException {
        //given
        OrderRequest orderRequest = easyRandom.nextObject(OrderRequest.class);
        mockGetPeople(orderRequest,true);
        //when
        ErrorResponse orderResponse = orderApiService.createOrder(orderRequest,checkStatusCodeBadRequest()).as(ErrorResponse.class);

        //then
        assertEquals(orderResponse.getMessage(),"Darth Vader cannot create an order.");
    }
}
