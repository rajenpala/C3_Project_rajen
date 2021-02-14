import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    final String NAME = "Amelie's cafe";
    final String LOCATION = "Chennai";
    final LocalTime OPENING_TIME = LocalTime.parse("10:30:00");
    final LocalTime CLOSING_TIME = LocalTime.parse("22:00:00");

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        final LocalTime CURRENT_TIME = LocalTime.of(13, 0);

        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);

        Restaurant restaurantSpy = Mockito.spy(restaurant);

        Mockito.doReturn(CURRENT_TIME).when(restaurantSpy).getCurrentTime();

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        final LocalTime CURRENT_TIME = LocalTime.of(7, 0);

        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);

        Restaurant restaurantSpy = Mockito.spy(restaurant);

        Mockito.doReturn(CURRENT_TIME).when(restaurantSpy).getCurrentTime();

        assertFalse(restaurantSpy.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void selecting_items_should_give_sum_of_all_selected_item_price() {
        restaurant = new Restaurant(NAME, LOCATION, OPENING_TIME, CLOSING_TIME);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Paneer Makhani", 131);
        restaurant.addToMenu("Butter Naan", 100);

        List<String> selectedItems = Arrays.asList("Vegetable lasagne", "Paneer Makhani", "Butter Naan");
        int cost = restaurant.selectItems(selectedItems);

        assertEquals(500, cost);
    }
}