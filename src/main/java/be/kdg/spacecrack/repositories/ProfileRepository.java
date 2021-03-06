package be.kdg.spacecrack.repositories;

import be.kdg.spacecrack.model.Profile;
import be.kdg.spacecrack.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* Git $Id$
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */
@Component("profileRepository")
public class ProfileRepository implements IProfileRepository {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SessionFactory sessionFactory;

    public ProfileRepository() {}

    public ProfileRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createProfile(Profile profile) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(profile);
    }

    @Override
    public Profile getContact(User user) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("JpaQlInspection") Query q = session.createQuery("from Profile p where p.profileId = :pId");
        q.setParameter("pId", user.getProfile().getProfileId());

        return (Profile) q.uniqueResult();
    }

    @Override
    public void editContact(Profile profile) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(profile);
    }

    @Override
    public Profile getProfileByProfileId(int profileId) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("JpaQlInspection") Query q = session.createQuery("from Profile p where p.profileId = :profileId");
        q.setParameter("profileId", profileId);

        return (Profile) q.uniqueResult();
    }
}
