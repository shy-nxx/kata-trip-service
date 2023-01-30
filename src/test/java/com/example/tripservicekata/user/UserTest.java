package com.example.tripservicekata.user;

import com.example.tripservicekata.trip.Trip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User loggedUser = new User();

    @Mock
    private Trip trip;

    @DisplayName("친구 추가하기")
    @Test
    void addFriendsTest() {
        // given
        User friend = new User();

        // when
        loggedUser.addFriend(friend);

        // then
        assertEquals(1, loggedUser.getFriends().size());
    }

    @DisplayName("여행지 추가하기")
    @Test
    void addTripsTest() {
        // given
        trip = new Trip("taiwan");

        // when
        loggedUser.addTrip(trip);
        
        // then
        assertEquals(1, loggedUser.trips().size());
    }
}

