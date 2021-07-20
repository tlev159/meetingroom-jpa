package meetingRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MeetingRoomsDaoTest {

    private MeetingRoomsDao meetingRoomsDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        meetingRoomsDao = new MeetingRoomsDao(factory);

    }

    @Test
    void saveThenList() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);

        List<String> anotherRooms = meetingRoomsDao.getMeetingRoomsOrderedByName();
        assertThat(List.of("Első tárgyaló", "Harmadik tárgyaló", "Második tárgyaló"), equalTo(anotherRooms));
    }

    @Test
    void getMeetingRoomsOrderedByName() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);

        List<MeetingRoom> anotherRooms = meetingRoomsDao.getMeetingRooms();
        assertThat("Második tárgyaló", equalTo(anotherRooms.get(2).getName()));

    }
}