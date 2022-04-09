package models.orderApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.swapi.PeopleResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long peopleId;
    private PeopleResponse peopleResponse;
    private String orderName;
    private String orderDescription;
}
