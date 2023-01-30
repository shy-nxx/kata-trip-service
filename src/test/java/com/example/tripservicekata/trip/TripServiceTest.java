package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripServiceTest {

    @Mock
    private List<Trip> tripList = new ArrayList<>();

    @Mock
    private User user = new User();

    @Mock
    private User loggedUser = new User();

    @BeforeEach
    void init() {
        tripList.add(new Trip("taiwan"));
        tripList.add(new Trip("tokyo"));
        tripList.add(new Trip("osaka"));
        tripList.add(new Trip("sanghai"));
    }

    @DisplayName("로그인하지 않은 사용자 예외 처리")
    @Test
    void userNotLoggedExceptionTest() {
        // given
        TripService tripService = new TripServiceForTest(tripList);

        // when
        // then
        Assertions.assertThrows(UserNotLoggedInException.class, () -> { tripService.getTripsByUser(user, null); });
    }

    @DisplayName("친구와 함께 떠난 여행이 아니면 조회 안됨")
    @Test
    void getTripsByNotFriendTest() {
        // given
        TripService tripService = new TripServiceForTest(tripList);

        // when
        // then
        assertEquals(0, tripService.getTripsByUser(user, loggedUser).size());
    }

    @DisplayName("친구와 함께 떠난 여행 조회하기")
    @Test
    void getTripsByLoggedInUserTest() {
        // given
        TripService tripService = new TripServiceForTest(tripList);

        // when
        user.addFriend(loggedUser);

        // then
        assertEquals(tripList.size(), tripService.getTripsByUser(user, loggedUser).size());
    }

    private class TripServiceForTest extends TripService {

        private List<Trip> tripRslt;

        public TripServiceForTest(List<Trip> tripRslt) {
            super();
            this.tripRslt = tripRslt;
        }


        protected List<Trip> getTripsByUserFromDb(User user) {
            return tripRslt;
        }
    }
}
