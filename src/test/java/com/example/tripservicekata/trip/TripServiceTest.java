package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import com.example.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TripServiceTest {

    @InjectMocks
    private UserSession userSession = new UserSession();

    @Mock
    private List<Trip> tripList = new ArrayList<>();

    @Mock
    private User notUser = null;


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

        // when

        // then

        TripService tripService = new TripServiceForTest(userSession, tripList);
        Assertions.assertThrows(UserNotLoggedInException.class, () -> { tripService.getTripsByUser(notUser); });
    }

    @DisplayName("친구와 함께 떠난 여행이 아니면 조회 안됨")
    @Test
    void getTripsByNotFriendTest() {
        /* 친구들과 떠난게 아니면 아무것도 조회되면 안됨 */

        User logInUser = new User();
        logInUser.addFriend(new User());
        logInUser.addTrip(tripList.get(0));
        logInUser.addTrip(tripList.get(tripList.size()-1));

        TripService tripService = new TripServiceForTest(userSession, tripList);

        assertEquals(0, tripService.getTripsByUser(logInUser).size());

    }

    private class TripServiceForTest extends TripService {

        private List<Trip> tripRslt;

        public TripServiceForTest(UserSession userSession,
                                  List<Trip> tripRslt) {
            super(userSession);
            this.tripRslt = tripRslt;
        }


        protected List<Trip> getTripsByUserFromDb(User user) {
            return tripRslt;
        }
    }
}
