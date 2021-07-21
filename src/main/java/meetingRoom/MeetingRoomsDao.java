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
        em.close();
        return meetingRoom;
    }

    @Override
    public List<String> getMeetingRoomsOrderedByName() {
        EntityManager em = factory.createEntityManager();
        List<String> meetingRoomNames = em.createQuery("SELECT m.name FROM MeetingRoom m ORDER BY m.name", String.class)
                .getResultList();
        em.close();
        return meetingRoomNames;
    }

    @Override
    public List<MeetingRoom> getEverySecondMeetingRoom() {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("SELECT m FROM MeetingRoom m WHERE 0 = MOD(m.id,2)", MeetingRoom.class)
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("SELECT m FROM MeetingRoom m ORDER BY m.name", MeetingRoom.class)
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("SELECT m FROM MeetingRoom m WHERE m.name = :name", MeetingRoom.class)
                .setParameter("name", name)
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("SELECT m FROM MeetingRoom m WHERE m.name LIKE :name", MeetingRoom.class)
                .setParameter("name", "%" + nameOrPrefix + "%")
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public void deleteAll() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from MeetingRoom m").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
