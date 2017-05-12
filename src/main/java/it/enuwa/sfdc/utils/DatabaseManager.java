package it.enuwa.sfdc.utils;

import it.enuwa.sfdc.beans.CredentialsEntity;
import it.enuwa.sfdc.sfdc.AccessTokenResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by festini on 3/1/16.
 */
public class DatabaseManager {


    private static DateTimeFormatter dateDashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);



    public static Optional<CredentialsEntity> selectOAuthToken(){

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery("from CredentialsEntity");

        List<CredentialsEntity> listaCredenziali = q.list();

        session.getTransaction().commit();
        session.close();

        return listaCredenziali.stream().findFirst();

    }

    public static void saveOAuthToken(AccessTokenResponse token){

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        CredentialsEntity entity = new CredentialsEntity();

        entity.setAccessToken(token.getAccess_token());
        //entity.setTokenId(1);
        entity.setTokenType(token.getToken_type());
        entity.setInstanceUrl(token.getInstance_url());
        entity.setSignature(token.getSignature());

        session.saveOrUpdate(entity);

        session.getTransaction().commit();
        session.close();

    }

    public static boolean deleteOAuthToken(){

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query q =  session.createQuery("delete from CredentialsEntity");

        int i = q.executeUpdate();

        session.getTransaction().commit();
        session.close();

        return i > 0;

    }
}
