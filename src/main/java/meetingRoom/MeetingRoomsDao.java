package meetingRoom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MeetingRoomsDao implements MeetingRoomsRepository {

    private EntityManagerFactory factory;

    public MeetingRoomsDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public MeetingRoom save(String name, int width, int length) {
        MeetingRoom meetingRoom = new MeetingRoom(name, width, length);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(meetingRoom);
        em.getTransaction().commit();
        return meetingRoom;
    }

    @Override
    public List<String> getMeetingRoomsOrderedByName() {
        EntityManager em = factory.createEntityManager();
        List<String> meetingRoomNames = em.createQuery("SELECT m.name FROM MeetingRoom m ORDER BY m.name")
                .getResultList();
        return meetingRoomNames;
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        return null;
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("SELECT m FROM MeetingRoom m ORDER BY m.name", MeetingRoom.class)
                .getResultList();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        return null;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
