package helper;

import models.orderApi.OrderRequest;
import models.swapi.PeopleResponse;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;

public class RandomDataProvider {
    EasyRandomParameters easyRandomParameters = new EasyRandomParameters();
    public static EasyRandom random(){
        return new EasyRandom(new EasyRandomParameters()
                .randomize(OrderRequest.class, new OrderRequestRandomizer())
                .randomize(PeopleResponse.class, new PeopleResponseRandomizer())
        );
    }

    private static class OrderRequestRandomizer implements Randomizer<OrderRequest>{
        @Override
        public OrderRequest getRandomValue(){
             return OrderRequest.builder()
                    .orderName(new StringRandomizer(5,30, new LongRangeRandomizer(Long.MIN_VALUE,Long.MAX_VALUE).getRandomValue()).getRandomValue())
                    .orderDescription(new StringRandomizer(5,30, new LongRangeRandomizer(Long.MIN_VALUE,Long.MAX_VALUE).getRandomValue()).getRandomValue())
                    .peopleId(1L)
                    .build();
        }
    }

    private static class PeopleResponseRandomizer implements Randomizer<PeopleResponse>{
        @Override
        public PeopleResponse getRandomValue(){
            return PeopleResponse.builder()
                    .name("Luke Skywalker")
                    .skinColor(new StringRandomizer(5,30, new LongRangeRandomizer(Long.MIN_VALUE,Long.MAX_VALUE).getRandomValue()).getRandomValue())
                    .hairColor(new StringRandomizer(5,30, new LongRangeRandomizer(Long.MIN_VALUE,Long.MAX_VALUE).getRandomValue()).getRandomValue())
                    .build();
        }
    }
}
