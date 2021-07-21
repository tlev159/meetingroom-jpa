package meetingRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Test
    public void getEverySecondMeetingRoom() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);
        meetingRoomsDao.save("Negyedik tárgyaló", 3, 2);
        meetingRoomsDao.save("Ötödik tárgyaló", 2, 3);
        meetingRoomsDao.save("Hatodik tárgyaló", 1, 4);

        MeetingRoom meetingRoom = new MeetingRoom(4L,"Negyedik tárgyaló", 3, 2);

        List<MeetingRoom> oddMeetingRooms = meetingRoomsDao.getEverySecondMeetingRoom();
        assertThat(meetingRoom, equalTo(oddMeetingRooms.get(1)));

    }

    @Test
    void getExactMeetingRoomByName() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);
        meetingRoomsDao.save("Negyedik tárgyaló", 3, 2);
        meetingRoomsDao.save("Ötödik tárgyaló", 2, 3);
        meetingRoomsDao.save("Hatodik tárgyaló", 1, 4);

        List<MeetingRoom> loadedMeetingRooms = meetingRoomsDao.getExactMeetingRoomByName("Harmadik tárgyaló");

        assertThat(1, equalTo(loadedMeetingRooms.size()));
        assertThat(4, equalTo(loadedMeetingRooms.get(0).getLength()));
    }

    @Test
    void getMeetingRoomsByPrefix() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);
        meetingRoomsDao.save("Negyedik tárgyaló", 3, 2);
        meetingRoomsDao.save("Ötödik tárgyaló", 2, 3);
        meetingRoomsDao.save("Hatodik tárgyaló", 1, 4);

        List<MeetingRoom> loadedMeetingRooms = meetingRoomsDao.getMeetingRoomsByPrefix("k tárgyaló");

        assertThat(5, equalTo(loadedMeetingRooms.size()));
        assertThat(3, equalTo(loadedMeetingRooms.get(0).getLength()));

    }

    @Test
    void deleteAll() {
        meetingRoomsDao.save("Első tárgyaló", 3, 2);
        meetingRoomsDao.save("Második tárgyaló", 2, 3);
        meetingRoomsDao.save("Harmadik tárgyaló", 1, 4);
        meetingRoomsDao.save("Negyedik tárgyaló", 3, 2);
        meetingRoomsDao.save("Ötödik tárgyaló", 2, 3);
        meetingRoomsDao.save("Hatodik tárgyaló", 1, 4);

        meetingRoomsDao.deleteAll();

        List<MeetingRoom> loadedMeetingRooms = meetingRoomsDao.getMeetingRooms();

        assertThat(0, equalTo(loadedMeetingRooms.size()));
    }
}