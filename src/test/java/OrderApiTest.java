import base.BaseServiceTest;
import io.restassured.response.Response;
import models.orderApi.ErrorResponse;
import models.orderApi.OrderRequest;
import models.orderApi.OrderResponse;
import org.jeasy.random.EasyRandom;
import org.testng.annotations.Test;

import static helper.ResponseSpec.checkStatusCodeBadRequest;
import static helper.ResponseSpec.checkStatusCodeOk;
import static org.testng.AssertJUnit.assertEquals;

public class OrderApiTest extends BaseServiceTest {

    EasyRandom easyRandom = new EasyRandom();
    @Test
    public void shouldCreateOrder(){
        //given
        OrderRequest orderRequest = easyRandom.nextObject(OrderRequest.class);
        orderRequest.setPeopleId(1L);

        //when
        OrderResponse orderResponse = orderApiService.createOrder(orderRequest,checkStatusCodeOk()).as(OrderResponse.class);

        //then
        assertEquals(orderResponse.getPeopleResponse().getName(),"Luke Skywalker");
        assertEquals(orderResponse.getPeopleId(),orderRequest.getPeopleId());
        assertEquals(orderResponse.getOrderName(),orderRequest.getOrderName());
        assertEquals(orderResponse.getOrderDescription(),orderRequest.getOrderDescription());
    }

    @Test
    public void shouldNotCreateOrderWhenPeopleIsDarthVader(){
        //given
        OrderRequest orderRequest = easyRandom.nextObject(OrderRequest.class);
        orderRequest.setPeopleId(4L);
        //when
        ErrorResponse orderResponse = orderApiService.createOrder(orderRequest,checkStatusCodeBadRequest()).as(ErrorResponse.class);

        //then
        assertEquals(orderResponse.getMessage(),"Darth Vader cannot create an order.");
    }
}
